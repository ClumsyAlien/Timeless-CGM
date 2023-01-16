package com.tac.guns.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tac.guns.entity.ThrowableGrenadeEntity;
import com.tac.guns.entity.ThrowableStunGrenadeEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Pose;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;

import javax.annotation.Nullable;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class ThrowableGrenadeRenderer extends EntityRenderer<ThrowableGrenadeEntity>
{

    public ThrowableGrenadeRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Nullable
    @Override
    public ResourceLocation getTextureLocation(ThrowableGrenadeEntity entity)
    {
        return null;
    }

    @Override
    public void render(ThrowableGrenadeEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light)
    {
        matrixStack.pushPose();

        /* Makes the grenade face in the direction of travel */
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180F));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(entityYaw));

        /* Offsets to the center of the grenade before applying rotation */
        float rotation = entity.prevRotation + (entity.rotation - entity.prevRotation) * partialTicks;
        matrixStack.translate(0, 0.15, 0);
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(-rotation));
        matrixStack.translate(0, -0.15, 0);

        if(entity instanceof ThrowableStunGrenadeEntity)
        {
            matrixStack.translate(0, entity.getDimensions(Pose.STANDING).height / 2, 0);
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(-90F));
            matrixStack.translate(0, -entity.getDimensions(Pose.STANDING).height / 2, 0);
        }

        /* */
        matrixStack.translate(0.0, 0.5, 0.0);

        Minecraft.getInstance().getItemRenderer().renderStatic(entity.getItem(), ItemTransforms.TransformType.NONE, light, OverlayTexture.NO_OVERLAY, matrixStack, renderTypeBuffer, entity.getId());

        matrixStack.popPose();
    }
}
