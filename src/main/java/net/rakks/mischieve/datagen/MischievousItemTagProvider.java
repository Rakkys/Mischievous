package net.rakks.mischieve.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.rakks.mischieve.Mischievous;

import java.util.concurrent.CompletableFuture;

public class MischievousItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public static final TagKey<Item> GEODE_GEMS = TagKey.of(RegistryKeys.ITEM, Identifier.of(Mischievous.MOD_ID, "geode_gems"));

    public MischievousItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(GEODE_GEMS)
                .add(Items.COAL)
                .add(Items.IRON_INGOT)
                .add(Items.GOLD_INGOT)
                .add(Items.COPPER_INGOT)
                .add(Items.LAPIS_LAZULI)
                .add(Items.EMERALD)
                .add(Items.REDSTONE)
                .add(Items.DIAMOND);
    }
}
