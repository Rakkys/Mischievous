package net.rakks.mischieve.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.PositionFlag;
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
import java.util.Set;

public class WandOfTPItem extends Item {
    public WandOfTPItem(Settings settings) {
        super(settings);
    }

    public void resetNBT(ItemStack item, PlayerEntity user, boolean updateNBT) {
        NbtCompound nbtData = new NbtCompound();

        item.setNbt(nbtData);
        if (updateNBT) {
            setNBT(item, user);
        }
    }

    public void setNBT(ItemStack Item, PlayerEntity user) {
        NbtCompound nbtData = new NbtCompound();
        double user_X = user.getX();
        double user_Y = user.getY();
        double user_Z = user.getZ();

        RegistryKey<World> dimensionKey = user.getWorld().getRegistryKey();
        Identifier dimensionId = dimensionKey.getValue();

        nbtData.putDouble("wand_x", user_X);
        nbtData.putDouble("wand_y", user_Y);
        nbtData.putDouble("wand_z", user_Z);
        nbtData.putString("wand_world", dimensionId.toString());

        user.sendMessage(Text.literal(MessageFormat.format("({0}, {1}, {2})", user_X, user_Y, user_Z)), true);

        Item.setNbt(nbtData);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            return TypedActionResult.pass(user.getStackInHand(hand));
        }

        ItemStack wandOfTP = user.getStackInHand(hand);

        if (user.isSneaking()) {
            resetNBT(wandOfTP, user, true);
        } else if (!wandOfTP.hasNbt()) {
            setNBT(wandOfTP, user);
        } else {
            if (user.getStackInHand(hand).hasNbt()) {
                NbtCompound nbtData = wandOfTP.getNbt();
                double x = nbtData.getDouble("wand_x");
                double y = nbtData.getDouble("wand_y");
                double z = nbtData.getDouble("wand_z");

                Identifier dimensionId = new Identifier(nbtData.getString("wand_world"));
                RegistryKey<World> dimensionKey = RegistryKey.of(RegistryKeys.WORLD, dimensionId);
                ServerWorld wandDimension = user.getServer().getWorld(dimensionKey);

                if (wandDimension == null) {
                    user.sendMessage(Text.translatable("mischieve:WOT_invalid_dimension"));
                    return TypedActionResult.fail(user.getStackInHand(hand));
                }

                teleportParticles(user);

                //TODO: Make it so you teleport where you set the wand instead of a safe landing spot

                Set<PositionFlag> flags = EnumSet.of(PositionFlag.X, PositionFlag.Y, PositionFlag.Z);
                user.teleport(wandDimension, x, y, z, flags, user.getYaw(), user.getPitch());

                teleportParticles(user);
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    private void teleportParticles(PlayerEntity user) {
        user.teleport(user.getX(), user.getY(), user.getZ(), true); // Particles!
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
        if (stack.hasNbt()) {
            NbtCompound itemNbt = stack.getNbt();
            double x = itemNbt.getDouble("wand_x");
            double y = itemNbt.getDouble("wand_y");
            double z = itemNbt.getDouble("wand_z");

            tooltip.add(Text.literal(MessageFormat.format("({0}, {1}, {2})", x, y, z)));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
