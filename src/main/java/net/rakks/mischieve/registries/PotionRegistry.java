package net.rakks.mischieve.registries;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.rakks.mischieve.Mischievous;
import net.rakks.mischieve.statusEffects.FlightEffect;

public class PotionRegistry {
    public static final StatusEffect FLIGHT_EFFECT = registerEffect("flight_effect", new FlightEffect());

    public static StatusEffect registerEffect(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(Mischievous.MOD_ID, name), effect);
    }

    public static void registerEffects() {

    }
}
