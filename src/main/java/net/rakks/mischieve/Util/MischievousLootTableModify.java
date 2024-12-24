package net.rakks.mischieve.Util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import net.rakks.mischieve.item.ItemRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MischievousLootTableModify {
    private static final List<Identifier> GEODE_ORES = getGeodeOres();

    public static List<Identifier> getGeodeOres() {
        List<Identifier> geodeOres = new ArrayList<>();

        // COAL IRON GOLD COPPER LAPIS EMERALD REDSTONE DIAMOND

        List<String> oreBlocks = new ArrayList<>(Arrays.asList(
                "coal", "iron", "gold", "copper", "lapis", "emerald", "redstone", "diamond"));

        for (String oreBlock : oreBlocks) {
            geodeOres.add(new Identifier("minecraft", String.format("blocks/%s_ore", oreBlock)));
            geodeOres.add(new Identifier("minecraft", String.format("blocks/deepslate_%s_ore", oreBlock)));
        }

        return geodeOres;
    }

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, identifier, builder, lootTableSource) -> {
            if (GEODE_ORES.contains(identifier)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.1f)) // Drops 10% of the time
                        .with(ItemEntry.builder(ItemRegistry.GEM_GEODE)) // What Item to drop
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)) // How many to drop? x between y drops
                                .build());

                builder.pool(poolBuilder.build());
            }
        }));
    }
}
