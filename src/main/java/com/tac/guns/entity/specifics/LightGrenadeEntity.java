package com.tac.guns.entity.specifics;

import com.tac.guns.Config;
import com.tac.guns.entity.GrenadeEntity;
import com.tac.guns.entity.ThrowableGrenadeEntity;
import com.tac.guns.entity.ThrowableItemEntity;
import com.tac.guns.init.ModEntities;
import com.tac.guns.init.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class LightGrenadeEntity extends ThrowableGrenadeEntity
{
    public LightGrenadeEntity(EntityType<? extends ThrowableItemEntity> entityType, Level worldIn)
    {
        super(entityType, worldIn);
    }

    public LightGrenadeEntity(EntityType<? extends ThrowableItemEntity> entityType, Level world, LivingEntity entity)
    {
        super(entityType, world, entity);
        this.setShouldBounce(true);
        this.setGravityVelocity(0.03F);
        this.setItem(new ItemStack(ModItems.LIGHT_GRENADE.get()));
        //this.setMaxLife(20 * 2);
    }

    public LightGrenadeEntity(Level world, LivingEntity entity, int timeLeft, float power)
    {
        super(ModEntities.THROWABLE_GRENADE.get(), world, entity);
        this.power = power;
        this.setShouldBounce(true);
        this.setItem(new ItemStack(ModItems.LIGHT_GRENADE.get()));
        this.setMaxLife(timeLeft);
        this.setGravityVelocity(0.0325F);
    }

    @Override
    public void tick()
    {
        super.tick();
        this.prevRotation = this.rotation;
        double speed = this.getDeltaMovement().length();
        if (speed > 0.1)
        {
            this.rotation += speed * 325;
        }
        if (this.level.isClientSide)
        {
            this.level.addParticle(ParticleTypes.SMOKE, true, this.getX(), this.getY() + 0.25, this.getZ(), 0, 0.135, 0);
        }
    }
}
