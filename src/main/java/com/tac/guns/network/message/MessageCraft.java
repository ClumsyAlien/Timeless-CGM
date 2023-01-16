package com.tac.guns.network.message;

import com.mrcrayfish.framework.api.network.PlayMessage;
import com.tac.guns.common.network.ServerPlayHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class MessageCraft extends PlayMessage<MessageCraft>
{
    private ResourceLocation id;
    private BlockPos pos;

    public MessageCraft() {}

    public MessageCraft(ResourceLocation id, BlockPos pos)
    {
        this.id = id;
        this.pos = pos;
    }

    @Override
    public void encode(MessageCraft messageCraft, FriendlyByteBuf buffer)
    {
        buffer.writeResourceLocation(messageCraft.id);
        buffer.writeBlockPos(messageCraft.pos);
    }

    @Override
    public MessageCraft decode(FriendlyByteBuf buffer)
    {
        return new MessageCraft(buffer.readResourceLocation(), buffer.readBlockPos());
    }

    @Override
    public void handle(MessageCraft messageCraft, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer player = supplier.get().getSender();
            if(player != null)
            {
                ServerPlayHandler.handleCraft(player, messageCraft.id, messageCraft.pos);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
