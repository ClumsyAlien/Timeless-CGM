package com.tac.guns.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo // OptiFine may not be installed
@Mixin(targets = "net.optifine.shaders.Shaders")
public class ShadersFramebufferMixin
{

    /*
    *THIS SHIT FUCKING WORKED, but it rendered the hand too so the screen rendered 24/7 into itself
    *
    *
    *
    *
    @Dynamic("Added by OptiFine")
    @Inject(
            method = "renderFinal",
            at = @At("TAIL"),
            cancellable = true,
            require = 0, // Don't fail if OptiFine isn't present
            remap = false // OptiFine added method
    )
    *
    *
    * Useful: setSkipRenderHands
    *
    *
    *       Minecraft.getInstance().player.renderArmYaw = 2000;
            ScreenTextureState.instance().SetImageFromOptifine();
            Minecraft.getInstance().player.renderArmYaw =  Minecraft.getInstance().player.prevRenderArmYaw;
            *
            * Free look tech
            *
            *
            *
            *
            *
            *
            * beginLivingDamage Method within Optifine, we can abuse this along with greyscale filters to enable thermal vision
    * */

    /**
     * bindColorImage
     *//*
    @Dynamic("Added by OptiFine")
    @Inject(
            method = "beginRender",
            at = @At("TAIL"),
            cancellable = true,
            require = 0, // Don't fail if OptiFine isn't present
            remap = false // OptiFine added method
    )
    private static void tac_skipHands(CallbackInfo ci) {
        if(ScreenTextureState.instance() != null && Minecraft.getInstance().player != null && Minecraft.getInstance().world != null) {
            OptifineHelper.setSkipRenderHands();
        }
    }*/



    /**
     * bindColorImage
     */
    /*@Dynamic("Added by OptiFine")
    @Inject(
            method = "renderFinal",
            at = @At(value = "TAIL", ordinal = 0),
            cancellable = true,
            require = 0, // Don't fail if OptiFine isn't present
            remap = false // OptiFine added method
    )
    private static void tac_getMappedShaderImage(CallbackInfo ci) {
        if(ScreenTextureState.instance() != null && Minecraft.getInstance().player != null && Minecraft.getInstance().world != null*//* && !OptifineHelper.isRenderingFirstPersonHand()*//*) {
            //OptifineHelper.setSkipRenderHands();
            ScreenTextureState.instance().SetImageFromOptifine();
        }
    }*/
}