package net.rakks.mischieve.util;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.rakks.mischieve.Mischievous;

import java.util.List;
import java.util.stream.Collectors;

public class ItemUtil {
    public static List<Item> getItemsWithTag(World world, String tagName) {
        TagKey<Item> itemTag = TagKey.of(RegistryKeys.ITEM, new Identifier(Mischievous.MOD_ID, tagName));
        return world.getRegistryManager().get(RegistryKeys.ITEM).getOrCreateEntryList(itemTag)
                .stream()
                .map(RegistryEntry::value)
                .collect(Collectors.toList());
    }
}
