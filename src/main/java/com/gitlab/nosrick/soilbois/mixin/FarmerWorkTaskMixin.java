package com.gitlab.nosrick.soilbois.mixin;

import com.gitlab.nosrick.soilbois.registry.ItemRegistry;
import net.minecraft.entity.ai.brain.task.FarmerWorkTask;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FarmerWorkTask.class, priority = 999)
public abstract class FarmerWorkTaskMixin {

    //Inject a check against a separate list AFTER the COMPOSTABLES.indexOf() check?
    /*
    @Inject(method = "compostSeeds",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Ljava/util/List;indexOf(Ljava/lang/Object;)I"),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    protected void checkAdditionalCompostables(
            ServerWorld world, VillagerEntity entity, GlobalPos pos, BlockState composterState,
            CallbackInfo ci, BlockPos blockPos, int i, int j, int[] is,
            SimpleInventory simpleInventory, int k, BlockState blockState,
            int l, ItemStack itemStack, int m) {

        if(m == -1){
            m = itemStack.isIn(Tags.VILLAGER_COMPOSTABLES) ? 0 : -1;
            if(m != -1){
                SoilBoisMod.LOGGER.info("WE DID IT LADS");
            }
        }
    }
     */

    @Inject(method = "performAdditionalWork",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    protected void performAdditionalWorkMixin(ServerWorld world, VillagerEntity entity, CallbackInfo ci) {
        this.craftAndDropBoxOfOats(entity);
    }

    protected void craftAndDropBoxOfOats(VillagerEntity villagerEntity) {
        SimpleInventory simpleInventory = villagerEntity.getInventory();
        int oats = simpleInventory.count(ItemRegistry.OATS.get());

        if (oats > 9) {
            int craftableBoxes = oats / 9;
            int oatsToRemove = craftableBoxes * 9;

            simpleInventory.removeItem(ItemRegistry.OATS.get(), oatsToRemove);
            ItemStack craftedBoxes = simpleInventory.addStack(new ItemStack(ItemRegistry.BOX_OF_OATS.get(), craftableBoxes));
            if (!craftedBoxes.isEmpty()) {
                villagerEntity.dropStack(craftedBoxes, 0.5f);
            }
        }
    }
}
