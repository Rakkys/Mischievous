package net.rakks.mischieve.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.rakks.mischieve.registries.PotionRegistry;

public class FlightFeatherItem extends Item {
    public FlightFeatherItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {
            StatusEffectInstance statusEffect = new StatusEffectInstance(PotionRegistry.FLIGHT_EFFECT, 5 * 20, 0, true, false, true);
            player.addStatusEffect(statusEffect, player);
        }
    }
}
