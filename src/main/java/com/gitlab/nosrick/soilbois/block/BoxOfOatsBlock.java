package com.gitlab.nosrick.soilbois.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class BoxOfOatsBlock extends Block {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    private static final VoxelShape VOXEL_SHAPE = Block.createCuboidShape(
            5d, 0, 6d,
            11d, 8d, 10d);

    public BoxOfOatsBlock() {
        super(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)
                .nonOpaque());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getDefaultState()
                .with(FACING, context.getPlayerFacing().getOpposite());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VOXEL_SHAPE;
    }
}
