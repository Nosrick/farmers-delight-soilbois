package com.gitlab.nosrick.soilbois.tag;

import com.gitlab.nosrick.soilbois.SoilBoisMod;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class Tags {

    public static Tag.Identified<Block> FARMLAND;
    public static Tag.Identified<Block> WILD_CROP_SPAWNS;
    public static Tag.Identified<Item> CROPS;
    public static Tag.Identified<Item> SEEDS;
    public static Tag.Identified<Item> VILLAGER_COMPOSTABLES;
    public static Tag.Identified<Item> VILLAGER_PLANTABLES;
    public static Tag.Identified<Item> VILLAGER_GATHERABLES;

    public static void registerAll() {
        FARMLAND = create("farmland", TagFactory.BLOCK);
        WILD_CROP_SPAWNS = create("wild_crop_spawns", TagFactory.BLOCK);
        CROPS = create("crops", TagFactory.ITEM);
        SEEDS = create("seeds", TagFactory.ITEM);
        VILLAGER_COMPOSTABLES = create("villager_compostables", TagFactory.ITEM);
        VILLAGER_PLANTABLES = create("villager_plantables", TagFactory.ITEM);
        VILLAGER_GATHERABLES = create("villager_gatherables", TagFactory.ITEM);
    }

    private static <E> Tag.Identified<E> create(String pathName, TagFactory<E> tagFactory) {
        return tagFactory.create(new Identifier(SoilBoisMod.MOD_ID, pathName));
    }
}
