package com.tac.guns.network.message;

import com.mrcrayfish.framework.api.network.PlayMessage;
import com.tac.guns.client.network.ClientPlayHandler;
import com.tac.guns.common.Gun;
import com.tac.guns.entity.ProjectileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class MessageBulletTrail extends PlayMessage<MessageBulletTrail>
{
    private int[] entityIds;
    private Vec3[] positions;
    private Vec3[] motions;
    private float[] shooterYaws;
    private float[] shooterPitches;
    private ItemStack item;
    private int trailColor;
    private double trailLengthMultiplier;
    private int life;
    private double gravity;
    private int shooterId;

    public MessageBulletTrail() {}

    public MessageBulletTrail(ProjectileEntity[] spawnedProjectiles, Gun.Projectile projectileProps, int shooterId)
    {
        this.positions = new Vec3[spawnedProjectiles.length];
        this.motions = new Vec3[spawnedProjectiles.length];
        this.shooterYaws = new float[spawnedProjectiles.length];
        this.shooterPitches = new float[spawnedProjectiles.length];
        this.entityIds = new int[spawnedProjectiles.length];
        for(int i = 0; i < spawnedProjectiles.length; i++)
        {
            ProjectileEntity projectile = spawnedProjectiles[i];
            this.positions[i] = projectile.position();
            this.motions[i] = projectile.getDeltaMovement();
            this.shooterYaws[i] = projectile.getShooter().getViewYRot(1);
            this.shooterPitches[i] = projectile.getShooter().getViewXRot(1);
            this.entityIds[i] = projectile.getId();
        }
        this.item = spawnedProjectiles[0].getItem();
        this.trailColor = projectileProps.getTrailColor();
        this.trailLengthMultiplier = projectileProps.getTrailLengthMultiplier();
        this.life = projectileProps.getLife();
        this.gravity = spawnedProjectiles[0].getModifiedGravity(); //It's possible that projectiles have different gravity
        this.shooterId = shooterId;
    }

    @Override
    public void encode(MessageBulletTrail messageBulletTrail, FriendlyByteBuf buffer)
    {
        buffer.writeInt(messageBulletTrail.entityIds.length);
        for(int i = 0; i < messageBulletTrail.entityIds.length; i++)
        {
            buffer.writeInt(messageBulletTrail.entityIds[i]);

            Vec3 position = messageBulletTrail.positions[i];
            buffer.writeDouble(position.x);
            buffer.writeDouble(position.y);
            buffer.writeDouble(position.z);

            Vec3 motion = messageBulletTrail.motions[i];
            buffer.writeDouble(motion.x);
            buffer.writeDouble(motion.y);
            buffer.writeDouble(motion.z);

            buffer.writeFloat(messageBulletTrail.shooterYaws[i]);
            buffer.writeFloat(messageBulletTrail.shooterPitches[i]);
        }
        buffer.writeItem(messageBulletTrail.item);
        buffer.writeVarInt(messageBulletTrail.trailColor);
        buffer.writeDouble(messageBulletTrail.trailLengthMultiplier);
        buffer.writeInt(messageBulletTrail.life);
        buffer.writeDouble(messageBulletTrail.gravity);
        buffer.writeInt(messageBulletTrail.shooterId);
    }

    @Override
    public MessageBulletTrail decode(FriendlyByteBuf buffer)
    {
        int size = buffer.readInt();
        ProjectileEntity[] projectileEntities = new ProjectileEntity[size];
        for(int i = 0; i < size; i++)
        {
            projectileEntities[i] = (ProjectileEntity) Minecraft.getInstance().level.getEntity(buffer.readInt());
            projectileEntities[i].setPos(new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()));
            projectileEntities[i].setDeltaMovement(new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()));
            projectileEntities[i].getShooter().setYRot(buffer.readFloat());
            projectileEntities[i].getShooter().setXRot(buffer.readFloat());
        }
        Gun.Projectile projectile = new Gun.Projectile();
        ItemStack item = buffer.readItem();
        projectile.trailColor = buffer.readVarInt();
        projectile.trailLengthMultiplier = buffer.readDouble();
        projectile.life = buffer.readInt();
        double gravity = buffer.readDouble();
        for (ProjectileEntity projectileEntity : projectileEntities) {
            projectileEntity.setItem(item);
            projectileEntity.modifiedGravity = gravity;
        }
        int shooterId = buffer.readInt();
        return new MessageBulletTrail(projectileEntities, projectile, shooterId);
    }

    @Override
    public void handle(MessageBulletTrail messageBulletTrail, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() -> ClientPlayHandler.handleMessageBulletTrail(messageBulletTrail));
        supplier.get().setPacketHandled(true);
    }

    public int getCount()
    {
        return this.entityIds.length;
    }

    public int[] getEntityIds()
    {
        return this.entityIds;
    }

    public Vec3[] getPositions()
    {
        return this.positions;
    }

    public Vec3[] getMotions()
    {
        return this.motions;
    }

    public int getTrailColor()
    {
        return this.trailColor;
    }

    public double getTrailLengthMultiplier()
    {
        return this.trailLengthMultiplier;
    }

    public int getLife()
    {
        return this.life;
    }

    public ItemStack getItem()
    {
        return this.item;
    }

    public double getGravity()
    {
        return this.gravity;
    }

    public int getShooterId()
    {
        return this.shooterId;
    }

    public float[] getShooterYaws() { return shooterYaws; }

    public float[] getShooterPitches() { return shooterPitches; }
}
