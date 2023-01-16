package com.tac.guns.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;


@Cancelable
public class RenderItemEvent extends Event {

    private ItemStack heldItem;
    private ItemTransforms.TransformType transformType;
    private PoseStack poseStack;
    private MultiBufferSource bufferSource;
    private float partialTicks;
    private int light;
    private int overlay;

    public RenderItemEvent(ItemStack heldItem, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay, float partialTicks) {
        this.heldItem = heldItem;
        this.transformType = transformType;
        this.poseStack = poseStack;
        this.bufferSource = bufferSource;
        this.partialTicks = partialTicks;
        this.light = light;
        this.overlay = overlay;
    }

    public ItemStack getItem() {
        return this.heldItem;
    }

    public ItemTransforms.TransformType getTransformType() {
        return this.transformType;
    }

    public PoseStack getPoseStack() {
        return this.poseStack;
    }

    public MultiBufferSource getBufferSource() {
        return this.bufferSource;
    }

    public int getLight() {
        return this.light;
    }

    public int getOverlay() {
        return this.overlay;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    @Cancelable
    public static class Held extends RenderItemEvent {
        private LivingEntity entity;
        private HumanoidArm handSide;

        public Held(LivingEntity entity, ItemStack heldItem, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource bufferSource, HumanoidArm handSide, int light, int overlay, float partialTicks) {
            super(heldItem, transformType, poseStack, bufferSource, light, overlay, partialTicks);
            this.entity = entity;
            this.handSide = handSide;
        }

        public LivingEntity getEntity() {
            return this.entity;
        }

        public HumanoidArm getHandSide() {
            return this.handSide;
        }

        public static class Post extends Held {
            public Post(LivingEntity entity, ItemStack heldItem, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource bufferSource, HumanoidArm handSide, int light, int overlay, float partialTicks) {
                super(entity, heldItem, transformType, poseStack, bufferSource, handSide, light, overlay, partialTicks);
            }

            public boolean isCancelable() {
                return false;
            }
        }

        public static class Pre extends Held {
            public Pre(LivingEntity entity, ItemStack heldItem, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource bufferSource, HumanoidArm handSide, int light, int overlay, float partialTicks) {
                super(entity, heldItem, transformType, poseStack, bufferSource, handSide, light, overlay, partialTicks);
            }
        }
    }

    @Cancelable
    public static class Entity extends RenderItemEvent
    {
        private final ItemEntity entity;

        public Entity(ItemEntity entity, ItemStack heldItem, PoseStack poseStack, MultiBufferSource source, int light, int overlay, float deltaTicks)
        {
            super(heldItem, ItemTransforms.TransformType.GROUND, poseStack, source, light, overlay, deltaTicks);
            this.entity = entity;
        }

        /**
         * Gets the item entity being rendered
         */
        public ItemEntity getEntity()
        {
            return this.entity;
        }

        /**
         * Called before the item is rendered on the ground. This event can be cancelled to
         * prevent the item from being rendered and perform any custom rendering. It should be noted
         * that cancelling this event will prevent {@link Post} from being called.
         */
        public static class Pre extends Entity
        {
            public Pre(ItemEntity entity, ItemStack heldItem, PoseStack poseStack, MultiBufferSource source, int light, int overlay, float deltaTicks)
            {
                super(entity, heldItem, poseStack, source, light, overlay, deltaTicks);
            }
        }

        /**
         * Called after the item has been rendered and if {@link Pre} has not been cancelled. This
         * event cannot be cancelled.
         */
        public static class Post extends Entity
        {
            public Post(ItemEntity entity, ItemStack heldItem, PoseStack poseStack, MultiBufferSource source, int light, int overlay, float deltaTicks)
            {
                super(entity, heldItem, poseStack, source, light, overlay, deltaTicks);
            }

            @Override
            public boolean isCancelable()
            {
                return false;
            }
        }
    }

    @Cancelable
    public static class Gui extends RenderItemEvent
    {
        public Gui(ItemStack heldItem, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
        {
            super(heldItem, ItemTransforms.TransformType.GUI, poseStack, source, light, overlay, Minecraft.getInstance().getDeltaFrameTime());
        }

        /**
         * Called before the item is rendered in the GUI. This event can be cancelled to prevent the
         * item from being rendered and perform any custom rendering. It should be noted that
         * cancelling this event will prevent {@link Post} from being called.
         */
        public static class Pre extends Gui
        {
            public Pre(ItemStack heldItem, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
            {
                super(heldItem, poseStack, source, light, overlay);
            }
        }

        /**
         * Called after the item has been rendered and if {@link Pre} has not been cancelled. This
         * event cannot be cancelled.
         */
        public static class Post extends Gui
        {
            public Post(ItemStack heldItem, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
            {
                super(heldItem, poseStack, source, light, overlay);
            }

            @Override
            public boolean isCancelable()
            {
                return false;
            }
        }
    }

    @Cancelable
    public static class ItemFrame extends RenderItemEvent
    {
        private final net.minecraft.world.entity.decoration.ItemFrame entity;

        public ItemFrame(net.minecraft.world.entity.decoration.ItemFrame entity, ItemStack heldItem, PoseStack poseStack, MultiBufferSource source, int light, int overlay, float deltaTicks)
        {
            super(heldItem, ItemTransforms.TransformType.FIXED, poseStack, source, light, overlay, deltaTicks);
            this.entity = entity;
        }

        /**
         * Gets the item frame entity that is holding the item
         */
        public net.minecraft.world.entity.decoration.ItemFrame getEntity()
        {
            return this.entity;
        }

        /**
         * Called before the item is rendered into the item frame. This event can be cancelled to
         * prevent the item from being rendered and perform any custom rendering. It should be noted
         * that cancelling this event will prevent {@link Post} from being called.
         */
        public static class Pre extends ItemFrame
        {
            public Pre(net.minecraft.world.entity.decoration.ItemFrame entity, ItemStack heldItem, PoseStack poseStack, MultiBufferSource source, int light, int overlay, float deltaTicks)
            {
                super(entity, heldItem, poseStack, source, light, overlay, deltaTicks);
            }
        }

        /**
         * Called after the item has been rendered and if {@link Pre} has not been cancelled. This
         * event cannot be cancelled.
         */
        public static class Post extends ItemFrame
        {
            public Post(net.minecraft.world.entity.decoration.ItemFrame entity, ItemStack heldItem, PoseStack poseStack, MultiBufferSource source, int light, int overlay, float deltaTicks)
            {
                super(entity, heldItem, poseStack, source, light, overlay, deltaTicks);
            }

            @Override
            public boolean isCancelable()
            {
                return false;
            }
        }
    }

}
