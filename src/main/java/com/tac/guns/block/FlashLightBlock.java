package com.tac.guns.block;

import com.tac.guns.init.ModTileEntities;
import com.tac.guns.tileentity.FlashLightSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.Material.Builder;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class FlashLightBlock extends AirBlock implements EntityBlock
{
    public static final Material flashLightBlock;

    public FlashLightBlock() {
        super(Properties.of(flashLightBlock).noCollission().noDrops().air().instabreak().lightLevel((p_235470_0_) -> {
            return 15;
        }));
    }

    @Override
    public int getLightBlock(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 15;
    }
    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FlashLightSource(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> type) {
        return type == ModTileEntities.LIGHT_SOURCE.get() ? FlashLightSource::tick : null;
    }

    static {
        flashLightBlock = (new Builder(MaterialColor.NONE)).noCollider().nonSolid().build();
    }
}
