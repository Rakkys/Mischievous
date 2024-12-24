package net.rakks.mischieve;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.rakks.mischieve.datagen.MischievousItemTagProvider;
import net.rakks.mischieve.datagen.MischievousEnglishLanguageProvider;

public class MischievousDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(MischievousEnglishLanguageProvider::new);
		pack.addProvider(MischievousItemTagProvider::new);
	}
}
