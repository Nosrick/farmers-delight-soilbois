package com.gitlab.nosrick.soilbois;

import com.gitlab.nosrick.soilbois.mixin.VillagerAccess;
import com.gitlab.nosrick.soilbois.registry.*;
import com.gitlab.nosrick.soilbois.tag.Tags;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeatures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class SoilBoisMod implements ModInitializer {
    public static final String MOD_ID = "soilbois";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "main"),
            () -> new ItemStack(ItemRegistry.COOKED_SEITAN.get()));

    public static Identifier identifier(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        LOGGER.info("FUCK FACTORY FARMING don't @me");

        BlockRegistry.registerAll();
        ItemRegistry.registerAll();
        ItemRegistry.registerCompostables();

        GenerationRegistry.registerAll();

        registerBiomeModifications();

        Tags.registerAll();

        modifyVillagerFoodItems();
    }

    public static Text i18n(String key, Object... args) {
        return Text.translatable(MOD_ID + "." + key, args);
    }

    protected void registerBiomeModifications() {
        BiomeModifications.addFeature(context ->
                context.getBiome().getTemperature() < 1f
                && context.getBiome().getDownfall() > 0,
                GenerationStep.Feature.VEGETAL_DECORATION,
                PlacedFeatures.register(
                        GenerationRegistry.PATCH_WILD_OATS.getFeatureId(),
                        GenerationRegistry.PATCH_WILD_OATS.getRegistryEntry(),
                        GenerationRegistry.PATCH_WILD_OATS.getPlacementModifiers()).getKey().get());

        BiomeModifications.addFeature(context ->
                context.getBiome().getTemperature() > 0.3f && context.getBiome().getDownfall() > 0.4f,
                GenerationStep.Feature.VEGETAL_DECORATION,
                PlacedFeatures.register(
                        GenerationRegistry.PATCH_WILD_COTTON.getFeatureId(),
                        GenerationRegistry.PATCH_WILD_COTTON.getRegistryEntry(),
                        GenerationRegistry.PATCH_WILD_COTTON.getPlacementModifiers()).getKey().get());
    }

    protected void modifyVillagerFoodItems() {
        ImmutableMap.Builder<Item, Integer> villagerFoodItems = new ImmutableMap.Builder<Item, Integer>()
                .putAll(VillagerAccess.getItemFoodValues());

        Arrays.stream(ItemRegistry.values())
                .map(ItemRegistry::get)
                .filter(Item::isFood)
                .forEach(item -> villagerFoodItems.put(
                        item,
                        item.getFoodComponent().getHunger()));

        VillagerAccess.setItemFoodValues(villagerFoodItems.build());
    }
}
