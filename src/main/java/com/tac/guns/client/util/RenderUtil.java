package com.tac.guns.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tac.guns.common.Gun;
import com.tac.guns.item.ScopeItem;
import com.tac.guns.item.attachment.IAttachment;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.HumanoidArm;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.RenderProperties;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class RenderUtil
{
    public static void scissor(int x, int y, int width, int height)
    {
        Minecraft mc = Minecraft.getInstance();
        int scale = (int) mc.getWindow().getGuiScale();
        GL11.glScissor(x * scale, mc.getWindow().getScreenHeight() - y * scale - height * scale, Math.max(0, width * scale), Math.max(0, height * scale));
    }

    public static BakedModel getModel(Item item)
    {
        return Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(new ItemStack(item));
    }

    public static BakedModel getModel(ItemStack item)
    {
        return Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(item);
    }

    public static void rotateZ(PoseStack matrixStack, float xOffset, float yOffset, float rotation)
    {
        matrixStack.translate(xOffset, yOffset, 0);
        matrixStack.mulPose(Vector3f.ZN.rotationDegrees(rotation));
        matrixStack.translate(-xOffset, -yOffset, 0);
    }

    public static void renderModel(ItemStack stack, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay, @Nullable LivingEntity entity)
    {
        renderModel(stack, ItemTransforms.TransformType.NONE, matrixStack, buffer, light, overlay, entity);
    }

    public static void renderModel(ItemStack child, ItemStack parent, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay)
    {
        BakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(child);
        renderModel(model, ItemTransforms.TransformType.NONE, null, child, parent, matrixStack, buffer, light, overlay);
    }

    public static void renderModel(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay, @Nullable LivingEntity entity)
    {
        BakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(stack);
        if(entity != null)
        {
            model = Minecraft.getInstance().getItemRenderer().getModel(stack, entity.level, entity, entity.getId());
        }
        renderModel(model, transformType, stack, matrixStack, buffer, light, overlay);
    }

    public static void renderModel(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay, @Nullable Level world, @Nullable LivingEntity entity)
    {
        BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(stack, world, entity, entity.getId());
        renderModel(model, transformType, stack, matrixStack, buffer, light, overlay);
    }


    public static void renderModel(BakedModel model, ItemStack stack, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay)
    {
        renderModel(model, ItemTransforms.TransformType.NONE, stack, matrixStack, buffer, light, overlay);
    }

    public static void renderModel(BakedModel model, ItemTransforms.TransformType transformType, ItemStack stack, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay)
    {
        renderModel(model, transformType, null, stack, ItemStack.EMPTY, matrixStack, buffer, light, overlay);
    }

    public static void renderModel(BakedModel model, ItemTransforms.TransformType transformType, @Nullable Transform transform, ItemStack stack, ItemStack parent, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay)
    {
        if(!stack.isEmpty())
        {
            matrixStack.pushPose();
            boolean flag = transformType == ItemTransforms.TransformType.GUI || transformType == ItemTransforms.TransformType.GROUND || transformType == ItemTransforms.TransformType.FIXED;
            if(stack.getItem() == Items.TRIDENT && flag)
            {
                model = Minecraft.getInstance().getModelManager().getModel(new ModelResourceLocation("minecraft:trident#inventory"));
            }

            model = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(matrixStack, model, transformType, false);
            matrixStack.translate(-0.5D, -0.5D, -0.5D);
            if(!model.isCustomRenderer() && (stack.getItem() != Items.TRIDENT || flag))
            {
                boolean flag1;
                if(transformType != ItemTransforms.TransformType.GUI && !transformType.firstPerson() && stack.getItem() instanceof BlockItem)
                {
                    Block block = ((BlockItem) stack.getItem()).getBlock();
                    flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
                }
                else
                {
                    flag1 = true;
                }

                if(model.isLayered())
                {
                    net.minecraftforge.client.ForgeHooksClient.drawItemLayered(Minecraft.getInstance().getItemRenderer(), model, stack, matrixStack, buffer, light, overlay, flag1);
                }
                else
                {
                    RenderType renderType = getRenderType(stack, !flag1);
                    VertexConsumer builder;
                    if(stack.getItem() == Items.COMPASS && stack.hasFoil())
                    {
                        matrixStack.pushPose();
                        PoseStack.Pose entry = matrixStack.last();
                        if(transformType == ItemTransforms.TransformType.GUI)
                        {
                            entry.pose().multiply(0.5F);
                        }
                        else if(transformType.firstPerson())
                        {
                            entry.pose().multiply(0.75F);
                        }

                        if(flag1)
                        {
                            builder = ItemRenderer.getCompassFoilBufferDirect(buffer, renderType, entry);
                        }
                        else
                        {
                            builder = ItemRenderer.getCompassFoilBuffer(buffer, renderType, entry);
                        }

                        matrixStack.popPose();
                    }
                    else if(flag1)
                    {
                        builder = ItemRenderer.getFoilBufferDirect(buffer, renderType, true, stack.hasFoil() || parent.hasFoil());
                    }
                    else
                    {
                        builder = ItemRenderer.getFoilBuffer(buffer, renderType, true, stack.hasFoil() || parent.hasFoil());
                    }

                    renderModel(model, stack, parent, transform, matrixStack, builder, light, overlay);
                }
            }
            else
            {
                RenderProperties.get(stack.getItem()).getItemStackRenderer().renderByItem(stack, transformType, matrixStack, buffer, light, overlay);
            }

            matrixStack.popPose();
        }
    }

    /**
     * @param model
     * @param stack
     * @param parent
     * @param transform
     * @param matrixStack
     * @param buffer
     * @param light
     * @param overlay
     */
    private static void renderModel(BakedModel model, ItemStack stack, ItemStack parent, @Nullable Transform transform, PoseStack matrixStack, VertexConsumer buffer, int light, int overlay)
    {
        if(transform != null)
        {
            transform.apply();
        }
        Random random = new Random();
        for(Direction direction : Direction.values())
        {
            random.setSeed(42L);
            renderQuads(matrixStack, buffer, model.getQuads(null, direction, random), stack, parent, light, overlay);
        }
        random.setSeed(42L);
        renderQuads(matrixStack, buffer, model.getQuads(null, null, random), stack, parent, light, overlay);
    }

    /*
    private static void renderQuads(MatrixStack matrixStack, IVertexBuilder buffer, List<BakedQuad> quads, ItemStack stack, ItemStack parent, int light, int overlay)
    {
        MatrixStack.Entry entry = matrixStack.getLast();
        for(BakedQuad quad : quads)
        {
            boolean keepColor = true;
            int color = -1;
            if(quad.hasTintIndex())
            {
                //if(getItemStackColor(stack, parent, IAttachment.Type.SCOPE_BODY_COLOR, quad.getTintIndex()) != 0)
                    color = getItemStackColor(stack, parent, IAttachment.Type.SCOPE_BODY_COLOR, quad.getTintIndex());

                if(stack.getItem() instanceof ScopeItem && getItemStackColor(stack, parent, IAttachment.Type.SCOPE_GLASS_COLOR, quad.getTintIndex()) != 0) {
                    color = getItemStackColor(stack, parent, IAttachment.Type.SCOPE_GLASS_COLOR, quad.getTintIndex());
                    overlay=1;
                    keepColor = false;
                }
            }
            float red = (float) (color >> 16 & 255) / 255.0F;
            float green = (float) (color >> 8 & 255) / 255.0F;
            float blue = (float) (color & 255) / 255.0F;
            buffer.addVertexData(entry, quad, red, green, blue, light, overlay, keepColor);
        }
    }
     */

    /**
     * @param matrixStack
     * @param buffer
     * @param quads
     * @param stack
     * @param parent
     * @param light
     * @param overlay
     */
    private static void renderQuads(PoseStack matrixStack, VertexConsumer buffer, List<BakedQuad> quads, ItemStack stack, ItemStack parent, int light, int overlay)
    {
        PoseStack.Pose entry = matrixStack.last();
        for(BakedQuad quad : quads)
        {
            float alpha = 1f;
            boolean keepColor = true;
            int color = -1;
            if(quad.isTinted())
            {
                color = getItemStackColor(stack, parent, IAttachment.Type.SCOPE_BODY_COLOR, quad.getTintIndex());
                keepColor = true;
            }
            if(quad.isTinted() && stack.getItem() instanceof ScopeItem && quad.getTintIndex() == 1)
            {
                color = getItemStackColor(stack, parent, IAttachment.Type.SCOPE_GLASS_COLOR, quad.getTintIndex());
                alpha = 2f;
                keepColor = false;
            }
            float red = (float) (color >> 16 & 255) / 255.0F;
            float green = (float) (color >> 8 & 255) / 255.0F;
            float blue = (float) (color & 255) / 255.0F;

            buffer.putBulkData(entry, quad, red, green, blue, alpha, light, overlay, keepColor);
        }
    }

    public static int getItemStackColor(ItemStack stack, ItemStack parent, int tintIndex)
    {
        int color = Minecraft.getInstance().getItemColors().getColor(stack, tintIndex);
        if(color == -1)
        {
            if(!parent.isEmpty())
            {
                return getItemStackColor(parent, ItemStack.EMPTY, tintIndex);
            }
        }
        if(stack != null && !Gun.getAttachment(IAttachment.Type.SCOPE_BODY_COLOR, stack).isEmpty())
        {

            float[] textureDiffuseColors = ((DyeItem) Gun.getAttachment(IAttachment.Type.SCOPE_BODY_COLOR, stack).getItem()).getDyeColor().getTextureDiffuseColors();
            color = ((int)(textureDiffuseColors[2] * 255)) << 16 | ((int)(textureDiffuseColors[1] * 255)) << 8 | ((int)(textureDiffuseColors[0] * 255));
        }
        return color;
    }
    public static int getItemStackColor(ItemStack stack, ItemStack parent, IAttachment.Type attachmentType, int tintIndex)
    {
        int color = getItemStackColor(stack,parent,tintIndex);

        if(stack != null && !Gun.getAttachment(attachmentType, stack).isEmpty())
        {
            if(Gun.getAttachment(attachmentType, stack).getItem() instanceof DyeItem) {
                float[] textureDiffuseColors = ((DyeItem) Gun.getAttachment(attachmentType, stack).getItem()).getDyeColor().getTextureDiffuseColors();
                color = ((int)(textureDiffuseColors[2] * 255)) << 16 | ((int)(textureDiffuseColors[1] * 255)) << 8 | ((int)(textureDiffuseColors[0] * 255));;
            }
        }
        return color;
    }

    public static void applyTransformType(ItemStack stack, PoseStack matrixStack, ItemTransforms.TransformType transformType, LivingEntity entity)
    {
        BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(stack, entity.level, entity, entity.getId());
        boolean leftHanded = transformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND || transformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND;
        ForgeHooksClient.handleCameraTransforms(matrixStack, model, transformType, leftHanded);

        /* Flips the model and normals if left handed. */
        if(leftHanded)
        {
            Matrix4f scale = Matrix4f.createScaleMatrix(-1, 1, 1);
            Matrix3f normal = new Matrix3f(scale);
            matrixStack.last().pose().multiply(scale);
            matrixStack.last().normal().mul(normal);
        }
    }

    public static void applyTransformTypeIB(BakedModel model, PoseStack matrixStack, ItemTransforms.TransformType transformType, LivingEntity entity)
    {
        /*IBakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelWithOverrides(stack, entity.world, entity);*/
        boolean leftHanded = transformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND || transformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND;
        ForgeHooksClient.handleCameraTransforms(matrixStack, model, transformType, leftHanded);

        /* Flips the model and normals if left handed. */
        if(leftHanded)
        {
            Matrix4f scale = Matrix4f.createScaleMatrix(-1, 1, 1);
            Matrix3f normal = new Matrix3f(scale);
            matrixStack.last().pose().multiply(scale);
            matrixStack.last().normal().mul(normal);
        }
    }

    public interface Transform
    {
        void apply();
    }

    public static boolean isMouseWithin(int mouseX, int mouseY, int x, int y, int width, int height)
    {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    public static void renderFirstPersonArm(LocalPlayer player, HumanoidArm hand, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight)
    {
        Minecraft mc = Minecraft.getInstance();
        EntityRenderDispatcher renderManager = mc.getEntityRenderDispatcher();
        PlayerRenderer renderer = (PlayerRenderer) renderManager.getRenderer(player);
        RenderSystem.setShaderTexture(0, player.getSkinTextureLocation());
        if(hand == HumanoidArm.RIGHT)
        {
            renderer.renderRightHand(matrixStack, buffer, combinedLight, player);
        }
        else
        {
            renderer.renderLeftHand(matrixStack, buffer, combinedLight, player);
        }
    }

    public static RenderType getRenderType(ItemStack stack, boolean entity)
    {
        Item item = stack.getItem();
        if(item instanceof BlockItem)
        {
            Block block = ((BlockItem) item).getBlock();
            return ItemBlockRenderTypes.getRenderType(block.defaultBlockState(), !entity);
        }
        return entity ? Sheets.translucentItemSheet() : RenderType.entityTranslucent(InventoryMenu.BLOCK_ATLAS);
    }
}