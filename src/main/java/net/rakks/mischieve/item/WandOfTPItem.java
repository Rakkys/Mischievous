package net.rakks.mischieve.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class WandOfTPItem extends Item {
    public WandOfTPItem(Settings settings) {
        super(settings);
    }

    public void resetNBT(ItemStack Item, PlayerEntity user) {
        NbtCompound nbtData = new NbtCompound();

        Item.setNbt(nbtData);
        setNBT(Item, user);
    }

    public void setNBT(ItemStack Item, PlayerEntity user) {
        NbtCompound nbtData = new NbtCompound();
        double user_X = user.getX();
        double user_Y = user.getY();
        double user_Z = user.getZ();

        RegistryKey<World> dimensionKey = user.getWorld().getRegistryKey();
        Identifier dimensionId = dimensionKey.getValue();

        nbtData.putDouble("mischieve:WOT_x", user_X);
        nbtData.putDouble("mischieve:WOT_y", user_Y);
        nbtData.putDouble("mischieve:WOT_z", user_Z);
        nbtData.putString("mischieve:WOT_world", dimensionId.toString());

        user.sendMessage(Text.literal(MessageFormat.format("({0}, {1}, {2})", user_X, user_Y, user_Z)), true);

        Item.setNbt(nbtData);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            return TypedActionResult.pass(user.getStackInHand(hand));
        }

        ItemStack WandOfTP = user.getStackInHand(hand);

        if (user.isSneaking()) {
            resetNBT(WandOfTP, user);
        } else if (!WandOfTP.hasNbt()) {
            setNBT(WandOfTP, user);
        } else {
            if (user.getStackInHand(hand).hasNbt()) {
                NbtCompound nbtData = WandOfTP.getNbt();
                double x = nbtData.getDouble("mischieve:WOT_x");
                double y = nbtData.getDouble("mischieve:WOT_y");
                double z = nbtData.getDouble("mischieve:WOT_z");

                Identifier dimensionId = new Identifier(nbtData.getString("mischieve:WOT_world"));
                RegistryKey<World> dimensionKey = RegistryKey.of(RegistryKeys.WORLD, dimensionId);
                ServerWorld wandDimension = user.getServer().getWorld(dimensionKey);

                // Work around because I couldn't figure out how to summon this particle normally.
                user.teleport(user.getX(), user.getY(), user.getZ(), true);

                // Flags are what is meant to be updated when teleported
                Set<PositionFlag> flags = EnumSet.of(PositionFlag.X, PositionFlag.Y, PositionFlag.Z);
                // Update X, Y, and Z
                user.teleport(wandDimension, x, y, z, flags, user.getYaw(), user.getPitch());


                // Work around because I couldn't figure out how to summon this particle normally.
                user.teleport(user.getX(), user.getY(), user.getZ(), true);
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.hasNbt();
    }

    public Rarity getRarity(ItemStack stack) {
        return Rarity.EPIC;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound itemNbt = stack.getNbt();
        double x = itemNbt.getDouble("mischieve:WOT_x");
        double y = itemNbt.getDouble("mischieve:WOT_y");
        double z = itemNbt.getDouble("mischieve:WOT_z");

        tooltip.add(Text.literal(MessageFormat.format("({0}, {1}, {2})", x, y, z)));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
