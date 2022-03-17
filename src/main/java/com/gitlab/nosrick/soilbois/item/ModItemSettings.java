package com.gitlab.nosrick.soilbois.item;

import com.gitlab.nosrick.soilbois.SoilBoisMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

public class ModItemSettings extends FabricItemSettings {
    public ModItemSettings() {
        super();
        group(SoilBoisMod.ITEM_GROUP);
    }
}