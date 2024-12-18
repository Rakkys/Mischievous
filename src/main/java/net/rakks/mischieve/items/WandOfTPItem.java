package net.rakks.mischieve.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.text.MessageFormat;

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

        nbtData.putDouble("mischieve:WOT_x", user_X);
        nbtData.putDouble("mischieve:WOT_y", user_Y);
        nbtData.putDouble("mischieve:WOT_z", user_Z);

        user.sendMessage(Text.literal(MessageFormat.format("({0}, {1}, {2})", user_X, user_Y, user_Z)));

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
