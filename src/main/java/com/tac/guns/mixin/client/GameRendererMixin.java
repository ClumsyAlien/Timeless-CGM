package com.tac.guns.mixin.client;

import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GameRenderer.class)
public class GameRendererMixin
{
    // Make Mixin resources/util file to house these puppies as public dictionaries type collections, expect people add new data since we simply iterate

    /*@Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/IProfiler;endStartSection(Ljava/lang/String;)V", shift = At.Shift.AFTER))
    public void updateCameraAndRender(float partialTicks, long nanoTime, boolean renderWorldIn, CallbackInfo ci)
    {
        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity player = minecraft.player;
        if (player == null) {
            return;
        }
        MainWindow window = Minecraft.getInstance().getMainWindow();
        EffectInstance blindedEffect = player.getActivePotionEffect(ModEffects.BLINDED.get());
        if (blindedEffect != null) {
            // Render white screen-filling overlay at full alpha effect when duration is above threshold
            // When below threshold, fade to full transparency as duration approaches 0
            float percent = Math.min((blindedEffect.getDuration() / (float) Config.SERVER.alphaFadeThreshold.get()), 1);
            AbstractGui.fill(new MatrixStack(), 0, 0, window.getWidth(), window.getHeight(), ((int) (percent * Config.SERVER.alphaOverlay.get() + 0.5) << 24) | 16777215);
        }
    }*/
}