package com.gitlab.nosrick.soilbois.registry;

import com.gitlab.nosrick.soilbois.SoilBoisMod;
import com.gitlab.nosrick.soilbois.block.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.HayBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public enum BlockRegistry {

    OAK_PANTRY("oak_pantry", PantryBlock::new),
    BIRCH_PANTRY("birch_pantry", PantryBlock::new),
    SPRUCE_PANTRY("spruce_pantry", PantryBlock::new),
    JUNGLE_PANTRY("jungle_pantry", PantryBlock::new),
    ACACIA_PANTRY("acacia_pantry", PantryBlock::new),
    DARK_OAK_PANTRY("dark_oak_pantry", PantryBlock::new),
    CRIMSON_PANTRY("crimson_pantry", PantryBlock::new),
    WARPED_PANTRY("warped_pantry", PantryBlock::new),

    BASKET("basket", BasketBlock::new, true),

    STRAW_BALE("straw_bale", () -> new HayBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK)), true),

    BOX_OF_OATS("box_of_oats", BoxOfOatsBlock::new),
    CRATE_OF_OATS("crate_of_oats", () -> new Block(FabricBlockSettings.copyOf(Blocks.OAK_WOOD))),
    OAT_CROPS("oat_crops", OatCropBlock::new, true),
    COTTON_CROPS("cotton_crops", CottonCropBlock::new, true),
    CRATE_OF_COTTON_SEEDS("crate_of_cotton_seeds", () -> new Block(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));

    protected final String pathName;
    protected final Supplier<Block> blockSupplier;
    protected final FlammableBlockRegistry.Entry flammableRate;
    protected final boolean isCutout;
    protected Block block;

    protected static Block[] pantries;

    BlockRegistry(String pathName, Supplier<Block> blockSupplier) {
        this(pathName, blockSupplier, false, new FlammableBlockRegistry.Entry(0, 0));
    }

    BlockRegistry(String pathName, Supplier<Block> blockSupplier, boolean isCutout) {
        this(pathName, blockSupplier, isCutout, new FlammableBlockRegistry.Entry(0, 0));
    }

    private static FlammableBlockRegistry.Entry flammable(int burnChance, @SuppressWarnings("SameParameterValue") int spreadChance) {
        return new FlammableBlockRegistry.Entry(burnChance, spreadChance);
    }

    private static boolean isValidFlammableEntry(FlammableBlockRegistry.Entry flammableRate) {
        return flammableRate != null && flammableRate.getBurnChance() > 0 && flammableRate.getSpreadChance() > 0;
    }

    BlockRegistry(String pathName, Supplier<Block> blockSupplier, boolean isCutout, FlammableBlockRegistry.Entry flammableRate) {
        this.pathName = pathName;
        this.blockSupplier = blockSupplier;
        this.flammableRate = flammableRate;
        this.isCutout = isCutout;
    }

    public static void registerAll() {
        for (BlockRegistry value : values()) {
            Block block = value.get();
            Registry.register(Registry.BLOCK, new Identifier(SoilBoisMod.MOD_ID, value.pathName), block);
            if (isValidFlammableEntry(value.flammableRate)) {
                FlammableBlockRegistry.getDefaultInstance().add(block, value.flammableRate);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static void registerRenderLayer() {
        for (BlockRegistry value : values()) {
            if (value.isCutout) {
                BlockRenderLayerMap.INSTANCE.putBlock(value.get(), RenderLayer.getCutout());
            }
        }
    }

    public Block get() {
        if (block == null) {
            block = blockSupplier.get();
        }

        return block;
    }

    public static Block[] getPantries() {
        if(pantries == null) {
            List<Block> blocks = Arrays.stream(
                            BlockRegistry.values())
                    .map(BlockRegistry::get)
                    .filter(b -> b instanceof PantryBlock)
                    .toList();

            pantries = new Block[blocks.size()];
            pantries = blocks.toArray(pantries);
        }

        return pantries;
    }
}
