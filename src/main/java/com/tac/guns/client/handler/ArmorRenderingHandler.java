package com.tac.guns.client.handler;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.tac.guns.Reference;
import com.tac.guns.client.GunRenderType;
import com.tac.guns.client.render.armor.CloakPilot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static org.lwjgl.opengl.GL11.GL_QUADS;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT)
public enum ArmorRenderingHandler {
    INSTANCE;
    //private static final CloakPilot model = new CloakPilot();
    private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, "textures/armor/cloak_pilot.png");
    private static final RenderType type = RenderType.create(Reference.MOD_ID + ":muzzle_flash", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1024, true, false, RenderType.CompositeState.builder().setTextureState(new RenderStateShard.TextureStateShard(texture, false, false)).createCompositeState(true));
    @SubscribeEvent
    public void onPlayerRender(RenderPlayerEvent.Pre event){
        //Minecraft.getInstance().player.sendChatMessage("test");
        //model.render(event.getMatrixStack(),event.getBuffers().getBuffer(type),event.getLight(),event.getLight(),1f,1f,1f,1f);
    }
}
