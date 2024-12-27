package net.rakks.mischieve.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.rakks.mischieve.item.ItemRegistry;

import java.util.function.Consumer;

public class MischievousRecipeProvider extends FabricRecipeProvider {
    public MischievousRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> consumer) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ItemRegistry.MAGIC_MIRROR)
                .pattern("dgd")
                .pattern("geg")
                .pattern("dgd")
                .input('d', Items.DIAMOND)
                .input('g', ConventionalItemTags.GLASS_BLOCKS) // Uses all glass blocks, but panes
                .input('e', Items.ENDER_PEARL)
                .criterion(FabricRecipeProvider.hasItem(Items.ENDER_PEARL),
                        FabricRecipeProvider.conditionsFromItem(Items.ENDER_PEARL))
                .offerTo(consumer);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ItemRegistry.WAND_OF_TELEPORTATION)
                .pattern(" ge")
                .pattern(" bg")
                .pattern("b  ")
                .input('g', Items.GOLD_INGOT)
                .input('e', Items.ENDER_PEARL)
                .input('b', Items.BLAZE_ROD)
                .criterion(FabricRecipeProvider.hasItem(Items.ENDER_PEARL),
                        FabricRecipeProvider.conditionsFromItem(Items.ENDER_PEARL))
                .offerTo(consumer);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ItemRegistry.KATANA)
                .pattern("  n")
                .pattern(" n ")
                .pattern("sr ")
                .input('n', Items.NETHERITE_INGOT)
                .input('s', Items.STICK)
                .input('r', Items.FEATHER)
                .criterion(FabricRecipeProvider.hasItem(Items.NETHERITE_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.NETHERITE_INGOT))
                .offerTo(consumer);
    }

    public void generateCompact(ItemConvertible input, ItemConvertible result,
                                RecipeCategory recipeCategory, Consumer<RecipeJsonProvider> consumer) {
        ShapelessRecipeJsonBuilder.create(recipeCategory, result)
                .input(input, 9)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(consumer);
    }

    public void generateUncompact(ItemConvertible input, ItemConvertible result,
                                  RecipeCategory recipeCategory, Consumer<RecipeJsonProvider> consumer) {
        ShapelessRecipeJsonBuilder.create(recipeCategory, result)
                .input(input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(consumer);
    }
}
