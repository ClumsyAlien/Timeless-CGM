package com.tac.guns.init;

import com.tac.guns.Reference;
import com.tac.guns.common.container.*;
import com.tac.guns.inventory.AmmoPackContainer;
import com.tac.guns.tileentity.UpgradeBenchTileEntity;
import com.tac.guns.tileentity.WorkbenchTileEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class ModContainers
{
    public static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.CONTAINERS, Reference.MOD_ID);

    public static final RegistryObject<MenuType<WorkbenchContainer>> WORKBENCH = register("workbench", (IContainerFactory<WorkbenchContainer>) (windowId, playerInventory, data) -> {
        WorkbenchTileEntity workstation = (WorkbenchTileEntity) playerInventory.player.level.getBlockEntity(data.readBlockPos());
        return new WorkbenchContainer(windowId, playerInventory, workstation);
    });

    public static final RegistryObject<MenuType<AttachmentContainer>> ATTACHMENTS = register("attachments", AttachmentContainer::new);
    
    public static final RegistryObject<MenuType<InspectionContainer>> INSPECTION = register("inspection", InspectionContainer::new);

    public static final RegistryObject<MenuType<ColorBenchContainer>> COLOR_BENCH = register("color_bench", ColorBenchContainer::new);
    public static final RegistryObject<MenuType<UpgradeBenchContainer>> UPGRADE_BENCH = register("upgrade_bench", (IContainerFactory<UpgradeBenchContainer>) (windowId, playerInventory, data) -> {
        UpgradeBenchTileEntity workstation = (UpgradeBenchTileEntity) playerInventory.player.level.getBlockEntity(data.readBlockPos());
        return new UpgradeBenchContainer(windowId, playerInventory, workstation);
    });
    public static final RegistryObject<MenuType<AmmoPackContainer>> AMMOPACK = REGISTER.register("ammopack", () -> new MenuType<>((IContainerFactory<AmmoPackContainer>) (windowId, inv, data) -> new AmmoPackContainer(windowId, inv)));

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String id, MenuType.MenuSupplier<T> factory)
    {
        return REGISTER.register(id, () -> new MenuType<>(factory));
    }
}
