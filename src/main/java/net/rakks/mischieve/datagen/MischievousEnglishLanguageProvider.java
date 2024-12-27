package net.rakks.mischieve.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.rakks.mischieve.Mischievous;
import net.rakks.mischieve.item.ItemRegistry;

import java.text.MessageFormat;

public class MischievousEnglishLanguageProvider extends FabricLanguageProvider {
    public MischievousEnglishLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(ItemRegistry.WAND_OF_TELEPORTATION, "Wand of teleportation");
        translationBuilder.add(ItemRegistry.KATANA, "Katana");
        translationBuilder.add(ItemRegistry.GEM_GEODE, "Gem Geode");
        translationBuilder.add(ItemRegistry.MAGIC_MIRROR, "Magic Mirror");

        translationBuilder.add("mischieve.magic_mirror.error.respawn_location", "Could not locate your respawn point");
    }
}
