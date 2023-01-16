package com.tac.guns.network.message;

import com.mrcrayfish.framework.api.network.PlayMessage;
import com.tac.guns.client.network.ClientPlayHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class MessageBlood extends PlayMessage<MessageBlood>
{
    private double x;
    private double y;
    private double z;

    public MessageBlood() {}

    public MessageBlood(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void encode(MessageBlood messageBlood, FriendlyByteBuf buffer)
    {
        buffer.writeDouble(messageBlood.x);
        buffer.writeDouble(messageBlood.y);
        buffer.writeDouble(messageBlood.z);
    }

    @Override
    public MessageBlood decode(FriendlyByteBuf buffer)
    {
        return new MessageBlood(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
    }

    @Override
    public void handle(MessageBlood messageBlood, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() -> ClientPlayHandler.handleMessageBlood(messageBlood));
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
}
