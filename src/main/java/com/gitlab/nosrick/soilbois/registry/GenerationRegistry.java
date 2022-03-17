package com.gitlab.nosrick.soilbois.registry;

import net.minecraft.block.Block;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public enum GenerationRegistry {

    PATCH_WILD_OATS(
            "patch_wild_oats",
            BlockRegistry.OAT_CROPS.get(),
            16,
            2,
            2,
            RarityFilterPlacementModifier.of(32),
            SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
            BiomePlacementModifier.of()),

    PATCH_WILD_COTTON(
            "patch_wild_cotton",
            BlockRegistry.COTTON_CROPS.get(),
            16,
            2,
            2,
            RarityFilterPlacementModifier.of(32),
            SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
            BiomePlacementModifier.of());

    private final String featureId;
    private final Block block;
    private final RandomPatchFeatureConfig config;
    private final PlacementModifier[] placementModifiers;

    private RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> registryEntry;

    GenerationRegistry(
            String id,
            Block block,
            int tries,
            int spreadX,
            int spreadZ,
            PlacementModifier... placementModifiers) {
        this.featureId = id;
        this.block = block;
        this.placementModifiers = placementModifiers;
        this.config = createRandomPatchFeatureConfig(block, tries, spreadX, spreadZ);
    }

    public String getFeatureId() {
        return this.featureId;
    }

    public Block getBlock() {
        return this.block;
    }

    public PlacementModifier[] getPlacementModifiers() {
        return this.placementModifiers;
    }

    public RandomPatchFeatureConfig getConfig() {
        return this.config;
    }

    public RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> getRegistryEntry() {
        return this.registryEntry;
    }

    private static RandomPatchFeatureConfig createRandomPatchFeatureConfig(
            Block block,
            int tries,
            int spreadX,
            int spreadZ,
            BlockPredicate blockPredicate) {
        return new RandomPatchFeatureConfig(
                tries,
                spreadX,
                spreadZ,
                PlacedFeatures.createEntry(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(block)),
                        blockPredicate == null ? BlockPredicate.IS_AIR : blockPredicate));
    }

    private static RandomPatchFeatureConfig createRandomPatchFeatureConfig(
            Block block,
            int tries,
            int spreadX,
            int spreadZ) {
        return new RandomPatchFeatureConfig(
                tries,
                spreadX,
                spreadZ,
                PlacedFeatures.createEntry(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(block))));
    }

    public static void registerAll() {
        for (GenerationRegistry value : values()) {
            value.registryEntry = ConfiguredFeatures.register(value.featureId, Feature.RANDOM_PATCH, value.config);
        }
    }

}
