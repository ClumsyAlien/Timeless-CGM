package com.tac.guns.client.render.gun;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tac.guns.Reference;
import com.tac.guns.client.render.ScreenTextureState;
import com.tac.guns.client.util.RenderUtil;
import com.tac.guns.item.GunItem;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT)
public class ModelOverrides
{
    private static final Map<Item, IOverrideModel> MODEL_MAP = new HashMap<>();
    private static final Map<Item, ItemStackTileEntityRenderer> ISTER_MAP = new HashMap<>();

    /**
     * Registers an override model to the given item.
     *
     * @param item  the item to override it's model
     * @param model a custom IOverrideModel implementation
     */
    public static void register(Item item, IOverrideModel model)
    {
        if(MODEL_MAP.putIfAbsent(item, model) == null)
        {
            /* Register model overrides as an event for ease. Doesn't create an extra overhead because
             * Forge will just ignore it if it contains no events */
            MinecraftForge.EVENT_BUS.register(model);
        }
    }
    /**
     * Registers an override ISTER to the given item.
     *
     * @param item  the item to override it's model
     * @param renderer a custom ItemStackTileEntityRenderer
     */
    public static void registerISTER(Item item, ItemStackTileEntityRenderer renderer)
    {
        if(ISTER_MAP.putIfAbsent(item, renderer) == null)
        {
            /* Register model overrides as an event for ease. Doesn't create an extra overhead because
             * Forge will just ignore it if it contains no events */
            MinecraftForge.EVENT_BUS.register(renderer);
        }
        register(item, new ISTERModelOverride());
    }

    /**
     * Checks if the given ItemStack has an overridden model
     *
     * @param stack the stack to check
     * @return True if overridden model exists
     */
    public static boolean hasModel(ItemStack stack)
    {
        return MODEL_MAP.containsKey(stack.getItem());
    }

    /**
     * Gets the overridden model for the given ItemStack.
     *
     * @param stack the stack of the overriden model
     * @return The overridden model for the stack or null if no overridden model exists.
     */
    @Nullable
    public static IOverrideModel getModel(ItemStack stack)
    {
        return MODEL_MAP.get(stack.getItem());
    }

    @Nullable
    public static ItemStackTileEntityRenderer getISTERRenderer(ItemStack stack)
    {
        return ISTER_MAP.get(stack.getItem());
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onClientPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if(event.phase == TickEvent.Phase.START && event.side == LogicalSide.CLIENT) //  && event.type == TickEvent.Type.RENDER
        {
            if(!ScreenTextureState.instance().isRenderGun)
            {
                ScreenTextureState.instance().isRenderGun = true;
                return;
            }
            tick(event.player);
        }
    }

    private static void tick(PlayerEntity player)
    {
        ItemStack heldItem = player.getHeldItemMainhand();
        if(!heldItem.isEmpty() && heldItem.getItem() instanceof GunItem)
        {
            IOverrideModel model = ModelOverrides.getModel(heldItem);
            if(model != null)
            {
                model.tick(player);
            }
        }
    }

    public static class ISTERModelOverride implements IOverrideModel{

        @Override
        public void render(float partialTicks, ItemCameraTransforms.TransformType transformType, ItemStack stack, ItemStack parent, LivingEntity entity, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, int overlay) {
            RenderUtil.renderISTER(ModelOverrides.getISTERRenderer(stack), transformType, stack, matrixStack, buffer, light, overlay);
        }
    }


}
