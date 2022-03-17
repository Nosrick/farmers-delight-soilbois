package com.gitlab.nosrick.soilbois.registry;

import com.gitlab.nosrick.soilbois.block.entity.PantryBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;

import java.util.function.Supplier;

public enum BlockEntityTypeRegistry {

    PANTRY("pantry",
            PantryBlockEntity.class,
            PantryBlockEntity::new,
            BlockRegistry.getPantries());

    protected final String pathName;
    protected final Class<? extends BlockEntity> blockEntityClass;
    protected final Supplier<BlockEntityType<? extends BlockEntity>> blockEntityTypeSupplier;
    protected BlockEntityType<? extends BlockEntity> blockEntityType;

    BlockEntityTypeRegistry(
            String pathName,
            Class<? extends BlockEntity> blockEntityClass,
            FabricBlockEntityTypeBuilder.Factory<? extends BlockEntity> blockEntitySupplier,
            Block... blocks) {

        this.pathName = pathName;
        this.blockEntityClass = blockEntityClass;
        this.blockEntityTypeSupplier = () -> FabricBlockEntityTypeBuilder.create(blockEntitySupplier, blocks).build();
    }

    @SuppressWarnings("unchecked")
    public <T extends BlockEntity> BlockEntityType<T> get() {
        if(this.blockEntityType == null) {
            this.blockEntityType = this.blockEntityTypeSupplier.get();
        }

        return (BlockEntityType<T>) this.blockEntityType;
    }
}
