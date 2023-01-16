package com.tac.guns.network.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mrcrayfish.framework.api.network.PlayMessage;
import com.tac.guns.client.screen.UpgradeBenchScreen;
import com.tac.guns.common.network.ServerPlayHandler;
import com.tac.guns.init.ModEnchantments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraftforge.network.NetworkEvent;
import org.apache.commons.lang3.SerializationUtils;

import java.beans.XMLEncoder;
import java.io.*;
import java.util.function.Supplier;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class MessageUpgradeBenchApply extends PlayMessage<MessageUpgradeBenchApply>
{
    // Ew public
   public BlockPos pos;
   public String reqKey;
    public MessageUpgradeBenchApply() {}

    public MessageUpgradeBenchApply(BlockPos pos, String reqKey)
    {
        this.pos = pos;
        this.reqKey = reqKey;
    }

    @Override
    public void encode(MessageUpgradeBenchApply messageUpgradeBenchApply, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(messageUpgradeBenchApply.pos);
        buffer.writeUtf(messageUpgradeBenchApply.reqKey);


    }

    @Override
    public MessageUpgradeBenchApply decode(FriendlyByteBuf buffer)
    {
        return new MessageUpgradeBenchApply(buffer.readBlockPos(), buffer.readUtf());
    }

    @Override
    public void handle(MessageUpgradeBenchApply messageUpgradeBenchApply, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer player = supplier.get().getSender();
            if(player != null)
            {
                ServerPlayHandler.handleUpgradeBenchApply(messageUpgradeBenchApply, player);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
