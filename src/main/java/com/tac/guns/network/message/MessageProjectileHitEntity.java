package com.tac.guns.network.message;

import com.mrcrayfish.framework.api.network.PlayMessage;
import com.tac.guns.client.network.ClientPlayHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class MessageProjectileHitEntity extends PlayMessage<MessageProjectileHitEntity>
{
    private double x;
    private double y;
    private double z;
    private int type;
    private boolean player;

    public MessageProjectileHitEntity() {}

    public MessageProjectileHitEntity(double x, double y, double z, int type, boolean player)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
        this.player = player;
    }

    @Override
    public void encode(MessageProjectileHitEntity messageProjectileHitEntity, FriendlyByteBuf buffer)
    {
        buffer.writeDouble(messageProjectileHitEntity.x);
        buffer.writeDouble(messageProjectileHitEntity.y);
        buffer.writeDouble(messageProjectileHitEntity.z);
        buffer.writeByte(messageProjectileHitEntity.type);
        buffer.writeBoolean(messageProjectileHitEntity.player);
    }

    @Override
    public MessageProjectileHitEntity decode(FriendlyByteBuf buffer)
    {
        return new MessageProjectileHitEntity(
                buffer.readDouble(),
                buffer.readDouble(),
                buffer.readDouble(),
                buffer.readByte(),
                buffer.readBoolean()
        );
    }

    @Override
    public void handle(MessageProjectileHitEntity messageProjectileHitEntity, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() -> ClientPlayHandler.handleProjectileHitEntity(messageProjectileHitEntity));
        supplier.get().setPacketHandled(true);
    }

    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }

    public double getZ()
    {
        return this.z;
    }

    public boolean isHeadshot()
    {
        return this.type == HitType.HEADSHOT;
    }

    public boolean isCritical()
    {
        return this.type == HitType.CRITICAL;
    }

    public boolean isPlayer()
    {
        return this.player;
    }

    public static class HitType
    {
        public static final int NORMAL = 0;
        public static final int HEADSHOT = 1;
        public static final int CRITICAL = 2;
    }
}
