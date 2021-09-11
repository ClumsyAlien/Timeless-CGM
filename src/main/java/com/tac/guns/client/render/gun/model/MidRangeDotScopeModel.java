package com.tac.guns.client.render.gun.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tac.guns.client.handler.AimingHandler;
import com.tac.guns.client.render.gun.IOverrideModel;
import com.tac.guns.client.util.RenderUtil;
import com.tac.guns.util.OptifineHelper;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

/**
 * Author: MrCrayfish
 */
public class MidRangeDotScopeModel implements IOverrideModel
{
    //private static final ResourceLocation RED_DOT_RETICLE = new ResourceLocation(Reference.MOD_ID, "textures/effect/red_dot_reticle.png");
    //private static final ResourceLocation RED_DOT_RETICLE_GLOW = new ResourceLocation(Reference.MOD_ID, "textures/effect/red_dot_reticle_glow.png");
    //private static final ResourceLocation VIGNETTE = new ResourceLocation(Reference.MOD_ID, "textures/effect/scope_vignette.png");

    @Override
    public void render(float partialTicks, ItemCameraTransforms.TransformType transformType, ItemStack stack, ItemStack parent, LivingEntity entity, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, int overlay) {
        if (OptifineHelper.isShadersEnabled()) {
            double transition = 1.0D - Math.pow(1.0D - AimingHandler.get().getNormalisedAdsProgress(), 2.0D);
            double zScale = 0.05D + 0.95D * (1.0D - transition);
            matrixStack.scale(1.0F, 1.0F, (float)zScale);
        }

        //Gun.ScaledPositioned

        matrixStack.scale(0.65f,0.65f,0.65f);
        matrixStack.translate(0,0.25,0);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(90));

        RenderUtil.renderModel(stack, parent, matrixStack, renderTypeBuffer, light, overlay);

        matrixStack.translate(0, -1.25, 0);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(-90));
        matrixStack.scale(1.45f,1.45f,1.45f);
    }
}
