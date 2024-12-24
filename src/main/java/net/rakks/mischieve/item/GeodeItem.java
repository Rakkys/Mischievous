package net.rakks.mischieve.item;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.rakks.mischieve.util.ItemUtil;

import java.util.List;

public class GeodeItem extends Item {
    public GeodeItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            Random random = world.getRandom();
            List<Item> gemItems = ItemUtil.getItemsWithTag(world, "geode_gems");

            int randomInt = random.nextInt(gemItems.size());

            ItemStack randomOre = new ItemStack(gemItems.get(randomInt));

            user.giveItemStack(randomOre);
            user.getStackInHand(hand).decrement(1);

            ExperienceOrbEntity expOrb = new ExperienceOrbEntity(world, user.getX(), user.getY(), user.getZ(), 5);
            world.spawnEntity(expOrb);

            //TODO: Make the random item depend on a weighted system instead of just pulling out of a hat
            //TODO: It would probably have to rely on a built in list instead of using tags

            return TypedActionResult.success(user.getStackInHand(hand));
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
