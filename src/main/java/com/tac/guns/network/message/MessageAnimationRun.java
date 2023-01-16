package com.tac.guns.network.message;

import com.mrcrayfish.framework.api.network.PlayMessage;
import com.tac.guns.network.PacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.UUID;
import java.util.function.Supplier;

public class MessageAnimationRun extends PlayMessage<MessageAnimationRun> {
    private ResourceLocation animationResource;
    private ResourceLocation soundResource;
    private boolean play;
    private UUID fromWho;

    public MessageAnimationRun(){}

    public MessageAnimationRun(ResourceLocation animationResource,
                                 ResourceLocation soundResource,
                                 boolean play,
                                 UUID fromWho)
    {
        this.animationResource = animationResource;
        this.soundResource = soundResource;
        this.play = play;
        this.fromWho = fromWho;
    }

    @Override
    public void encode(MessageAnimationRun messageAnimationRun, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(messageAnimationRun.animationResource);
        buffer.writeResourceLocation(messageAnimationRun.soundResource);
        buffer.writeBoolean(messageAnimationRun.play);
        buffer.writeUUID(messageAnimationRun.fromWho);
    }

    @Override
    public MessageAnimationRun decode(FriendlyByteBuf buffer) {
        return new MessageAnimationRun(buffer.readResourceLocation(), buffer.readResourceLocation(), buffer.readBoolean(), buffer.readUUID());
    }

    @Override
    public void handle(MessageAnimationRun messageAnimationRun, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() ->
        {
            MessageAnimationSound message = new MessageAnimationSound(messageAnimationRun.animationResource, messageAnimationRun.soundResource, messageAnimationRun.play, messageAnimationRun.fromWho);
            PacketHandler.getPlayChannel().send(PacketDistributor.ALL.noArg(), message);
        });
        supplier.get().setPacketHandled(true);
    }
}
