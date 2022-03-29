package com.nhoryzon.mc.farmersdelight.registry;

import net.minecraft.block.Block;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import javax.annotation.Nullable;

public enum ConfiguredFeaturesRegistry {

    PATCH_WILD_CABBAGES("patch_wild_cabbages",
            BlocksRegistry.WILD_CABBAGES.get(),
            64,
            2,
            2,
            RarityFilterPlacementModifier.of(32),
            SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
            BiomePlacementModifier.of()),

    PATCH_WILD_ONIONS("patch_wild_onions",
            BlocksRegistry.WILD_ONIONS.get(),
            64,
            2,
            2,
            RarityFilterPlacementModifier.of(48),
            SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
            BiomePlacementModifier.of()),

    PATCH_WILD_TOMATOES("patch_wild_tomatoes",
            BlocksRegistry.WILD_TOMATOES.get(),
            64,
            2,
            2,
            RarityFilterPlacementModifier.of(32),
            SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
            BiomePlacementModifier.of()),
    PATCH_WILD_CARROTS("patch_wild_carrots",
            BlocksRegistry.WILD_CARROTS.get(),
            64,
            2,
            2,
            RarityFilterPlacementModifier.of(48),
            SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
            BiomePlacementModifier.of()),

    PATCH_WILD_POTATOES("patch_wild_potatoes",
            BlocksRegistry.WILD_POTATOES.get(),
            64,
            2,
            2,
            RarityFilterPlacementModifier.of(48),
            SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
            BiomePlacementModifier.of()),

    PATCH_WILD_BEETROOTS("patch_wild_beetroots",
            BlocksRegistry.WILD_BEETROOTS.get(),
            64,
            2,
            2,
            RarityFilterPlacementModifier.of(32),
            SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
            BiomePlacementModifier.of()),

    PATCH_WILD_RICE("patch_wild_rice",
            BlocksRegistry.WILD_RICE.get(),
            64,
            4,
            4,
            BlockPredicate.not(BlockPredicate.IS_AIR),
            RarityFilterPlacementModifier.of(32),
            SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
            BiomePlacementModifier.of());

    private final String featureId;
    private final Block block;
    private final RandomPatchFeatureConfig config;
    private final PlacementModifier[] placementModifiers;

    private RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> registryEntry;

    ConfiguredFeaturesRegistry(
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

    ConfiguredFeaturesRegistry(
            String id,
            Block block,
            int tries,
            int spreadX,
            int spreadZ,
            @Nullable BlockPredicate blockPredicate,
            PlacementModifier... placementModifiers) {
        this.featureId = id;
        this.block = block;
        this.placementModifiers = placementModifiers;
        this.config = createRandomPatchFeatureConfig(block, tries, spreadX, spreadZ, blockPredicate);
    }

    public String getFeatureId() { return this.featureId; }
    public Block getBlock() { return this.block; }
    public PlacementModifier[] getPlacementModifiers() { return this.placementModifiers; }
    public RandomPatchFeatureConfig getConfig() { return this.config; }
    public RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> getRegistryEntry() { return this.registryEntry; }

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
        for (ConfiguredFeaturesRegistry value : values()) {
            value.registryEntry = ConfiguredFeatures.register(value.featureId, Feature.RANDOM_PATCH, value.config);
        }
    }
}
