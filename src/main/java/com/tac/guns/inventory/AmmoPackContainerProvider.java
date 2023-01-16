package com.tac.guns.inventory;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import javax.annotation.Nullable;

public class AmmoPackContainerProvider implements MenuProvider {

    private ItemStack item;

    public AmmoPackContainerProvider(ItemStack item) {
        this.item = item;
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("AmmoPack");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
        AmmoPackContainer container = new AmmoPackContainer(windowId, inv, this.item);
        return container;
    }
}
