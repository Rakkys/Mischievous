package net.rakks.mischieve.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.rakks.mischieve.Mischievous;

public class ItemRegistry {
    public static final Item WAND_OF_TELEPORTATION = registerItem("wand_of_teleport",
            new WandOfTPItem(new FabricItemSettings().maxCount(1)));

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Mischievous.MOD_ID, name), item);
    }

    public static void registerItems() {

    }
}
