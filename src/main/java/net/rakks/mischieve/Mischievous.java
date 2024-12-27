package net.rakks.mischieve;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;
import net.rakks.mischieve.util.MischievousLootTableModify;
import net.rakks.mischieve.item.ItemRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mischievous implements ModInitializer {
	public static final String MOD_ID = "mischievous";

	// No clue where to put this
	public static final GameRules.Key<GameRules.BooleanRule> INSTANT_MAGIC_MIRROR =
			GameRuleRegistry.register("instantMagicMirror", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(false));

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		ItemRegistry.registerItems();

		MischievousLootTableModify.modifyLootTables();
	}
}