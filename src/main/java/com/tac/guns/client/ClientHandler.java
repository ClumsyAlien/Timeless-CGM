package com.tac.guns.client;

import com.tac.guns.Reference;
import com.tac.guns.client.handler.*;

import com.tac.guns.client.render.entity.GrenadeRenderer;
import com.tac.guns.client.render.entity.MissileRenderer;
import com.tac.guns.client.render.entity.ProjectileRenderer;
import com.tac.guns.client.render.entity.ThrowableGrenadeRenderer;
import com.tac.guns.client.render.gun.ModelOverrides;
import com.tac.guns.client.render.gun.model.*;
import com.tac.guns.client.screen.AttachmentScreen;
import com.tac.guns.client.screen.InspectScreen;
import com.tac.guns.client.screen.WorkbenchScreen;
import com.tac.guns.client.settings.GunOptions;
import com.tac.guns.init.ModBlocks;
import com.tac.guns.init.ModContainers;
import com.tac.guns.init.ModEntities;
import com.tac.guns.init.ModItems;
import com.tac.guns.item.IColored;
import com.tac.guns.network.PacketHandler;
import com.tac.guns.network.message.MessageAttachments;
import com.tac.guns.network.message.MessageInspection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.MouseSettingsScreen;
import net.minecraft.client.gui.widget.list.OptionsRowList;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT)
public class ClientHandler
{
    private static Field mouseOptionsField;

    public static void setup()
    {
        MinecraftForge.EVENT_BUS.register(AimingHandler.get());
        MinecraftForge.EVENT_BUS.register(BulletTrailRenderingHandler.get());
        MinecraftForge.EVENT_BUS.register(CrosshairHandler.get());
        MinecraftForge.EVENT_BUS.register(GunRenderingHandler.get());
        MinecraftForge.EVENT_BUS.register(RecoilHandler.get());
        MinecraftForge.EVENT_BUS.register(ReloadHandler.get());
        MinecraftForge.EVENT_BUS.register(ShootingHandler.get());
        MinecraftForge.EVENT_BUS.register(SoundHandler.get());
        MinecraftForge.EVENT_BUS.register(FireModeSwitchEvent.get()); // Technically now a handler but, yes I need some naming reworks

        KeyBinds.register();

        setupRenderLayers();
        registerEntityRenders();
        registerColors();
        registerModelOverrides();
        registerScreenFactories();
    }

    private static void setupRenderLayers()
    {
        RenderTypeLookup.setRenderLayer(ModBlocks.WORKBENCH.get(), RenderType.cutout());
    }

    private static void registerEntityRenders()
    {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.PROJECTILE.get(), ProjectileRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.GRENADE.get(), GrenadeRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.MISSILE.get(), MissileRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.THROWABLE_GRENADE.get(), ThrowableGrenadeRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.THROWABLE_STUN_GRENADE.get(), ThrowableGrenadeRenderer::new);
    }

    private static void registerColors()
    {
        IItemColor color = (stack, index) -> {
            if(!((IColored) stack.getItem()).canColor(stack))
            {
                return -1;
            }
            if(index == 0 && stack.hasTag() && stack.getTag().contains("Color", Constants.NBT.TAG_INT))
            {
                return stack.getTag().getInt("Color");
            }
            return -1;
        };
        ForgeRegistries.ITEMS.forEach(item -> {
            if(item instanceof IColored)
            {
                Minecraft.getInstance().getItemColors().register(color, item);
            }
        });
    }

    private static void registerModelOverrides()
    {
        //ModelOverrides.register(ModItems.MINI_GUN.get(), new MiniGunModel());
        ModelOverrides.register(ModItems.SHORT_SCOPE.get(), new ShortScopeModel());
        ModelOverrides.register(ModItems.MEDIUM_SCOPE.get(), new MediumScopeModel());
        ModelOverrides.register(ModItems.LONG_SCOPE.get(), new LongScopeModel());
        ModelOverrides.register(ModItems.COYOTE_SIGHT.get(), new CoyoteSightModel());
        ModelOverrides.register(ModItems.MIDRANGE_DOT_SCOPE.get(), new MidRangeDotScopeModel());
        //ModelOverrides.register(ModItems.BAZOOKA.get(), new BazookaModel());
        //ModelOverrides.register(ModItems.GRENADE_LAUNCHER.get(), new GrenadeLauncherModel());
    }

    private static void registerScreenFactories()
    {
        ScreenManager.register(ModContainers.WORKBENCH.get(), WorkbenchScreen::new);
        ScreenManager.register(ModContainers.ATTACHMENTS.get(), AttachmentScreen::new);
        ScreenManager.register(ModContainers.INSPECTION.get(), InspectScreen::new);
    }

    @SubscribeEvent
    public static void onScreenInit(GuiScreenEvent.InitGuiEvent.Post event)
    {
        if(event.getGui() instanceof MouseSettingsScreen)
        {
            MouseSettingsScreen screen = (MouseSettingsScreen) event.getGui();
            if(mouseOptionsField == null)
            {
                mouseOptionsField = ObfuscationReflectionHelper.findField(MouseSettingsScreen.class, "list");
                mouseOptionsField.setAccessible(true);
            }
            try
            {
                OptionsRowList list = (OptionsRowList) mouseOptionsField.get(screen);
                list.addSmall(GunOptions.ADS_SENSITIVITY, GunOptions.CROSSHAIR);
            }
            catch(IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public static void onKeyPressed(InputEvent.KeyInputEvent event)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player != null && mc.screen == null)
        {
            if(KeyBinds.KEY_ATTACHMENTS.consumeClick())
            {
                PacketHandler.getPlayChannel().sendToServer(new MessageAttachments());
            }
            else if(KeyBinds.KEY_INSPECT.consumeClick())
            {
                PacketHandler.getPlayChannel().sendToServer(new MessageInspection());
            }
        }
    }

    /* Uncomment for debugging headshot hit boxes */

    /*@SubscribeEvent
    @SuppressWarnings("unchecked")
    public static void onRenderLiving(RenderLivingEvent.Post event)
    {
        LivingEntity entity = event.getEntity();
        IHeadshotBox<LivingEntity> headshotBox = (IHeadshotBox<LivingEntity>) BoundingBoxManager.getHeadshotBoxes(entity.getType());
        if(headshotBox != null)
        {
            AxisAlignedBB box = headshotBox.getHeadshotBox(entity);
            if(box != null)
            {
                WorldRenderer.drawBoundingBox(event.getMatrixStack(), event.getBuffers().getBuffer(RenderType.getLines()), box, 1.0F, 1.0F, 0.0F, 1.0F);

                AxisAlignedBB boundingBox = entity.getBoundingBox().offset(entity.getPositionVec().inverse());
                boundingBox = boundingBox.grow(Config.COMMON.gameplay.growBoundingBoxAmount.get(), 0, Config.COMMON.gameplay.growBoundingBoxAmount.get());
                WorldRenderer.drawBoundingBox(event.getMatrixStack(), event.getBuffers().getBuffer(RenderType.getLines()), boundingBox, 0.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }*/
}
