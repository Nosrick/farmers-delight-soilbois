package com.gitlab.nosrick.soilbois.registry;

import com.gitlab.nosrick.soilbois.SoilBoisMod;
import com.gitlab.nosrick.soilbois.item.ConsumableItem;
import com.gitlab.nosrick.soilbois.item.ModBlockItem;
import com.gitlab.nosrick.soilbois.item.ModItemSettings;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public enum ItemRegistry {

    RAW_SEITAN("raw_seitan", () -> new ConsumableItem(new ModItemSettings().food(FoodRegistry.RAW_SEITAN.get()))),
    COOKED_SEITAN("cooked_seitan", () -> new ConsumableItem(new ModItemSettings().food(FoodRegistry.COOKED_SEITAN.get()))),
    RAW_SEITAN_PATTY("raw_seitan_patty", () -> new ConsumableItem(new ModItemSettings().food(FoodRegistry.RAW_SEITAN_PATTY.get()))),
    COOKED_SEITAN_PATTY("cooked_seitan_patty", () -> new ConsumableItem(new ModItemSettings().food(FoodRegistry.COOKED_SEITAN_PATTY.get()))),
    RAW_SEITAN_BACON("raw_seitan_bacon", () -> new ConsumableItem(new ModItemSettings().food(FoodRegistry.RAW_SEITAN_BACON.get()))),
    SEITAN_BACON("cooked_seitan_bacon", () -> new ConsumableItem(new ModItemSettings().food(FoodRegistry.SEITAN_BACON.get()))),
    SEITAN_SANDWICH("seitan_sandwich", () -> new ConsumableItem(new ModItemSettings().food(FoodRegistry.SEITAN_SANDWICH.get()))),

    COTTON_SEEDS("cotton_seeds", () -> new AliasedBlockItem(
            BlockRegistry.COTTON_CROPS.get(),
            new FabricItemSettings()
                    .group(SoilBoisMod.ITEM_GROUP))),
    COTTON_PUFFS("cotton_puff", () -> new Item(new ModItemSettings())),
    CRATE_OF_COTTON_SEEDS("crate_of_cotton_seeds", () -> new ModBlockItem(BlockRegistry.CRATE_OF_COTTON_SEEDS.get())),

    OATS("oats", () -> new ConsumableItem(
            new FabricItemSettings()
                    .group(SoilBoisMod.ITEM_GROUP)
                    .food(FoodRegistry.OATS.get()))),
    OAT_SEEDS("oat_seeds", () -> new AliasedBlockItem(
            BlockRegistry.OAT_CROPS.get(),
            new FabricItemSettings()
                    .group(SoilBoisMod.ITEM_GROUP))),
    BOX_OF_OATS("box_of_oats", () -> new ModBlockItem(BlockRegistry.BOX_OF_OATS.get())),
    CRATE_OF_OATS("crate_of_oats", () -> new ModBlockItem(BlockRegistry.CRATE_OF_OATS.get())),
    OAT_MILK("oat_milk", () -> new ConsumableItem(new ModItemSettings().food(FoodRegistry.OAT_MILK.get()))),
    PLAIN_PORRIDGE("plain_porridge", () -> new ConsumableItem(new ModItemSettings().food(FoodRegistry.PLAIN_PORRIDGE.get()))),
    FRUIT_PORRIDGE("fruit_porridge", () -> new ConsumableItem(new ModItemSettings().food(FoodRegistry.FRUIT_PORRIDGE.get()))),

    AGAR("agar", () -> new Item(new ModItemSettings())),
    PLANT_CHEESE("plant_cheese", () -> new ConsumableItem(new ModItemSettings().food(FoodRegistry.PLANT_CHEESE.get()))),
    PLANT_CHEESE_SLICE("plant_cheese_slice", () -> new ConsumableItem(new ModItemSettings().food(FoodRegistry.PLANT_CHEESE_SLICE.get()))),

    FRUIT_COMPOTE("fruit_compote", () -> new ConsumableItem(new ModItemSettings().food(FoodRegistry.FRUIT_COMPOTE.get()))),
    MUSHROOM_STOCK("mushroom_stock", () -> new ConsumableItem(new ModItemSettings().food(FoodRegistry.MUSHROOM_STOCK.get()))),

    PROTEIN_WRAP("protein_wrap", () -> new ConsumableItem(new ModItemSettings().food(FoodRegistry.PROTEIN_WRAP.get()))),
    ROASTED_PROTEIN_CHOPS("roasted_protein_chops", () -> new ConsumableItem(new ModItemSettings().food(FoodRegistry.ROASTED_PROTEIN_CHOPS.get())));

    private final String pathName;
    private final Supplier<Item> itemSupplier;
    private final Integer burnTime;
    private Item item;

    private final static List<Item> COMPOSTABLES = new ArrayList<>();

    ItemRegistry(String pathName, Supplier<Item> itemSupplier) {
        this(pathName, itemSupplier, null);
    }

    ItemRegistry(String pathName, Supplier<Item> itemSupplier, Integer burnTime) {
        this.pathName = pathName;
        this.itemSupplier = itemSupplier;
        this.burnTime = burnTime;
    }

    public static void registerAll() {
        for (ItemRegistry value : values()) {
            Registry.register(Registry.ITEM, new Identifier(SoilBoisMod.MOD_ID, value.pathName), value.get());
            if (value.burnTime != null) {
                FuelRegistry.INSTANCE.add(value.get(), value.burnTime);
            }
        }
    }

    public static void registerCompostables() {
        for (ItemRegistry value : values()) {
            if (value.item.isFood()
                    && value.item.getFoodComponent().getSaturationModifier() > 0f) {
                CompostingChanceRegistry.INSTANCE.add(
                        value.item,
                        value.item.getFoodComponent().getSaturationModifier());

                COMPOSTABLES.add(value.item);
            }

        }

        CompostingChanceRegistry.INSTANCE.add(COTTON_SEEDS.get(), 0.3f);
        CompostingChanceRegistry.INSTANCE.add(OAT_SEEDS.get(), 0.3f);
        COMPOSTABLES.add(COTTON_SEEDS.get());
        COMPOSTABLES.add(OAT_SEEDS.get());
    }

    public static List<Item> getCompostables() {
        return COMPOSTABLES.stream().toList();
    }

    public Item get() {
        if (item == null) {
            item = itemSupplier.get();
        }
        return item;
    }
}
