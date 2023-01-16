package com.tac.guns.network.message;

import com.mrcrayfish.framework.api.network.PlayMessage;
import com.tac.guns.client.network.ClientPlayHandler;
import com.tac.guns.common.network.ServerPlayHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class MessageSaveItemUpgradeBench extends PlayMessage<MessageSaveItemUpgradeBench>
{
    private BlockPos pos;

    public MessageSaveItemUpgradeBench() {
    }
    public MessageSaveItemUpgradeBench(BlockPos pos)
    {
        this.pos = pos;
    }

    @Override
    public void encode(MessageSaveItemUpgradeBench messageSaveItemUpgradeBench, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(messageSaveItemUpgradeBench.pos);
    }

    @Override
    public MessageSaveItemUpgradeBench decode(FriendlyByteBuf buffer) {
        return new MessageSaveItemUpgradeBench(buffer.readBlockPos());
    }

    @Override
    public void handle(MessageSaveItemUpgradeBench messageSaveItemUpgradeBench, Supplier<NetworkEvent.Context> supplier)
    {

        supplier.get().enqueueWork(() ->
        {
            ServerPlayer player = supplier.get().getSender();
            if(player != null)
            {
                supplier.get().enqueueWork(() -> ServerPlayHandler.handleUpgradeBenchItem(messageSaveItemUpgradeBench, player));
            }
        });
        supplier.get().setPacketHandled(true);
    }
    public BlockPos getPos() {
        return this.pos;
    }
}
