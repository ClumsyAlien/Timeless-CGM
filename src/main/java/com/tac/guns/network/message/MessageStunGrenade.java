package com.tac.guns.network.message;

import com.mrcrayfish.framework.api.network.PlayMessage;
import com.tac.guns.client.network.ClientPlayHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageStunGrenade extends PlayMessage<MessageStunGrenade>
{
    private double x, y, z;

    public MessageStunGrenade() {}

    public MessageStunGrenade(double x, double y, double z)
    {
        this.z = z;
        this.y = y;
        this.x = x;
    }

    @Override
    public void encode(MessageStunGrenade messageStunGrenade, FriendlyByteBuf buffer)
    {
        buffer.writeDouble(messageStunGrenade.x);
        buffer.writeDouble(messageStunGrenade.y);
        buffer.writeDouble(messageStunGrenade.z);
    }

    @Override
    public MessageStunGrenade decode(FriendlyByteBuf buffer)
    {
        return new MessageStunGrenade(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
    }

    @Override
    public void handle(MessageStunGrenade messageStunGrenade, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() -> ClientPlayHandler.handleExplosionStunGrenade(messageStunGrenade));
        supplier.get().setPacketHandled(true);
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public double getZ()
    {
        return z;
    }
}