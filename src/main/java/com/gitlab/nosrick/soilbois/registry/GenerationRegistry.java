package com.gitlab.nosrick.soilbois.registry;

import com.gitlab.nosrick.soilbois.SoilBoisMod;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.function.Supplier;

public enum GenerationRegistry {

    PATCH_WILD_OATS(
            "patch_wild_oats",
            () -> Feature.RANDOM_PATCH.configure(
                    generateRandomPatchFeatureConfig(
                            BlockRegistry.OAT_CROPS.get(),
                            16,
                            2,
                            2)),
            "oat_crops",
            RarityFilterPlacementModifier.of(32),
            SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
            BiomePlacementModifier.of()),

    PATCH_WILD_COTTON(
            "patch_wild_cotton",
            () -> Feature.RANDOM_PATCH.configure(
                    generateRandomPatchFeatureConfig(
                            BlockRegistry.COTTON_CROPS.get(),
                            16,
                            2,
                            2)),
            "cotton_crops",
            RarityFilterPlacementModifier.of(32),
            SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
            BiomePlacementModifier.of());

    private final String configPath;
    private final String featurePath;
    private final Supplier<? extends ConfiguredFeature<?, ?>> featureConfigSupplier;
    private final PlacementModifier[] placementModifiers;

    private ConfiguredFeature<?, ?> configuredFeature;
    private RegistryKey<ConfiguredFeature<?, ?>> configuredFeatureRegistryKey;
    private PlacedFeature feature;
    private RegistryKey<PlacedFeature> placedFeatureRegistryKey;

    GenerationRegistry(
            String configPathName,
            Supplier<? extends ConfiguredFeature<?, ?>> featureConfigSupplier,
            String featurePathName,
            PlacementModifier... placementModifiers) {

        this.configPath = configPathName;
        this.featureConfigSupplier = featureConfigSupplier;
        this.featurePath = featurePathName;
        this.placementModifiers = placementModifiers;
    }

    public static void registerAll() {
        for (GenerationRegistry value : values()) {
            Identifier configId = new Identifier(SoilBoisMod.MOD_ID, value.configPath);
            value.configuredFeature = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, configId, value.featureConfigSupplier.get());
            value.configuredFeatureRegistryKey = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, configId);

            Identifier featureId = new Identifier(SoilBoisMod.MOD_ID, value.featurePath);
            value.feature = Registry.register(
                    BuiltinRegistries.PLACED_FEATURE,
                    featureId,
                    value.configuredFeature.withPlacement(value.placementModifiers));

            value.placedFeatureRegistryKey = RegistryKey.of(Registry.PLACED_FEATURE_KEY, featureId);
        }
    }

    public PlacedFeature get() {
        return feature;
    }

    public RegistryKey<PlacedFeature> key() {
        return placedFeatureRegistryKey;
    }

    private static RandomPatchFeatureConfig generateRandomPatchFeatureConfig(
            Block block,
            int tries,
            int spreadX,
            int spreadY) {

        if (block instanceof CropBlock) {
            return new RandomPatchFeatureConfig(
                    tries,
                    spreadX,
                    spreadY,
                    () -> Feature.SIMPLE_BLOCK.configure(
                                    new SimpleBlockFeatureConfig(
                                            BlockStateProvider.of(block.getDefaultState().with(
                                                    CropBlock.AGE,
                                                    CropBlock.MAX_AGE))))
                            .withInAirFilter());
        }

        return new RandomPatchFeatureConfig(
                tries,
                spreadX,
                spreadY,
                () -> Feature.SIMPLE_BLOCK.configure(
                                new SimpleBlockFeatureConfig(
                                        BlockStateProvider.of(block)))
                        .withInAirFilter());
    }

}
