package com.gitlab.nosrick.soilbois.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class ModBlockItem extends BlockItem {
    public ModBlockItem(Block block) {
        super(block, new ModItemSettings());
    }
}