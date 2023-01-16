package com.tac.guns.entity;

import com.tac.guns.Config;
import com.tac.guns.common.Gun;
import com.tac.guns.item.GunItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class MissileEntity extends ProjectileEntity
{
    private float power;
    public MissileEntity(EntityType<? extends ProjectileEntity> entityType, Level worldIn)
    {
        super(entityType, worldIn);
    }

    public MissileEntity(EntityType<? extends ProjectileEntity> entityType, Level worldIn, LivingEntity shooter, ItemStack weapon, GunItem item, Gun modifiedGun, float power)
    {
        super(entityType, worldIn, shooter, weapon, item, modifiedGun);
        this.power = power;
    }

    @Override
    protected void onProjectileTick()
    {
        if (this.level.isClientSide)
        {
            for (int i = 5; i > 0; i--)
            {
                this.level.addParticle(ParticleTypes.CLOUD, true, this.getX() - (this.getDeltaMovement().x() / i), this.getY() - (this.getDeltaMovement().y() / i), this.getZ() - (this.getDeltaMovement().z() / i), 0, 0, 0);
            }
            if (this.level.random.nextInt(2) == 0)
            {
                this.level.addParticle(ParticleTypes.SMOKE, true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
                this.level.addParticle(ParticleTypes.FLAME, true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            }
        }
    }

    @Override
    protected void onHitEntity(Entity entity, Vec3 hitVec, Vec3 startVec, Vec3 endVec, boolean headshot)
    {
        createExplosion(this, this.power*Config.COMMON.missiles.explosionRadius.get().floatValue(), false);
    }

    @Override
    protected void onHitBlock(BlockState state, BlockPos pos, Direction face, double x, double y, double z)
    {
        createExplosion(this, this.power*Config.COMMON.missiles.explosionRadius.get().floatValue(), false);
        this.life = 0;
    }

    @Override
    public void onExpired()
    {
        createExplosion(this, this.power*Config.COMMON.missiles.explosionRadius.get().floatValue(), false);
    }
}
