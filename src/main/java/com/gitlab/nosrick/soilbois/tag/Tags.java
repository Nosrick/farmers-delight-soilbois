package com.gitlab.nosrick.soilbois.tag;

import com.gitlab.nosrick.soilbois.SoilBoisMod;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class Tags {

    public static final Tag.Identified<Block> FARMLAND = create("farmland", TagFactory.BLOCK);
    public static final Tag.Identified<Block> WILD_CROP_SPAWNS = create("wild_crop_spawns", TagFactory.BLOCK);

    private static <E> Tag.Identified<E> create(String pathName, TagFactory<E> tagFactory) {
        return tagFactory.create(new Identifier(SoilBoisMod.MOD_ID, pathName));
    }
}
