package com.tac.guns.init;

import com.tac.guns.Reference;
import com.tac.guns.tileentity.FlashLightSource;
import com.tac.guns.tileentity.UpgradeBenchTileEntity;
import com.tac.guns.tileentity.WorkbenchTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class ModTileEntities
{
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Reference.MOD_ID);

    public static final RegistryObject<BlockEntityType<WorkbenchTileEntity>> WORKBENCH = register("workbench", WorkbenchTileEntity::new, () -> new Block[]{ModBlocks.WORKBENCH.get()});

    //public static final RegistryObject<TileEntityType<UpgradeBenchTileEntity>> UPGRADE_BENCH = registers("upgrade_benchy", UpgradeBenchTileEntity::new, () -> ModBlocks.UPGRADE_BENCH.get());
    public static final RegistryObject<BlockEntityType<FlashLightSource>> LIGHT_SOURCE = register("flashlight",FlashLightSource::new, () -> new Block[]{ModBlocks.FLASHLIGHT_BLOCK.get()});

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> registers(String id, BlockEntityType.BlockEntitySupplier<T> factoryIn, Supplier<Block> validBlocksSupplier)
    {
        return REGISTER.register(id, () -> BlockEntityType.Builder.of(factoryIn, validBlocksSupplier.get()).build(null));
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String id, BlockEntityType.BlockEntitySupplier<T> factoryIn, Supplier<Block[]> validBlocksSupplier)
    {
        return REGISTER.register(id, () -> BlockEntityType.Builder.of(factoryIn, validBlocksSupplier.get()).build(null));
    }
}
