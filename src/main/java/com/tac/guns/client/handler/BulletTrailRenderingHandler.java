package com.tac.guns.client.handler;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.tac.guns.Config;
import com.tac.guns.client.BulletTrail;
import com.tac.guns.client.GunRenderType;
import com.tac.guns.client.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
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
        //if(Config.CLIENT.particle.)

        // Prevents trails being added when not in a world
        World world = Minecraft.getInstance().world;
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
        World world = Minecraft.getInstance().world;
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

    public void render(MatrixStack stack, float partialSticks)
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

    // TODO: Clean-up this entire method...

    /**
     * @param bulletTrail
     * @param matrixStack
     * @param partialTicks
     */
    private void renderBulletTrail(BulletTrail bulletTrail, MatrixStack matrixStack, float partialTicks)
    {
        Minecraft mc = Minecraft.getInstance();
        Entity entity = mc.getRenderViewEntity();
        if(entity == null || bulletTrail.isDead() || !Config.CLIENT.display.showBulletTrails.get())
            return;
        if(!Config.CLIENT.display.showFirstPersonBulletTrails.get() && Minecraft.getInstance().player.isEntityEqual(entity))
            return;
        matrixStack.push();
        Vector3d view = mc.gameRenderer.getActiveRenderInfo().getProjectedView();
        Vector3d position = bulletTrail.getPosition();
        Vector3d motion = bulletTrail.getMotion();

        double bulletX = position.x + motion.x * partialTicks;
        double bulletY = position.y + motion.y * partialTicks;
        double bulletZ = position.z + motion.z * partialTicks;
        //TODO: Use muzzle flash location of entity render as the render position for muzzle flash start
        Vector3d motionVec = new Vector3d(motion.x, motion.y, motion.z);
        float length = (float) motionVec.length();

        if(Minecraft.getInstance().player.isEntityEqual(entity)) {
            if (mc.player.getLookVec().y > 0.945) // max 1.0
                length *= 0.25;
            else if (mc.player.getLookVec().y > 0.385)
                matrixStack.translate(0, -0.115f * mc.player.getLookVec().y, 0);
        }

        if(ShootingHandler.get().isShooting() && Minecraft.getInstance().player.isEntityEqual(entity) && bulletTrail.getAge() < 1)
        {
            matrixStack.translate(bulletX - (view.getX()), bulletY - view.getY() - 0.145f, (bulletZ - view.getZ()));
            if(AimingHandler.get().isAiming())
            {
                matrixStack.translate(0, -0.685f,0);
            }
        }
        else {
            matrixStack.translate(bulletX - view.getX(), bulletY - view.getY() - 0.125f, bulletZ - view.getZ());

        }
        matrixStack.rotate(Vector3f.YP.rotationDegrees(bulletTrail.getYaw()));
        matrixStack.rotate(Vector3f.XP.rotationDegrees(-bulletTrail.getPitch() + 90.105f));


        float trailLength = (float) ((length / 3f) * bulletTrail.getTrailLengthMultiplier());
        float red = (float) (bulletTrail.getTrailColor() >> 16 & 255) / 255.0F;
        float green = (float) (bulletTrail.getTrailColor() >> 8 & 255) / 255.0F;
        float blue = (float) (bulletTrail.getTrailColor() & 255) / 255.0F;
        float alpha = Config.CLIENT.display.bulletTrailOpacity.get().floatValue();

        // Prevents the trail length from being longer than the distance to shooter
        Entity shooter = bulletTrail.getShooter();
        if(shooter != null && Minecraft.getInstance().player.isEntityEqual(shooter))
        {
            if(AimingHandler.get().getNormalisedAdsProgress() > 0.4)
            {
                trailLength = (float) Math.min(trailLength+0.6f, shooter.getEyePosition(partialTicks).distanceTo(new Vector3d(bulletX,bulletY, bulletZ))/1.175f);
            }
            else
                trailLength = (float) Math.min(trailLength+0.6f, shooter.getEyePosition(partialTicks).distanceTo(new Vector3d(bulletX,bulletY, bulletZ))/1.0975f); ///1.1125f TODO: Add another value per trail to help give a maximum to player eyes
            // distance
        }

        Matrix4f matrix4f = matrixStack.getLast().getMatrix();
        IRenderTypeBuffer.Impl renderTypeBuffer = mc.getRenderTypeBuffers().getBufferSource();
        /*float posSize = 0.1f;
        posSize *= bulletTrail.getSize()*10;
        if(bulletTrail.getAge() < 1 && Minecraft.getInstance().player.isEntityEqual(entity) && !AimingHandler.get().isAiming())
        {
            trailLength*=10; // must be for fps
            RenderType bulletType = GunRenderType.getBulletTrail();
            IVertexBuilder builder = renderTypeBuffer.getBuffer(bulletType);
            //matrixStack.push();
            //builder.pos(matrix4f, 0, trailLength/1.325f, 0).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            //builder.pos(matrix4f, 0, 0, -posSize).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            //builder.pos(matrix4f, -posSize, 0, 0).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            //matrixStack.scale(1.5F, 1.5F, 1.825F);
            matrixStack.translate(GunRenderingHandler.get().sizeZ / 19.25f, -GunRenderingHandler.get().sizeZ / 2, 0);
            //matrixStack.translate(GunRenderingHandler.get().sizeZ / 2, GunRenderingHandler.get().sizeZ / 2, 0);
            matrixStack.translate(GunRenderingHandler.get().displayX, GunRenderingHandler.get().displayY, GunRenderingHandler.get().displayZ);

            // Make customizable?
            matrixStack.translate(0, 0, GunRenderingHandler.get().adjustedTrailZ-0.02);//1.15f);
            //builder.pos(matrix4f, 0, -trailLength/1.325f, 0).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            //builder.pos(matrix4f, 0, 0, posSize).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            //builder.pos(matrix4f, 0, 0, trailLength).color(red, green, blue, alpha).lightmap(15728880).endVertex();

            //builder.pos(matrix4f, 0, 0, 0.225F).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            //builder.pos(matrix4f, 0, 0, -0.225F).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            //builder.pos(matrix4f, 0, trailLength*1.15f, 0).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            //builder.pos(matrix4f, 0, -trailLength*1.15f, 0).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            //builder.pos(matrix4f, 0, 0, -0.225F).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            //builder.pos(matrix4f, 0, 0, 0.225F).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            builder.pos(matrix4f, -0.205F, 0, 0).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            builder.pos(matrix4f, 0.205F, 0, 0).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            builder.pos(matrix4f, 0, -trailLength*1.15f, 0).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            builder.pos(matrix4f, 0, trailLength*1.15f, 0).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            Minecraft.getInstance().getRenderTypeBuffers().getBufferSource().finish(bulletType);
            //matrixStack.pop();
        }*/

        /*if(bulletTrail.isTrailVisible())
        {*/
        if(bulletTrail.getAge() < 1 && Minecraft.getInstance().player.isEntityEqual(entity) && !AimingHandler.get().isAiming())
        {
            RenderType bulletType = GunRenderType.getBulletTrail();
            IVertexBuilder builder = renderTypeBuffer.getBuffer(bulletType);

            // all 0.2f works
            //0.6f static
            float posSize = 0.1f;
            posSize *= bulletTrail.getSize()*10;

            builder.pos(matrix4f, 0, trailLength/1.325f, 0).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            //builder.pos(matrix4f, -posSize, 0, 0).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            builder.pos(matrix4f, 0, 0, -posSize).color(red, green, blue, alpha).lightmap(15728880).endVertex();

            matrixStack.scale(1.5F, 1.5F, 1.825F);
            matrixStack.translate(GunRenderingHandler.get().sizeZ / 17.25f, -GunRenderingHandler.get().sizeZ / 2, 0);
            //matrixStack.translate(GunRenderingHandler.get().sizeZ / 2, GunRenderingHandler.get().sizeZ / 2, 0);
            matrixStack.translate(GunRenderingHandler.get().displayX, GunRenderingHandler.get().displayY, GunRenderingHandler.get().displayZ);
            // Make customizable?
            matrixStack.translate(0, 0, GunRenderingHandler.get().adjustedTrailZ-0.02);//1.15f);

            builder.pos(matrix4f, 0, -trailLength/1.325f, 0).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            //builder.pos(matrix4f, posSize, 0, 0).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            builder.pos(matrix4f, 0, 0, posSize).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            Minecraft.getInstance().getRenderTypeBuffers().getBufferSource().finish(bulletType);
        }
        else {
            RenderType bulletType = GunRenderType.getBulletTrail();
            IVertexBuilder builder = renderTypeBuffer.getBuffer(bulletType);
            builder.pos(matrix4f, 0, 0, 0.225F).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            builder.pos(matrix4f, 0, 0, -0.225F).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            builder.pos(matrix4f, 0, trailLength*1.15f, 0).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            builder.pos(matrix4f, 0, -trailLength*1.15f, 0).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            builder.pos(matrix4f, 0, 0, -0.225F).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            builder.pos(matrix4f, 0, 0, 0.225F).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            builder.pos(matrix4f, -0.225F, 0, 0).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            builder.pos(matrix4f, 0.225F, 0, 0).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            builder.pos(matrix4f, 0, trailLength*1.15f, 0).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            builder.pos(matrix4f, 0, -trailLength*1.15f, 0).color(red, green, blue, alpha).lightmap(15728880).endVertex();
            Minecraft.getInstance().getRenderTypeBuffers().getBufferSource().finish(bulletType);
        }
        if(!bulletTrail.getItem().isEmpty() && !bulletTrail.isTrailVisible())
        {
            matrixStack.rotate(Vector3f.YP.rotationDegrees((bulletTrail.getAge() + partialTicks) * (float) 50));
            matrixStack.scale(0.25F, 0.25F, 0.25F);

            int combinedLight = WorldRenderer.getCombinedLight(entity.world, new BlockPos(entity.getPositionVec()));
            ItemStack stack = bulletTrail.getItem();
            RenderUtil.renderModel(stack, ItemCameraTransforms.TransformType.NONE, matrixStack, renderTypeBuffer, combinedLight, OverlayTexture.NO_OVERLAY, null, null);
        }

        matrixStack.pop();
    }
}
