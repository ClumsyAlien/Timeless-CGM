package com.tac.guns.tileentity;

import com.tac.guns.init.ModTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FlashLightSource extends BlockEntity
{

    public FlashLightSource(BlockPos pPos, BlockState pBlockState) {
        super(ModTileEntities.LIGHT_SOURCE.get(), pPos, pBlockState);
    }

    public static int ticks;
    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        ticks++;
        if (ticks > 4) {
            blockEntity.getLevel().setBlock(blockEntity.getBlockPos(), Blocks.AIR.defaultBlockState(), 3);
            blockEntity.getLevel().removeBlockEntity(blockEntity.getBlockPos());
        }
    }


}