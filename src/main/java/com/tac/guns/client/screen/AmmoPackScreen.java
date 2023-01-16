package com.tac.guns.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tac.guns.inventory.AmmoPackContainer;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class AmmoPackScreen extends AbstractContainerScreen<AmmoPackContainer> implements MenuAccess<AmmoPackContainer> {
    private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
    private final int inventoryRows;

    public AmmoPackScreen(AmmoPackContainer container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);
        this.passEvents = false;
        int i = 222;
        int j = 114;
        this.inventoryRows = container.getNumRows();
        this.imageHeight = 114 + this.inventoryRows * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        //this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        //this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
        /*RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.blit(matrixStack, i, j + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);*/
    }
}