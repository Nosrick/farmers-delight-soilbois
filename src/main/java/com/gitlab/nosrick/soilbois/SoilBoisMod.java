package com.gitlab.nosrick.soilbois;

import com.gitlab.nosrick.soilbois.registry.BlockRegistry;
import com.gitlab.nosrick.soilbois.registry.GenerationRegistry;
import com.gitlab.nosrick.soilbois.registry.ItemRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.world.gen.GenerationStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoilBoisMod implements ModInitializer {
    public static final String MOD_ID = "soilbois";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("FUCK FACTORY FARMING don't @me");

        BlockRegistry.registerAll();
        ItemRegistry.registerAll();

        GenerationRegistry.registerAll();

        registerBiomeModifications();
    }

    protected void registerBiomeModifications() {
        BiomeModifications.addFeature(context ->
                context.getBiome().getTemperature() < 1f
                && context.getBiome().getDownfall() > 0,
                GenerationStep.Feature.VEGETAL_DECORATION,
                GenerationRegistry.PATCH_WILD_OATS.key());

        BiomeModifications.addFeature(context ->
                context.getBiome().getTemperature() > 0.3f && context.getBiome().getDownfall() > 0.4f,
                GenerationStep.Feature.VEGETAL_DECORATION,
                GenerationRegistry.PATCH_WILD_COTTON.key());
    }
}
