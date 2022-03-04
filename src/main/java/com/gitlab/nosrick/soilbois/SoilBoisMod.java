package com.gitlab.nosrick.soilbois;

import com.gitlab.nosrick.soilbois.mixin.VillagerAccess;
import com.gitlab.nosrick.soilbois.registry.*;
import com.gitlab.nosrick.soilbois.tag.Tags;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.item.Item;
import net.minecraft.world.gen.GenerationStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class SoilBoisMod implements ModInitializer {
    public static final String MOD_ID = "soilbois";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

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
        //modifyVillagerGatherables();
        //modifyVillagerFarmerTaskCompostables();
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
/*
    protected void modifyVillagerGatherables() {
        ImmutableSet.Builder<Item> villagerGatherables = new ImmutableSet.Builder<Item>()
                .addAll(VillagerAccess.getGatherableItems());

        Tags.VILLAGER_PLANTABLES.values().forEach(villagerGatherables::add);
        Tags.VILLAGER_GATHERABLES.values().forEach(villagerGatherables::add);

        VillagerAccess.setGatherableItems(villagerGatherables.build());
    }
    protected void modifyVillagerFarmerTaskCompostables() {
        List<Item> baseItems = FarmerWorkTaskAccessor.getCompostables();
        List<Item> newItems = Lists.newArrayList(baseItems);
        newItems.addAll(Tags.VILLAGER_COMPOSTABLES.values());
        FarmerWorkTaskAccessor.setCompostables(newItems);
    }
*/
}
