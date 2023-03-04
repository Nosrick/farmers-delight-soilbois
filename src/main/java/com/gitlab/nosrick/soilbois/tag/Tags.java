package com.gitlab.nosrick.soilbois.tag;

import com.gitlab.nosrick.soilbois.SoilBoisMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class Tags {

    public static TagKey<Block> FARMLAND;
    public static TagKey<Block> WILD_CROP_SPAWNS;
    public static TagKey<Item> CROPS;
    public static TagKey<Item> SEEDS;
    //public static TagKey<Item> VILLAGER_COMPOSTABLES;
    public static TagKey<Item> VILLAGER_PLANTABLES;
    public static TagKey<Item> VILLAGER_GATHERABLES;

    public static void registerAll() {
        FARMLAND = TagKey.of(Registry.BLOCK_KEY, SoilBoisMod.identifier("farmland"));
        WILD_CROP_SPAWNS = TagKey.of(Registry.BLOCK_KEY, SoilBoisMod.identifier("wild_crop_spawns"));
        CROPS = TagKey.of(Registry.ITEM_KEY, SoilBoisMod.identifier("crops"));
        SEEDS = TagKey.of(Registry.ITEM_KEY, SoilBoisMod.identifier("seeds"));
        //VILLAGER_COMPOSTABLES = TagKey.of(Registry.ITEM_KEY, SoilBoisMod.identifier("villager_compostables"));
        VILLAGER_PLANTABLES = TagKey.of(Registry.ITEM_KEY, SoilBoisMod.identifier("villager_plantables"));
        VILLAGER_GATHERABLES = TagKey.of(Registry.ITEM_KEY, SoilBoisMod.identifier("villager_gatherables"));
    }
}
