package com.gitlab.nosrick.soilbois.mixin;

import com.gitlab.nosrick.soilbois.tag.Tags;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin {

    @ModifyArg(
            method = "hasSeedToPlant",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/inventory/SimpleInventory;containsAny(Ljava/util/Set;)Z"))
    protected Set<Item> hasSeedToPlantMixin(Set<Item> vanillaSeeds) {
        Set<Item> modifiedSet = new HashSet<>(vanillaSeeds);
        modifiedSet.addAll(
                Registry.ITEM.stream()
                        .filter(item ->
                                new ItemStack(item)
                                        .isIn(Tags.VILLAGER_PLANTABLES))
                        .collect(Collectors.toSet()));

        return modifiedSet;
    }

    @Inject(
            method = "canGather",
            at = @At(value = "RETURN"),
            cancellable = true)
    protected void checkForAdditionalGatherables(
            ItemStack stack,
            CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() | stack.isIn(Tags.VILLAGER_GATHERABLES));
    }
}
