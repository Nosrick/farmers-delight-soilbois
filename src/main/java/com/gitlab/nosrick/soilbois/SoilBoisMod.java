package com.gitlab.nosrick.soilbois;

import com.gitlab.nosrick.soilbois.registry.BlockRegistry;
import com.gitlab.nosrick.soilbois.registry.ItemRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.block.Blocks;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProviderType;
import net.minecraft.loot.provider.number.LootNumberProviderTypes;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoilBoisMod implements ModInitializer {
    public static final String MOD_ID = "soilbois";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final Identifier GRASS_LOOT_ID = Blocks.GRASS.getLootTableId();

    @Override
    public void onInitialize() {
        LOGGER.info("FUCK FACTORY FARMING don't @me");

        BlockRegistry.registerAll();
        ItemRegistry.registerAll();

        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, table, setter) -> {
            if (id.equals(GRASS_LOOT_ID)) {
                FabricLootPoolBuilder builder = FabricLootPoolBuilder
                        .builder()
                        .rolls(ConstantLootNumberProvider.create(0.5f))
                        .with(ItemEntry.builder(ItemRegistry.OATS.get()))
                        .rolls(ConstantLootNumberProvider.create(0.5f))
                        .with(ItemEntry.builder(ItemRegistry.COTTON_SEEDS.get()));

                table.pool(builder);
            }
        });
    }
}
