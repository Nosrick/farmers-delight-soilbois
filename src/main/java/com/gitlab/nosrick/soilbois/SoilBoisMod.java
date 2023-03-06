package com.gitlab.nosrick.soilbois;

import com.gitlab.nosrick.soilbois.registry.BlockRegistry;
import com.gitlab.nosrick.soilbois.registry.GenerationRegistry;
import com.gitlab.nosrick.soilbois.registry.ItemRegistry;
import com.gitlab.nosrick.soilbois.tag.Tags;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeatures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        registerLootTableModifications();

        Tags.registerAll();
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

    protected void registerLootTableModifications() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (source.isBuiltin()) {
                if (Blocks.GRASS.getLootTableId().equals(id)) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .with(ItemEntry.builder(ItemRegistry.OAT_SEEDS.get()))
                            .with(ItemEntry.builder(ItemRegistry.COTTON_SEEDS.get()));

                    tableBuilder.pool(poolBuilder);
                }
                else if(Blocks.TALL_GRASS.getLootTableId().equals(id)) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .with(ItemEntry.builder(ItemRegistry.OAT_SEEDS.get()))
                            .with(ItemEntry.builder(ItemRegistry.COTTON_SEEDS.get()));

                    tableBuilder.pool(poolBuilder);
                }
            }
        });
    }
}
