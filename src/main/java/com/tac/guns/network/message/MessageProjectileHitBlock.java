package com.tac.guns.network.message;

import com.tac.guns.client.network.ClientPlayHandler;
import com.tac.guns.common.Gun;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.fml.network.NetworkEvent;
import com.tac.guns.entity.ProjectileEntity;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class MessageProjectileHitBlock implements IMessage
{
    private BlockRayTraceResult result;
    private ProjectileEntity bullet;

    public MessageProjectileHitBlock() {}

    public MessageProjectileHitBlock(BlockRayTraceResult blockRayTraceResult, ProjectileEntity entity)
    {
        this.result = blockRayTraceResult;
        this.bullet = entity;
    }

    @Override
    public void encode(PacketBuffer buffer)
    {
        buffer.writeBlockHitResult(this.result);
    }

    @Override
    public void decode(PacketBuffer buffer)
    {
        this.result = buffer.readBlockHitResult();
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() -> ClientPlayHandler.handleProjectileHitBlock(this));
        supplier.get().setPacketHandled(true);
    }

    public BlockRayTraceResult getResult()
    {
        return this.result;
    }
    public ProjectileEntity getBullet()
    {
        return this.bullet;
    }
}
