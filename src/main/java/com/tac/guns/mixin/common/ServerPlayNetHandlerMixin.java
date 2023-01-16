package com.tac.guns.mixin.common;

import com.tac.guns.network.CommonStateBox;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerPlayNetHandlerMixin {
    @Shadow
    public ServerPlayer player;

    @Inject(at = @At("HEAD"), method = "handlePlayerAction")
    public void applyDraw(ServerboundPlayerActionPacket packetIn, CallbackInfo ci){
        ServerboundPlayerActionPacket.Action action = packetIn.getAction();
        if(action.name().equals("SWAP_ITEM_WITH_OFFHAND") && !player.isSpectator()){
            CommonStateBox.isSwapped = true;
        }
    }
}
