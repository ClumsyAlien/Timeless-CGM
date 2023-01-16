package com.tac.guns.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tac.guns.client.BulletTrail;
import com.tac.guns.client.GunRenderType;
import com.tac.guns.client.util.RenderUtil;
import com.tac.guns.util.OptifineHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import com.mojang.math.Matrix4f;
import net.minecraft.world.phys.Vec3;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class BulletTrailRenderingHandler
{
    private static BulletTrailRenderingHandler instance;

    public static BulletTrailRenderingHandler get()
    {
        if(instance == null)
        {
            instance = new BulletTrailRenderingHandler();
        }
        return instance;
    }

    private Map<Integer, BulletTrail> bullets = new HashMap<>();

    private BulletTrailRenderingHandler() {}

    /**
     * Adds a bullet trail to render into the world
     *
     * @param trail the bullet trail get
     */
    public void add(BulletTrail trail)
    {
        // Prevents trails being added when not in a world
        Level world = Minecraft.getInstance().level;
        if(world != null)
        {
            this.bullets.put(trail.getEntityId(), trail);
        }
    }

    /**
     * Removes the bullet for the given entity id.
     *
     * @param entityId the entity id of the bullet
     */
    public void remove(int entityId)
    {
        this.bullets.remove(entityId);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        Level world = Minecraft.getInstance().level;
        if(world != null)
        {
            if(event.phase == TickEvent.Phase.END)
            {
                this.bullets.values().forEach(BulletTrail::tick);
                this.bullets.values().removeIf(BulletTrail::isDead);
            }
        }
        else if(!this.bullets.isEmpty())
        {
            this.bullets.clear();
        }
    }

    public void render(PoseStack stack, float partialSticks)
    {
        for(BulletTrail bulletTrail : this.bullets.values())
        {
            this.renderBulletTrail(bulletTrail, stack, partialSticks);
        }
    }

    @SubscribeEvent
    public void onRespawn(ClientPlayerNetworkEvent.RespawnEvent event)
    {
        this.bullets.clear();
    }

    @SubscribeEvent
    public void onLoggedOut(ClientPlayerNetworkEvent.LoggedOutEvent event)
    {
        this.bullets.clear();
    }

    /**
     * @param bulletTrail
     * @param matrixStack
     * @param partialTicks
     */
    private void renderBulletTrail(BulletTrail bulletTrail, PoseStack matrixStack, float partialTicks)
    {
        if(OptifineHelper.isShadersEnabled())
            return;

        Minecraft mc = Minecraft.getInstance();
        Entity entity = mc.getCameraEntity();
        if(entity == null || bulletTrail.isDead() || bulletTrail.getAge() < 2)
            return;

        matrixStack.pushPose();

        Vec3 view = mc.gameRenderer.getMainCamera().getPosition();
        Vec3 position = bulletTrail.getPosition();
        Vec3 motion = bulletTrail.getMotion();
        double bulletX = position.x + motion.x * partialTicks;
        double bulletY = position.y + motion.y * partialTicks;
        double bulletZ = position.z + motion.z * partialTicks;
        //matrixStack.translate(bulletX - view.getX(), bulletY - view.getY(), bulletZ - view.getZ());

       // matrixStack.rotate(Vector3f.YP.rotationDegrees(-bulletTrail.getYaw() - bulletTrail.getShooterYaw()));
       // matrixStack.rotate(Vector3f.XP.rotationDegrees(bulletTrail.getPitch() + bulletTrail.getShooterPitch()));

        Vec3 motionVec = new Vec3(motion.x, motion.y, motion.z);
        float trailLength = (float) ((motionVec.length() / 3.0F) * bulletTrail.getTrailLengthMultiplier());
        float red = (float) (bulletTrail.getTrailColor() >> 16 & 255) / 255.0F;
        float green = (float) (bulletTrail.getTrailColor() >> 8 & 255) / 255.0F;
        float blue = (float) (bulletTrail.getTrailColor() & 255) / 255.0F;
        float alpha = 0.3F;

        // Prevents the trail length from being longer than the distance to shooter
        Entity shooter = bulletTrail.getShooter();
        if(shooter != null)
        {
            trailLength = (float) Math.min(trailLength, shooter.getEyePosition(partialTicks).distanceTo(new Vec3(bulletX,bulletY, bulletZ)));
        }

        Matrix4f matrix4f = matrixStack.last().pose();
        MultiBufferSource.BufferSource renderTypeBuffer = mc.renderBuffers().bufferSource();

        if(bulletTrail.isTrailVisible())
        {
/*
            RenderType bulletType = GunRenderType.getBulletTrail();
            IVertexBuilder builder = renderTypeBuffer.getBuffer(bulletType);
            builder.pos(matrix4f, 0, 0, -0.035F).color(red, green, blue, alpha).endVertex();
            builder.pos(matrix4f, 0, 0, 0.035F).color(red, green, blue, alpha).endVertex();
            builder.pos(matrix4f, 0, -trailLength, 0.035F).color(red, green, blue, alpha).endVertex();
            builder.pos(matrix4f, 0, -trailLength, -0.035F).color(red, green, blue, alpha).endVertex();
            builder.pos(matrix4f, -0.035F, 0, 0).color(red, green, blue, alpha).endVertex();
            builder.pos(matrix4f, 0.035F, 0, 0).color(red, green, blue, alpha).endVertex();
            builder.pos(matrix4f, 0.035F, -trailLength, 0).color(red, green, blue, alpha).endVertex();
            builder.pos(matrix4f, -0.035F, -trailLength, 0).color(red, green, blue, alpha).endVertex();
            Minecraft.getInstance().getRenderTypeBuffers().getBufferSource().finish(bulletType);
            */

            RenderType bulletType = GunRenderType.getBulletTrail();
            VertexConsumer buffer1 = renderTypeBuffer.getBuffer(bulletType);
            buffer1.vertex(matrix4f, 0, -0.035F, 0).color(red, green, blue, alpha).endVertex();
            buffer1.vertex(matrix4f, 0, 0.035F, 0).color(red, green, blue, alpha).endVertex();
            buffer1.vertex(matrix4f, 0, 0.035F, -trailLength).color(red, green, blue, alpha).endVertex();
            buffer1.vertex(matrix4f, 0, -0.035F, -trailLength).color(red, green, blue, alpha).endVertex();
            buffer1.vertex(matrix4f, -0.035F, 0, 0).color(red, green, blue, alpha).endVertex();
            buffer1.vertex(matrix4f, 0.035F, 0, 0).color(red, green, blue, alpha).endVertex();
            buffer1.vertex(matrix4f, 0.035F, 0, -trailLength).color(red, green, blue, alpha).endVertex();
            buffer1.vertex(matrix4f, -0.035F, 0, -trailLength).color(red, green, blue, alpha).endVertex();
            Minecraft.getInstance().renderBuffers().bufferSource().endBatch(bulletType);
        }

        if(!bulletTrail.getItem().isEmpty())
        {
            matrixStack.mulPose(Vector3f.YP.rotationDegrees((bulletTrail.getAge() + partialTicks) * (float) 50));
            matrixStack.scale(0.275F, 0.275F, 0.275F);

            int combinedLight = LevelRenderer.getLightColor(entity.level, new BlockPos(entity.position()));
            ItemStack stack = bulletTrail.getItem();
            RenderUtil.renderModel(stack, ItemTransforms.TransformType.NONE, matrixStack, renderTypeBuffer, combinedLight, OverlayTexture.NO_OVERLAY, null, null);
        }

        matrixStack.popPose();
    }
}
