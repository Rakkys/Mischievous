package net.rakks.mischieve.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.text.MessageFormat;
import java.util.Objects;

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
        World user_dim = user.getWorld();

        nbtData.putDouble("mischieve:WOT_x", user_X);
        nbtData.putDouble("mischieve:WOT_y", user_Y);
        nbtData.putDouble("mischieve:WOT_z", user_Z);
        nbtData.putString("mischieve:WOT_world", String.valueOf(user_dim));

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
                double x = WandOfTP.getNbt().getDouble("mischieve:WOT_x");
                double y = WandOfTP.getNbt().getDouble("mischieve:WOT_y");
                double z = WandOfTP.getNbt().getDouble("mischieve:WOT_z");
                String WOT_world = WandOfTP.getNbt().getString("mischieve:WOT_world");

                if (!Objects.equals(WOT_world, String.valueOf(user.getWorld()))) {
                    user.sendMessage(Text.translatable("mischieve:WOT.failed_teleport.different_world"), true);
                    return TypedActionResult.fail(user.getStackInHand(hand));
                }

                // Work around because I couldn't figure out how to summon this particle normally.
                user.teleport(user.getX(), user.getY(), user.getZ(), true);
                user.teleport(x,y,z, true);
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.hasNbt();
    }
}
