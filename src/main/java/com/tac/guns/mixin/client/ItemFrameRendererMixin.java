package com.tac.guns.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tac.guns.client.event.RenderItemEvent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemFrameRenderer.class)
public class ItemFrameRendererMixin {

    private ItemFrame frame;
    private float deltaTick;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderStatic(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;IILcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
    public void capture(ItemFrame frame, float entityYaw, float deltaTick, PoseStack poseStack, MultiBufferSource source, int light, CallbackInfo ci)
    {
        this.frame = frame;
        this.deltaTick = deltaTick;
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderStatic(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;IILcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
    public void fireRenderItemFrameItem(ItemRenderer renderer, ItemStack stack, ItemTransforms.TransformType transformType, int light, int overlay, PoseStack poseStack, MultiBufferSource source, int id)
    {
        if(!MinecraftForge.EVENT_BUS.post(new RenderItemEvent.ItemFrame.Pre(frame, stack, poseStack, source, light, overlay, deltaTick)))
        {
            renderer.renderStatic(stack, transformType, light, overlay, poseStack, source, frame.getId());
            MinecraftForge.EVENT_BUS.post(new RenderItemEvent.ItemFrame.Post(frame, stack, poseStack, source, light, overlay, deltaTick));
        }
    }

}
