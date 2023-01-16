package com.tac.guns.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class BulletTrail
{
    private int entityId;
    private Vec3 position;
    private Vec3 motion;
    private float yaw;
    private float pitch;
    private boolean dead;
    private ItemStack item;
    private int trailColor;
    private double trailLengthMultiplier;
    private int age;

    public int getMaxAge() {
        return maxAge;
    }

    private int maxAge;
    private double gravity;
    private int shooterId;
    private WeakReference<Entity> shooter;

    private float shooterYaw;

    private float shooterPitch;

    public BulletTrail(int entityId, Vec3 position, Vec3 motion, float shooterYaw, float shooterPitch, ItemStack item, int trailColor, double trailMultiplier, int maxAge, double gravity, int shooterId)
    {
        this.entityId = entityId;
        this.position = position;
        this.motion = motion;
        this.item = item;
        this.trailColor = trailColor;
        this.trailLengthMultiplier = trailMultiplier;
        this.maxAge = maxAge;
        this.gravity = gravity;
        this.shooterId = shooterId;
        this.updateYawPitch();
        this.shooterYaw = shooterYaw;
        this.shooterPitch = shooterPitch;
    }

    private void updateYawPitch()
    {
        float horizontalLength = Mth.sqrt((float) (this.motion.x * this.motion.x + this.motion.z * this.motion.z));
        this.yaw = (float) Math.toDegrees(Mth.atan2(this.motion.x, this.motion.z));
        this.pitch = (float) Math.toDegrees(Mth.atan2(this.motion.y, (double) horizontalLength));
    }

    public void tick()
    {
        this.age++;

        this.position = this.position.add(this.motion);

        if(this.gravity != 0)
        {
            this.motion = this.motion.add(0, this.gravity, 0);
            this.updateYawPitch();
        }

        Entity entity = Minecraft.getInstance().getCameraEntity();
        double distance = entity != null ? Math.sqrt(entity.distanceToSqr(this.position)) : Double.MAX_VALUE;
        if(this.age >= this.maxAge || distance > 256)
        {
            this.dead = true;
        }
    }

    public int getEntityId()
    {
        return this.entityId;
    }

    public Vec3 getPosition()
    {
        return this.position;
    }

    public Vec3 getMotion()
    {
        return this.motion;
    }

    public float getYaw()
    {
        return this.yaw;
    }

    public float getPitch()
    {
        return this.pitch;
    }

    public boolean isDead()
    {
        return this.dead;
    }

    public int getAge()
    {
        return this.age;
    }

    public ItemStack getItem()
    {
        return this.item;
    }

    public int getTrailColor()
    {
        return this.trailColor;
    }

    public double getTrailLengthMultiplier()
    {
        return this.trailLengthMultiplier;
    }

    public int getShooterId()
    {
        return this.shooterId;
    }

    public float getShooterYaw() { return shooterYaw; }

    public float getShooterPitch() { return shooterPitch; }

    /**
     * Gets the instance of the entity that shot the bullet. The entity is cached to avoid searching
     * for it every frame, especially when lots of bullet trails are being rendered.
     *
     * @return the shooter entity
     */
    @Nullable
    public Entity getShooter()
    {
        if(this.shooter == null)
        {
            Level world = Minecraft.getInstance().level;
            if(world != null)
            {
                Entity entity = world.getEntity(this.shooterId);
                if(entity != null)
                {
                    this.shooter = new WeakReference<>(entity);
                }
            }
        }
        if(this.shooter != null)
        {
            Entity entity = this.shooter.get();
            if(entity != null && !entity.isAlive())
            {
                return null;
            }
            return entity;
        }
        return null;
    }

    public boolean isTrailVisible()
    {
        Entity entity = Minecraft.getInstance().getCameraEntity();
        return entity != null/* && entity.getEntityId() != this.shooterId*/;
    }

    @Override
    public int hashCode()
    {
        return this.entityId;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof BulletTrail)
        {
            return ((BulletTrail) obj).entityId == this.entityId;
        }
        return false;
    }
}
