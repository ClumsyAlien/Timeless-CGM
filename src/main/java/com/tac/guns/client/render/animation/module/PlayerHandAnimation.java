package com.tac.guns.client.render.animation.module;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tac.guns.client.handler.GunRenderingHandler;
import com.tac.guns.client.handler.command.ObjectRenderEditor;
import com.tac.guns.client.util.RenderUtil;
import com.tac.guns.common.Gun;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.entity.HumanoidArm;
import com.mojang.math.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Vector;

@OnlyIn(Dist.CLIENT)
public class PlayerHandAnimation {
    public static void render(GunAnimationController controller, ItemTransforms.TransformType transformType, PoseStack matrices, MultiBufferSource renderBuffer, int light){
        if(!transformType.firstPerson()) return;
        matrices.pushPose();
        {
            controller.applyRightHandTransform(matrices);
            RenderUtil.renderFirstPersonArm(Minecraft.getInstance().player, HumanoidArm.RIGHT, matrices, renderBuffer, light);
        }
        matrices.popPose();
        matrices.pushPose();
        {
            if(GunRenderingHandler.get() != null)
            {
                /*if(ObjectRenderEditor.get() != null && ObjectRenderEditor.get().GetFromElements(1) != null)
                {
                    element = ObjectRenderEditor.get().GetFromElements(1);
                }*/

                //When performing a tactical sprint, apply additional actions
                if(GunRenderingHandler.get().wSpeed > 0.09) {
                    ObjectRenderEditor.RENDER_Element element =
                            new ObjectRenderEditor.RENDER_Element(0,0,0.25f,0);
                    float transition = GunRenderingHandler.get().sOT;

                    float result = GunRenderingHandler.get().sprintDynamicsHSSLeftHand.update(0.15f, transition);
                    //Reverse the left arm rotation
                    matrices.mulPose(Vector3f.XP.rotationDegrees(-90F * result));
                    matrices.mulPose(Vector3f.ZP.rotationDegrees(25f * result));
                    matrices.translate(-1.2 * /*leftHanded **/ result ,
                            -0.8 * result ,
                            0);
                }
            }
            controller.applyLeftHandTransform(matrices);
            RenderUtil.renderFirstPersonArm(Minecraft.getInstance().player, HumanoidArm.LEFT, matrices, renderBuffer, light);
        }
        matrices.popPose();
    }
}
