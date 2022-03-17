package com.gitlab.nosrick.soilbois.registry;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public enum FoodRegistry {

    RAW_SEITAN(4, 0.4f, false),
    COOKED_SEITAN(16, 0.8f, false),
    RAW_SEITAN_PATTY(1, 0.4f, false),
    COOKED_SEITAN_PATTY(4, 0.8f, false),
    RAW_SEITAN_BACON(1, 0.4f, false),
    SEITAN_BACON(2, 0.8f, false),
    SEITAN_SANDWICH(
            12,
            0.85f,
            new StatusEffectInstance(StatusEffects.SATURATION),
            100f,
            false,
            false,
            false),

    OATS(-2, 0.3f, false),
    OAT_MILK(
            4,
            0.5f,
            new StatusEffectInstance(StatusEffects.SATURATION),
            100f,
            false,
            true,
            true),
    PLAIN_PORRIDGE(
            10,
            0.6f,
            false),
    FRUIT_PORRIDGE(
            12,
            0.8f,
            false),

    PLANT_CHEESE(
            8,
            0.5f,
            false),
    PLANT_CHEESE_SLICE(
            2,
            0.5f,
            false),

    FRUIT_COMPOTE(2, 0.4f, false),
    MUSHROOM_STOCK(2, 0.8f, false),

    PROTEIN_WRAP(10, 0.8f),
    ROASTED_PROTEIN_CHOPS(12, 0.8f);

    private final FoodComponent food;

    FoodRegistry(int hunger, float saturation) {
        this(hunger, saturation, null, 0f, false, false, false);
    }

    FoodRegistry(int hunger, float saturation, boolean isMeat) {
        this(hunger, saturation, null, 0f, isMeat, false, false);
    }

    FoodRegistry(int hunger, float saturation, StatusEffectInstance effect, float effectChance) {
        this(hunger, saturation, effect, effectChance, false, false, false);
    }

    FoodRegistry(int hunger, float saturation, StatusEffectInstance effect, float effectChance, boolean isMeat) {
        this(hunger, saturation, effect, effectChance, isMeat, false, false);
    }

    FoodRegistry(
            int hunger,
            float saturation,
            StatusEffectInstance effect,
            float effectChance,
            boolean isMeat,
            boolean isFastToEat,
            boolean alwaysEdible) {

        FoodComponent.Builder builder = new FoodComponent.Builder();
        builder.hunger(hunger).saturationModifier(saturation);

        if (effect != null) {
            builder.statusEffect(effect, effectChance);
        }

        if (isMeat) {
            builder.meat();
        }

        if (isFastToEat) {
            builder.snack();
        }

        if (alwaysEdible) {
            builder.alwaysEdible();
        }

        food = builder.build();
    }

    public FoodComponent get() {
        return food;
    }
}
