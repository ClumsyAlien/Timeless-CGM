package com.tac.guns.network.message;


import com.mrcrayfish.framework.api.network.PlayMessage;
import com.tac.guns.Config;
import com.tac.guns.common.network.ServerPlayHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;


/**
 * Author: https://github.com/Charles445/DamageTilt/blob/1.16/src/main/java/com/charles445/damagetilt/MessageUpdateAttackYaw.java, continued by Timeless devs
 */


public class MessagePlayerShake extends PlayMessage<MessagePlayerShake>
{
    @Override
    public void encode(MessagePlayerShake messagePlayerShake, FriendlyByteBuf buffer)
    {
        buffer.writeFloat(messagePlayerShake.attackedAtYaw);
    }

    @Override
    public MessagePlayerShake decode(FriendlyByteBuf buffer)
    {
        return new MessagePlayerShake(buffer.readFloat());
    }

    public float attackedAtYaw;

    public MessagePlayerShake() {}
    public MessagePlayerShake(float value) {this.attackedAtYaw = value;}
    public MessagePlayerShake(LivingEntity entity)
    {
        this.attackedAtYaw = entity.hurtDir;
    }

    @OnlyIn(Dist.CLIENT)
    public static void fromMessage()
    {
        Minecraft.getInstance().player.hurtDir = 2.0f;
        Minecraft.getInstance().player.hurtTime = Config.CLIENT.display.cameraShakeOnHit.get();
    }

    @Override
    public void handle(MessagePlayerShake messagePlayerShake, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().setPacketHandled(true);
        if(supplier.get().getDirection() != NetworkDirection.PLAY_TO_CLIENT)
            return;

        Minecraft.getInstance().submitAsync(() ->
        {
            fromMessage();
        });
    }
}


