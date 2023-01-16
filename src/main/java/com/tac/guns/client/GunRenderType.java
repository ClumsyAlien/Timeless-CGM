package com.tac.guns.client;

import com.tac.guns.Reference;
import com.tac.guns.client.handler.GunRenderingHandler;
import com.tac.guns.client.render.ScreenTextureState;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;

import static org.lwjgl.opengl.GL11.GL_QUADS;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public final class GunRenderType extends RenderType
{
    private static final RenderType BULLET_TRAIL = RenderType.create(Reference.MOD_ID + ":projectile_trail", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, true, true, RenderType.CompositeState.builder().setShaderState(RenderStateShard.POSITION_COLOR_LIGHTMAP_SHADER).setCullState(NO_CULL).setTransparencyState(TRANSLUCENT_TRANSPARENCY).createCompositeState(false));
    private static final RenderType SCREEN = RenderType.create(Reference.MOD_ID + ":screen_texture", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1024, true, false, RenderType.CompositeState.builder().setShaderState(RenderStateShard.NEW_ENTITY_SHADER).setTexturingState(ScreenTextureState.instance()).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).setCullState(RenderStateShard.CULL).createCompositeState(false)); //256
    private static final RenderType MUZZLE_FLASH = RenderType.create(Reference.MOD_ID + ":muzzle_flash", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, true, false, RenderType.CompositeState.builder().setShaderState(RenderStateShard.POSITION_COLOR_TEX_LIGHTMAP_SHADER).setTextureState(new RenderStateShard.TextureStateShard(GunRenderingHandler.MUZZLE_FLASH_TEXTURE, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL).createCompositeState(true));
    private static final RenderType MUZZLE_SMOKE = RenderType.create(Reference.MOD_ID + ":muzzle_smoke", DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, true, false, RenderType.CompositeState.builder().setShaderState(RenderStateShard.POSITION_COLOR_TEX_SHADER).setTextureState(new RenderStateShard.TextureStateShard(GunRenderingHandler.MUZZLE_SMOKE_TEXTURE, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL).createCompositeState(true));

    public GunRenderType(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    }


    public static RenderType getBulletTrail()
    {
        return BULLET_TRAIL;
    }

    public static RenderType getScreen()
    {
        return SCREEN;
    } // E : Figuring out how to refactor this = fixing all of Optifine's scope issues

    public static RenderType getMuzzleSmoke() { return MUZZLE_SMOKE; }

    public static RenderType getMuzzleFlash()
    {
        return MUZZLE_FLASH;
    }
}
