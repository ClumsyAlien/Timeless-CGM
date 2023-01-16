package com.tac.guns.util.math;

import com.tac.guns.entity.ProjectileEntity;
import net.minecraft.world.phys.EntityHitResult;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class ExtendedEntityRayTraceResult extends EntityHitResult
{
    private final boolean headshot;

    public ExtendedEntityRayTraceResult(ProjectileEntity.EntityResult result)
    {
        super(result.getEntity(), result.getHitPos());
        this.headshot = result.isHeadshot();
    }

    public boolean isHeadshot()
    {
        return this.headshot;
    }
}
