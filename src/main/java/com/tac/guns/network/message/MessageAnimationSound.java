package com.tac.guns.network.message;

import com.mrcrayfish.framework.api.network.PlayMessage;
import com.tac.guns.client.network.ClientPlayHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class MessageAnimationSound extends PlayMessage<MessageAnimationSound> {
    private ResourceLocation animationResource;
    private ResourceLocation soundResource;
    private boolean play;
    private UUID fromWho;

    public MessageAnimationSound(){}

    public MessageAnimationSound(ResourceLocation animationResource,
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
    public void encode(MessageAnimationSound messageAnimationSound, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(messageAnimationSound.animationResource);
        buffer.writeResourceLocation(messageAnimationSound.soundResource);
        buffer.writeBoolean(messageAnimationSound.play);
        buffer.writeUUID(messageAnimationSound.fromWho);
    }

    @Override
    public MessageAnimationSound decode(FriendlyByteBuf buffer) {
        return new MessageAnimationSound(buffer.readResourceLocation(), buffer.readResourceLocation(), buffer.readBoolean(), buffer.readUUID());
    }

    @Override
    public void handle(MessageAnimationSound messageAnimationSound, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() ->
        {
            ClientPlayHandler.handleMessageAnimationSound(messageAnimationSound.fromWho, messageAnimationSound.animationResource, messageAnimationSound.soundResource, messageAnimationSound.play);
        });
        supplier.get().setPacketHandled(true);
    }
}
