package com.nhoryzon.mc.farmersdelight.tag;

import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

@SuppressWarnings("unused")
public class Tags {

    private Tags() {
    }

    public static final TagKey<Block> WILD_CROPS = createBlockTag(new Identifier(FarmersDelightMod.MOD_ID, "wild_crops"));
    public static final TagKey<Block> HEAT_SOURCES = createBlockTag(new Identifier(FarmersDelightMod.MOD_ID, "heat_sources"));
    public static final TagKey<Block> TRAY_HEAT_SOURCES = createBlockTag(new Identifier(FarmersDelightMod.MOD_ID, "tray_heat_sources"));
    public static final TagKey<Block> COMPOST_ACTIVATORS = createBlockTag(new Identifier(FarmersDelightMod.MOD_ID, "compost_activators"));
    public static final TagKey<Block> UNAFFECTED_BY_RICH_SOIL = createBlockTag(new Identifier(FarmersDelightMod.MOD_ID, "unaffected_by_rich_soil"));
    public static final TagKey<Block> KNIVES_CUTTABLE = createBlockTag(new Identifier(FarmersDelightMod.MOD_ID, "knives_cuttable"));
    public static final TagKey<Item> WILD_CROPS_ITEM = createItemTag(new Identifier(FarmersDelightMod.MOD_ID, "wild_crops"));
    public static final TagKey<Item> STRAW_HARVESTERS = createItemTag(new Identifier(FarmersDelightMod.MOD_ID, "straw_harvesters"));
    public static final TagKey<Item> COMFORT_FOODS = createItemTag(new Identifier(FarmersDelightMod.MOD_ID, "comfort_foods"));
    public static final TagKey<Item> WOLF_PREY = createItemTag(new Identifier(FarmersDelightMod.MOD_ID, "wolf_prey"));
    public static final TagKey<Item> CABBAGE_ROLL_INGREDIENTS = createItemTag(new Identifier(FarmersDelightMod.MOD_ID, "cabbage_roll_ingredients"));
    public static final TagKey<Item> KNIVES = createItemTag(new Identifier("c", "tools/knives"));
    public static final TagKey<Item> SHEARS = createItemTag(new Identifier("c", "tools/shears"));
    public static final TagKey<Item> HOES = createItemTag(new Identifier("c", "tools/hoes"));
    public static final TagKey<EntityType<?>> DOG_FOOD_USERS = createEntityTypeTag(new Identifier(FarmersDelightMod.MOD_ID, "dog_food_users"));
    public static final TagKey<EntityType<?>> HORSE_FEED_USERS = createEntityTypeTag(new Identifier(FarmersDelightMod.MOD_ID, "horse_feed_users"));

    //public static final TagKey<Biome> WILD_RICE_BIOMES = createBiomeTag(new Identifier(FarmersDelightMod.MOD_ID, "wild_rice"));

    private static TagKey<Block> createBlockTag(Identifier pathName) {
        return TagKey.of(Registry.BLOCK_KEY, pathName);
    }

    private static TagKey<Item> createItemTag(Identifier pathName) {
        return TagKey.of(Registry.ITEM_KEY, pathName);
    }

    private static TagKey<EntityType<?>> createEntityTypeTag(Identifier pathName) {
        return TagKey.of(Registry.ENTITY_TYPE_KEY, pathName);
    }

    private static TagKey<Biome> createBiomeTag(Identifier pathName) { return TagKey.of(Registry.BIOME_KEY, pathName); }
}