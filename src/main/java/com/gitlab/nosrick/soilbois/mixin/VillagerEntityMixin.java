package com.gitlab.nosrick.soilbois.mixin;

import com.gitlab.nosrick.soilbois.tag.Tags;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.Set;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin {

    @ModifyArg(
            method = "hasSeedToPlant",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/inventory/SimpleInventory;containsAny(Ljava/util/Set;)Z"))
    protected Set<Item> hasSeedToPlantMixin(Set<Item> vanillaSeeds) {
        Set<Item> modifiedSet = new HashSet<>(vanillaSeeds);
        modifiedSet.addAll(Tags.VILLAGER_PLANTABLES.values());

        return modifiedSet;
    }

    @Inject(
            method = "canGather",
            at = @At(value = "RETURN"),
            cancellable = true)
    protected void checkForAdditionalGatherables(
            ItemStack stack,
            CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() | Tags.VILLAGER_GATHERABLES.contains(stack.getItem()));
    }
}
