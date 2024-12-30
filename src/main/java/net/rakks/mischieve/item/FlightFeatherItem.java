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
        // So much nesting just so I have an idea of whats happening
        if (entity instanceof PlayerEntity player) {
            if (!player.hasStatusEffect(PotionRegistry.FLIGHT_EFFECT)) {
                    StatusEffectInstance flightEffect = new StatusEffectInstance(PotionRegistry.FLIGHT_EFFECT, 5 * 20, 0, true, false, true);
                    player.addStatusEffect(flightEffect, player);
            } else {
                StatusEffectInstance statusEffect = player.getStatusEffect(PotionRegistry.FLIGHT_EFFECT);
                if (statusEffect.getDuration() <= 2) {
                    StatusEffectInstance flightEffect = new StatusEffectInstance(PotionRegistry.FLIGHT_EFFECT, 5 * 20, 0, true, false, true);
                    player.addStatusEffect(flightEffect, player);
                }
            }
        }
    }
}
