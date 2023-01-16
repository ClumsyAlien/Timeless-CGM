package com.tac.guns.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tac.guns.Config;
import com.tac.guns.Reference;
import com.tac.guns.client.GunRenderType;
import com.tac.guns.client.event.RenderItemEvent;
import com.tac.guns.client.handler.command.GunEditor;
import com.tac.guns.client.render.IHeldAnimation;
import com.tac.guns.client.render.animation.module.GunAnimationController;
import com.tac.guns.client.render.animation.module.PistalAnimationController;
import com.tac.guns.client.render.gun.IOverrideModel;
import com.tac.guns.client.render.gun.ModelOverrides;
import com.tac.guns.client.util.RenderUtil;
import com.tac.guns.common.Gun;
import com.tac.guns.common.tooling.CommandsHandler;
import com.tac.guns.event.GunFireEvent;
import com.tac.guns.event.GunReloadEvent;
import com.tac.guns.init.ModSyncedDataKeys;
import com.tac.guns.item.GrenadeItem;
import com.tac.guns.item.GunItem;
import com.tac.guns.item.ScopeItem;
import com.tac.guns.item.TransitionalTypes.ITimelessAnimated;
import com.tac.guns.item.TransitionalTypes.TimelessGunItem;
import com.tac.guns.item.TransitionalTypes.TimelessPistolGunItem;
import com.tac.guns.item.attachment.IAttachment;
import com.tac.guns.item.attachment.IBarrel;
import com.tac.guns.item.attachment.impl.Barrel;
import com.tac.guns.item.attachment.impl.Scope;
import com.tac.guns.network.PacketHandler;
import com.tac.guns.network.message.MessagePlayerShake;
import com.tac.guns.network.message.MessageUpdatePlayerMovement;
import com.tac.guns.util.GunEnchantmentHelper;
import com.tac.guns.util.GunModifierHelper;
import com.tac.guns.util.OptifineHelper;
import com.tac.guns.util.math.OneDimensionalPerlinNoise;
import com.tac.guns.util.math.SecondOrderDynamics;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.CameraType;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.network.NetworkDirection;

import java.lang.reflect.Field;
import java.sql.Time;
import java.util.*;

public class GunRenderingHandler {
    private static GunRenderingHandler instance;
    private final SecondOrderDynamics recoilDynamics = new SecondOrderDynamics(0.4f,0.7f, 2f, 0);
    private final SecondOrderDynamics swayDynamics = new SecondOrderDynamics(0.35f,0.5f, 2f, 0);
    private final SecondOrderDynamics aimingDynamics = new SecondOrderDynamics(0.45f,0.8f, 1.2f, 0);
    // Standard Sprint Dynamics
    private final SecondOrderDynamics sprintDynamics = new SecondOrderDynamics(0.22f,0.7f, 0.6f, 0);
    private final SecondOrderDynamics bobbingDynamics = new SecondOrderDynamics(0.22f,0.7f, 0.6f, 1);
    private final SecondOrderDynamics speedUpDynamics = new SecondOrderDynamics(0.22f,0.7f, 0.6f, 0);
    private final SecondOrderDynamics sprintDynamicsZ = new SecondOrderDynamics(0.22f,0.8f, 0.5f, 0);
    private final SecondOrderDynamics jumpingDynamics =  new SecondOrderDynamics(0.28f,1f, 0.65f, 0);
    // High Speed Sprint Dynamics
    private final SecondOrderDynamics sprintDynamicsHSS = new SecondOrderDynamics(0.3f,0.6f, 0.6f,
            0);
   /* private final SecondOrderDynamics sprintDynamicsZHSS = new SecondOrderDynamics(0.15f,0.7f,
            -2.25f, new Vector3f(0,0,0));*/
    private final SecondOrderDynamics sprintDynamicsZHSS = new SecondOrderDynamics(0.27f,0.75f, 0.5f,
            0);
    public final SecondOrderDynamics sprintDynamicsHSSLeftHand = new SecondOrderDynamics(0.38f,
            1f, 0f, 0);

    public static GunRenderingHandler get() {
        if (instance == null) {
            instance = new GunRenderingHandler();
        }
        return instance;
    }

    public static final ResourceLocation MUZZLE_FLASH_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/effect/muzzle_flash.png");
    public static final ResourceLocation MUZZLE_SMOKE_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/effect/muzzle_smoke.png");

    private Random random = new Random();
    private Set<Integer> entityIdForMuzzleFlash = new HashSet<>();
    private Set<Integer> entityIdForDrawnMuzzleFlash = new HashSet<>();
    private Map<Integer, Float> entityIdToRandomValue = new HashMap<>();

    public int sprintTransition;
    private int prevSprintTransition;
    private int sprintCooldown;
    private int restingTimer;
    private final int restingTimerUpper = 5;

    private float offhandTranslate;
    private float prevOffhandTranslate;

    public float muzzleExtraOnEnch = 0f;

    private Field equippedProgressMainHandField;
    private Field prevEquippedProgressMainHandField;

    private float startingDistance = 0f;
    private float speedUpDistanceFrom = 0f;

    public float speedUpDistance = 0.6f;

    public float speedUpProgress = 0f;

    private float additionalSwayProgress = 0f;

    private GunRenderingHandler() {
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;
        this.updateSprinting();
        this.updateMuzzleFlash();
        this.updateOffhandTranslate();
        if(Minecraft.getInstance().player == null)
            return;
        if(Minecraft.getInstance().player.input.down){
            if(backwardTicker < 8) backwardTicker ++;
        }else {
            if(backwardTicker > 0) backwardTicker --;
        }
        if(CommandsHandler.get().getCatCurrentIndex() == 1 && GunEditor.get().getMode() == GunEditor.TaCWeaponDevModes.flash)
        {
            if(!(Minecraft.getInstance().player.getMainHandItem().getItem() instanceof TimelessGunItem))
                return;
            GunItem gun = ((TimelessGunItem)Minecraft.getInstance().player.getMainHandItem().getItem());
            Gun modifiedGun = gun.getModifiedGun(Minecraft.getInstance().player.getMainHandItem());
            if (modifiedGun.getDisplay().getFlash() != null || GunEditor.get().getMode() == GunEditor.TaCWeaponDevModes.flash) {
                this.showMuzzleFlashForPlayer(Minecraft.getInstance().player.getId());
            }
        }
    }

    private long fireTime = System.currentTimeMillis();

    @SubscribeEvent
    public void onGunFired(GunFireEvent event){
        if(event.isClient()) fireTime = System.currentTimeMillis();
    }

    @SubscribeEvent
    public void onCameraSetup(EntityViewRenderEvent.CameraSetup event){
        float cameraShakeDuration = 0.06f;
        long alphaTime = System.currentTimeMillis() - fireTime;
        float progress = (alphaTime < cameraShakeDuration * 1000 ? 1 - alphaTime / (cameraShakeDuration*1000f) : 0);
        //apply camera shake when firing.
        float alpha = (progress
                * (Math.random() - 0.5 < 0 ? -1 : 1)
                * 0.8f);
        event.setPitch(event.getPitch() - Math.abs(alpha));
        event.setRoll(event.getRoll() + alpha * 0.5f);
    }

    @SubscribeEvent
    public void onFovModifying(EntityViewRenderEvent.FieldOfView event){
        float cameraShakeDuration = 0.06f * (AimingHandler.get().isAiming() ? 1.5f : 1f);
        long alphaTime = System.currentTimeMillis() - fireTime;
        float progress = (alphaTime < cameraShakeDuration * 1000 ? 1 - alphaTime / (cameraShakeDuration*1000f) : 0);
        event.setFOV(event.getFOV() + progress * 0.5f);
    }

    private void updateSprinting() {
        this.prevSprintTransition = this.sprintTransition;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && (mc.player.isSprinting() && !mc.player.isCrouching()) && !ModSyncedDataKeys.SHOOTING.getValue(mc.player) && !ModSyncedDataKeys.RELOADING.getValue(mc.player) && !AimingHandler.get().isAiming() && this.sprintCooldown == 0) {
            if (Minecraft.getInstance().player != null) {
                ItemStack heldItem = Minecraft.getInstance().player.getMainHandItem();
                if(heldItem.getItem() instanceof GunItem) {
                    GunItem modifiedGun = (GunItem) heldItem.getItem();
                    GunAnimationController controller = GunAnimationController.fromItem(modifiedGun);
                    if (this.sprintTransition < 5) {
                        if (controller == null ||
                                (modifiedGun.getGun().getGeneral().getGripType().getHeldAnimation().canApplySprintingAnimation() && !controller.isAnimationRunning())) {
                            this.sprintTransition++;
                        }
                    }
                    if(controller != null && controller.isAnimationRunning()) {
                        if(sprintTransition > 0){
                            this.sprintTransition -- ;
                        }
                    }
                }
            }
        } else if (this.sprintTransition > 0) {
            this.sprintTransition = 0;
        }

        if (this.sprintCooldown > 0) {
            this.sprintCooldown--;
        }
        if(sprintTransition == 0){
            if(restingTimer < restingTimerUpper){
                restingTimer ++;
            }
        }else {
            restingTimer = 0;
        }
    }

    private void updateMuzzleFlash() {
        this.entityIdForMuzzleFlash.removeAll(this.entityIdForDrawnMuzzleFlash);
        this.entityIdToRandomValue.keySet().removeAll(this.entityIdForDrawnMuzzleFlash);
        this.entityIdForDrawnMuzzleFlash.clear();
        this.entityIdForDrawnMuzzleFlash.addAll(this.entityIdForMuzzleFlash);
    }

    private void updateOffhandTranslate() {
        this.prevOffhandTranslate = this.offhandTranslate;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null)
            return;

        boolean down = false;
        ItemStack heldItem = mc.player.getMainHandItem();
        if (heldItem.getItem() instanceof GunItem) {
            Gun modifiedGun = ((GunItem) heldItem.getItem()).getModifiedGun(heldItem);
            down = !modifiedGun.getGeneral().getGripType().getHeldAnimation().canRenderOffhandItem();
        }

        float direction = down ? -0.3F : 0.3F;
        this.offhandTranslate = Mth.clamp(this.offhandTranslate + direction, 0.0F, 1.0F);
    }

    @SubscribeEvent
    public void onGunFire(GunFireEvent.Post event) {
        if (!event.isClient())
            return;
        if(Minecraft.getInstance().player == null) return;
        Item item = event.getStack().getItem();
        if(item instanceof ITimelessAnimated){
            ITimelessAnimated animated = (ITimelessAnimated) item;
            animated.playAnimation("fire",event.getStack(),true);
        }
        this.sprintTransition = 0;
        this.speedUpDistanceFrom = Minecraft.getInstance().player.walkDist;
        this.sprintCooldown = 8;

        ItemStack heldItem = event.getStack();
        GunItem gunItem = (GunItem) heldItem.getItem();
        Gun modifiedGun = gunItem.getModifiedGun(heldItem);
        if (modifiedGun.getDisplay().getFlash() != null) {
            this.showMuzzleFlashForPlayer(Minecraft.getInstance().player.getId());
        }
    }

    @SubscribeEvent
    public void onGunFirePre(GunFireEvent.Pre event){
        if(sprintTransition == 0){
            if(restingTimer < restingTimerUpper){
                event.setCanceled(true);
            }
        }else {
            event.setCanceled(true);
        }
    }

    public void showMuzzleFlashForPlayer(int entityId) {
        this.entityIdForMuzzleFlash.add(entityId);
        this.entityIdToRandomValue.put(entityId, this.random.nextFloat());
    }

    @SubscribeEvent
    public void onAnimatedGunReload(GunReloadEvent.Pre event){
        Item item = event.getStack().getItem();
        if(item instanceof ITimelessAnimated){
            ITimelessAnimated animated = (ITimelessAnimated) item;
            animated.playAnimation("reload",event.getStack(),false);
        }
    }

/*    @SubscribeEvent
    public void onCameraSetup(EntityViewRenderEvent.CameraSetup event) // TEST FROM CGM
    {
        *//*if(!Config.CLIENT.experimental.immersiveCamera.get())
            return;*//*

        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null)
            return;

        ItemStack heldItem = mc.player.getHeldItemMainhand();
        float targetAngle = heldItem.getItem() instanceof GunItem ? mc.player.movementInput.moveStrafe * 1F: 0F;
        this.immersiveRoll = MathHelper.approach(this.immersiveRoll, targetAngle, 0.4F);
        event.setRoll(-this.immersiveRoll);
    }*/

    public float immersiveWeaponRoll;

    public float walkingDistance1;
    public float walkingDistance = 0.0f; //Abandoned. It is always 0.0f.
    public float walkingCrouch;
    public float walkingCameraYaw;
    public float zoomProgressInv;

    public double xOffset = 0.0;
    public double yOffset = 0.0;
    public double zOffset = 0.0;

    public double opticMovement;
    public double slideKeep;

    @SubscribeEvent
    public void onRenderOverlay(RenderHandEvent event)
    {
        GunAnimationController controller = GunAnimationController.fromItem(event.getItemStack().getItem());
        boolean isAnimated = controller != null;
        PoseStack matrixStack = event.getPoseStack();

        boolean right = Minecraft.getInstance().options.mainHand == HumanoidArm.RIGHT ? event.getHand() == InteractionHand.MAIN_HAND : event.getHand() == InteractionHand.OFF_HAND;
        ItemStack heldItem = event.getItemStack();

        if (!(heldItem.getItem() instanceof GunItem)) {
            return;
        }

        /* Cancel it because we are doing our own custom render */
        event.setCanceled(true);

        ItemStack overrideModel = ItemStack.EMPTY;
        if (heldItem.getTag() != null) {
            if (heldItem.getTag().contains("Model", Tag.TAG_COMPOUND)) {
                overrideModel = ItemStack.of(heldItem.getTag().getCompound("Model"));
            }
        }
        Minecraft mc = Minecraft.getInstance();
        GunItem gunItem = (GunItem) heldItem.getItem();
        if (mc.options.bobView && mc.getCameraEntity() instanceof Player)
        {
            Player playerentity = (Player) mc.getCameraEntity();
            float deltaDistanceWalked = playerentity.walkDist - playerentity.walkDistO;
            float distanceWalked = -(playerentity.walkDist + deltaDistanceWalked * event.getPartialTicks());
            float cameraYaw = Mth.lerp(event.getPartialTicks(), playerentity.oBob, playerentity.bob);

            /* Reverses the original bobbing rotations and translations so it can be controlled */
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(-(Math.abs(Mth.cos(distanceWalked * (float) Math.PI - 0.2F) * cameraYaw) * 5.0F)));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(-(Mth.sin(distanceWalked * (float) Math.PI) * cameraYaw * 3.0F)));
            matrixStack.translate((double) -(Mth.sin(distanceWalked * (float) Math.PI) * cameraYaw * 0.5F), (double) -(-Math.abs(Mth.cos(distanceWalked * (float) Math.PI) * cameraYaw)), 0.0D);

            // Walking bobbing
            boolean aimed = false;
            /* The new controlled bobbing */
            if(AimingHandler.get().isAiming())
                aimed = true;

            //double invertZoomProgress = aimed ? 0.0575 : 0.468; //0.135 : 0.44;//0.94;//aimed ? 1.0 - AimingHandler.get().getNormalisedAdsProgress() : ;
            double invertZoomProgress = aimed ? (Gun.getScope(heldItem) != null ? 0.0575 : 0.0725) : 0.468;
            float crouch = mc.player.isCrouching() ? 148f : 1f;

            if(playerentity.walkDist == playerentity.walkDistO && !playerentity.isShiftKeyDown())
                startingDistance = playerentity.walkDist;
            if(!mc.player.input.hasForwardImpulse()){
                speedUpDistanceFrom = playerentity.walkDist;
                speedUpProgress -= (new Date().getTime() - prevTime) / 150f;
                if(speedUpProgress < 0) speedUpProgress = 0;
            }else {
                speedUpProgress = ( -distanceWalked - speedUpDistanceFrom < speedUpDistance ? (-distanceWalked - speedUpDistanceFrom )/speedUpDistance : 1) ;
            }
            distanceWalked = distanceWalked + startingDistance;
            this.walkingDistance1 = distanceWalked;
            this.walkingCrouch = crouch;
            this.walkingCameraYaw = cameraYaw;
            this.zoomProgressInv = (float)invertZoomProgress;

            //matrixStack.translate((double) (Math.asin(-MathHelper.sin(distanceWalked*crouch * (float) Math.PI) * cameraYaw * 0.5F)) * invertZoomProgress, ((double) (Math.asin((-Math.abs(-MathHelper.cos(distanceWalked*crouch * (float) Math.PI) * cameraYaw))) * invertZoomProgress)) * 1.140, 0.0D);// * 1.140, 0.0D);
            applyBobbingTransforms(matrixStack, false);
            applyJumpingTransforms(matrixStack, event.getPartialTicks());
            //TODO: Implement config switch, it's not a required mechanic. it's just fun
            applyNoiseMovementTransform(matrixStack);

            matrixStack.mulPose(Vector3f.ZP.rotationDegrees((Mth.sin(distanceWalked*crouch * (float) Math.PI) * cameraYaw * 3.0F) * (float) invertZoomProgress));
            matrixStack.mulPose(Vector3f.XP.rotationDegrees((Math.abs(Mth.cos(distanceWalked*crouch * (float) Math.PI - 0.2F) * cameraYaw) * 5.0F) * (float) invertZoomProgress));

            // Weapon movement clanting
            float rollingForceCrouch = mc.player.isCrouching() ? 4f : 1f;
            float rollingForceAim = AimingHandler.get().isAiming() ? 0.425f : 1f;
            /*
                Pretty much from CGM, was going to build something very similar for 0.3, movement update comes early I guess,
                all credit to Mr.Crayfish who developed this weapon roll code for CGM,
                all I added was scaling for other game actions and adjusted rolling values
            */
            float targetAngle = heldItem.getItem() instanceof GunItem ? mc.player.input.leftImpulse * (6.65F * rollingForceCrouch * rollingForceAim) : 0F;
            this.immersiveWeaponRoll = Mth.approach(this.immersiveWeaponRoll, targetAngle, 0.335F);
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(this.immersiveWeaponRoll));
        }

        if (event.getHand() == InteractionHand.OFF_HAND)
        {
            if (heldItem.getItem() instanceof GunItem) {
                event.setCanceled(true);
                return;
            }

            float offhand = 1.0F - Mth.lerp(event.getPartialTicks(), this.prevOffhandTranslate, this.offhandTranslate);
            matrixStack.translate(0, offhand * -0.6F, 0);

            Player player = Minecraft.getInstance().player;
            if (player != null && player.getMainHandItem().getItem() instanceof GunItem) {
                Gun modifiedGun = ((GunItem) player.getMainHandItem().getItem()).getModifiedGun(player.getMainHandItem());
                if (!modifiedGun.getGeneral().getGripType().getHeldAnimation().canRenderOffhandItem()) {
                    return;
                }
            }

            /* Makes the off hand item move out of view */
            matrixStack.translate(0, -2 * AimingHandler.get().getNormalisedAdsProgress(), 0);
        }

        LivingEntity entity = Minecraft.getInstance().player;
        BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(overrideModel.isEmpty() ? heldItem : overrideModel, entity.level, entity, entity.getId());
        float scaleX = model.getTransforms().firstPersonRightHand.scale.x();
        float scaleY = model.getTransforms().firstPersonRightHand.scale.y();
        float scaleZ = model.getTransforms().firstPersonRightHand.scale.z();
        float translateX = model.getTransforms().firstPersonRightHand.translation.x();
        float translateY = model.getTransforms().firstPersonRightHand.translation.y();
        float translateZ = model.getTransforms().firstPersonRightHand.translation.z();

        matrixStack.pushPose();

        Gun modifiedGun = gunItem.getModifiedGun(heldItem);
        //int gunZoom = heldItem.getTag().getInt("currentZoom");

        if (modifiedGun.canAimDownSight())
        {
            if (event.getHand() == InteractionHand.MAIN_HAND) {
                this.xOffset = 0.0;
                this.yOffset = 0.0;
                this.zOffset = 0.0;
                Scope scope = Gun.getScope(heldItem);
                boolean isScopeOffsetType = Config.CLIENT.display.gameplayEnchancedScopeOffset.get();
                boolean isScopeRenderType = Config.CLIENT.display.scopeDoubleRender.get();
                /* Creates the required offsets to position the scope into the middle of the screen. */
                if (modifiedGun.canAttachType(IAttachment.Type.SCOPE) && scope != null) {
                    double viewFinderOffset = isScopeOffsetType || OptifineHelper.isShadersEnabled() ? scope.getViewFinderOffsetSpecial() : scope.getViewFinderOffset();
                    if(scope.getAdditionalZoom().getFovZoom() > 0)
                        viewFinderOffset = isScopeRenderType ? (isScopeOffsetType || OptifineHelper.isShadersEnabled() ? scope.getViewFinderOffsetSpecial() : scope.getViewFinderOffset()) : (isScopeOffsetType || OptifineHelper.isShadersEnabled() ? scope.getViewFinderOffsetSpecialDR() : scope.getViewFinderOffsetDR()); // switches between either, but either must be populated

                    //if (OptifineHelper.isShadersEnabled()) viewFinderOffset *= 0.735;
                    //if (isScopeRenderType) viewFinderOffset *= 0.735;
                    Gun.ScaledPositioned scaledPos = modifiedGun.getModules().getAttachments().getScope();
                    xOffset = -translateX +  (modifiedGun.getModules().getZoom().getXOffset() * 0.0625) + -scaledPos.getXOffset() * 0.0625 * scaleX;
                    yOffset = -translateY + (8 - scaledPos.getYOffset()) * 0.0625 * scaleY - scope.getCenterOffset() * scaleY * 0.0625 * scaledPos.getScale();
                    zOffset = Config.CLIENT.display.sight1xRealisticPosition.get() && scope.getAdditionalZoom().getFovZoom() == 0 ? -translateZ + modifiedGun.getModules().getZoom().getZOffset() * 0.0625 * scaleZ :
                            -translateZ - scaledPos.getZOffset() * 0.0625 * scaleZ + 0.72 - viewFinderOffset * scaleZ * scaledPos.getScale();
                }
                else if (modifiedGun.canAttachType(IAttachment.Type.OLD_SCOPE) && scope != null) {
                    double viewFinderOffset = isScopeOffsetType || isScopeRenderType ? scope.getViewFinderOffsetSpecial() : scope.getViewFinderOffset(); // switches between either, but either must be populated
                    if (OptifineHelper.isShadersEnabled()) viewFinderOffset *= 0.735;
                    Gun.ScaledPositioned scaledPos = modifiedGun.getModules().getAttachments().getOldScope();
                    xOffset = -translateX +  (modifiedGun.getModules().getZoom().getXOffset() * 0.0625) + -scaledPos.getXOffset() * 0.0625 * scaleX;
                    yOffset = -translateY + (8 - scaledPos.getYOffset()) * 0.0625 * scaleY - scope.getCenterOffset() * scaleY * 0.0625 * scaledPos.getScale();
                    zOffset = -translateZ - scaledPos.getZOffset() * 0.0625 * scaleZ + 0.72 - viewFinderOffset * scaleZ * scaledPos.getScale();

                }
                else if (modifiedGun.canAttachType(IAttachment.Type.PISTOL_SCOPE) && scope != null) {
                    double viewFinderOffset = isScopeOffsetType || isScopeRenderType ? scope.getViewFinderOffsetSpecial() : scope.getViewFinderOffset(); // switches between either, but either must be populated
                    if (OptifineHelper.isShadersEnabled()) viewFinderOffset *= 0.735;
                    Gun.ScaledPositioned scaledPos = modifiedGun.getModules().getAttachments().getPistolScope();
                    xOffset = -translateX +  (modifiedGun.getModules().getZoom().getXOffset() * 0.0625) + -scaledPos.getXOffset() * 0.0625 * scaleX;
                    yOffset = -translateY + (8 - scaledPos.getYOffset()) * 0.0625 * scaleY - scope.getCenterOffset() * scaleY * 0.0625 * scaledPos.getScale();
                    zOffset = Config.CLIENT.display.sight1xRealisticPosition.get() && scope.getAdditionalZoom().getFovZoom() == 0 ? -translateZ + modifiedGun.getModules().getZoom().getZOffset() * 0.0625 * scaleZ :
                            -translateZ - scaledPos.getZOffset() * 0.0625 * scaleZ + 0.72 - viewFinderOffset * scaleZ * scaledPos.getScale();
                }
                else if (modifiedGun.getModules().getZoom() != null)
                {
                    xOffset = -translateX + modifiedGun.getModules().getZoom().getXOffset() * 0.0625 * scaleX;
                    yOffset = -translateY + (8 - modifiedGun.getModules().getZoom().getYOffset()-0.2) * 0.0625 * scaleY;
                    zOffset = -translateZ + modifiedGun.getModules().getZoom().getZOffset() * 0.0625 * scaleZ;
                }


                /* Controls the direction of the following translations, changes depending on the main hand. */
                float side = right ? 1.0F : -1.0F;
                //double transition = 1.0 - Math.pow(1.0 - AimingHandler.get().getNormalisedAdsProgress(), 2);

                double transition = (float) AimingHandler.get().getNormalisedAdsProgress();

                float result = aimingDynamics.update(0.05f, (float) transition);
                /* Reverses the original first person translations */
                //matrixStack.translate(-0.56 * side * transition, 0.52 * transition, 0);
                matrixStack.translate(  xOffset * side * result - 0.56 * side * result,
                                        yOffset * result + 0.52 * result + 0.033 - Math.abs(0.5 - result) * 0.066,
                                        zOffset * result);
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees((float) (5*(1-transition))) );
                /* Reverses the first person translations of the item in order to position it in the center of the screen */
                //matrixStack.translate(xOffset * side * transition, yOffset * transition, zOffset * transition);
                matrixStack.translate(0, 0.015*transition, 0);

                if(Config.COMMON.gameplay.realisticAimedBreathing.get()) {
                    /* Apply scope jitter*/
                    double scopeJitterOffset = 0.8;
                    if (scope == null)
                        scopeJitterOffset *= modifiedGun.getModules().getZoom().getStabilityOffset();
                    else
                        scopeJitterOffset *= scope.getStabilityOffset();
                    if (entity.isCrouching())
                        scopeJitterOffset *= 0.30;
                    if (entity.isSprinting() && !entity.isCrouching())
                        scopeJitterOffset *= 4;
                    if (entity.getDeltaMovement().x() != 0.0 || entity.getDeltaMovement().y() != 0.0 || entity.getDeltaMovement().z() != 0.0)
                        scopeJitterOffset *= 6.5;

                    double yOffsetRatio = ScopeJitterHandler.getInstance().getYOffsetRatio() * (0.0125 * 0.75 * scopeJitterOffset);
                    double xOffsetRatio = ScopeJitterHandler.getInstance().getXOffsetRatio() * (0.0085 * 0.875 * scopeJitterOffset);
                    Objects.requireNonNull(Minecraft.getInstance().player).setXRot((float) (Objects.requireNonNull(Minecraft.getInstance().player).getXRot() + yOffsetRatio));
                    Objects.requireNonNull(Minecraft.getInstance().player).setXRot((float) (Objects.requireNonNull(Minecraft.getInstance().player).getYRot() + xOffsetRatio));
                }
            }
        }

        float equipProgress = this.getEquipProgress(event.getPartialTicks());
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(equipProgress * -50F));

        HumanoidArm hand = right ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
        Objects.requireNonNull(entity);
        int blockLight = entity.isOnFire() ? 15 : entity.level.getBrightness(LightLayer.BLOCK, new BlockPos(entity.getEyePosition(event.getPartialTicks())));
        blockLight += (this.entityIdForMuzzleFlash.contains(entity.getId()) ? 3 : 0); // 3
        int packedLight = LightTexture.pack(blockLight, entity.level.getBrightness(LightLayer.SKY, new BlockPos(entity.getEyePosition(event.getPartialTicks()))));

        /* Renders the reload arm. Will only render if actually reloading. This is applied before
         * any recoil or reload rotations as the animations would be borked if applied after. */
        //this.renderReloadArm(matrixStack, event.getBuffers(), event.getLight(), modifiedGun, heldItem, hand);

        /* Translate the item position based on the hand side */
        int offset = right ? 1 : -1;
        matrixStack.translate(0.56 * offset, -0.52, -0.72);

        this.applySprintingTransforms(heldItem, hand, matrixStack, event.getPartialTicks());
        /* Applies recoil and reload rotations */
        this.applyRecoilTransforms(matrixStack, heldItem, modifiedGun);
        if(!isAnimated) this.applyReloadTransforms(matrixStack, hand, event.getPartialTicks(), heldItem);

        /* Renders the first persons arms from the grip type of the weapon */
        matrixStack.pushPose();
        IHeldAnimation pose = modifiedGun.getGeneral().getGripType().getHeldAnimation();
        if(pose!=null) {
            if(!isAnimated) matrixStack.translate(-0.56, 0.52, 0.72);
            if(!isAnimated) pose.renderFirstPersonArms(Minecraft.getInstance().player, hand, heldItem, matrixStack, event.getMultiBufferSource(), event.getPackedLight(), event.getPartialTicks());
        }
        matrixStack.popPose();


        /* Renders the weapon */
        ItemTransforms.TransformType transformType = right ? ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND;
        this.renderWeapon(Minecraft.getInstance().player, heldItem, transformType, event.getPoseStack(), event.getMultiBufferSource(), packedLight, event.getPartialTicks());
        matrixStack.popPose();
    }
    // Sprinting Offset Transition, the same transition aggregate used for all running anims,
    // made public for adjusting hands within animator instances
    public float sOT = 0.0f;
    public float wSpeed = 0.0f;
    private void applySprintingTransforms(ItemStack gun, HumanoidArm hand,
                                          PoseStack matrixStack, float partialTicks)
    {
        TimelessGunItem modifiedGun = (TimelessGunItem) gun.getItem();
        GunAnimationController controller = GunAnimationController.fromItem(gun.getItem());
        float draw = (controller == null || !controller.isAnimationRunning(GunAnimationController.AnimationLabel.DRAW) ? 1 : 0);
        float leftHanded = hand == HumanoidArm.LEFT ? -1 : 1;
        this.sOT = (this.prevSprintTransition + (this.sprintTransition - this.prevSprintTransition) * partialTicks) / 5F;
        //TODO: Speed of the held weapon, make a static method? it's not that useful but will be cleaner
        this.wSpeed = 0.1f / (1 + ((modifiedGun.getGun().getGeneral().getWeightKilo() * (1 + GunModifierHelper.getModifierOfWeaponWeight(gun)) + GunModifierHelper.getAdditionalWeaponWeight(gun) - GunEnchantmentHelper.getWeightModifier(gun)) * 0.0275f));
        // Light weight animation, used for SMGS and light rifles like the hk416
        if (wSpeed > 0.09) {
            // Translation
            float result = sprintDynamicsHSS.update(0.05f, sOT) * draw;

            // Rotating to the left a bit
            float result2 = sprintDynamicsZHSS.update(0.05f, sOT) * draw;

            matrixStack.translate(0.215 * leftHanded * result ,
                    0.07f * result , -30F * leftHanded * result / 170);
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(60f * result2));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(-25f * result2));
        }
        // Default
        else {
            //transition = (float) Math.sin((transition * Math.PI) / 2);
            float result = sprintDynamics.update(0.05f, sOT) * draw;
            float result2 = sprintDynamicsZ.update(0.05f, sOT) * draw;
            //matrixStack.translate(-0.25 * leftHanded * transition, -0.1 * transition, 0);
            matrixStack.translate(-0.25 * leftHanded * result, -0.1 * result - 0.1 + Math.abs(0.5 - result) * 0.2, 0);
            //matrixStack.rotate(Vector3f.YP.rotationDegrees(45F * leftHanded * transition));
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(28F * leftHanded * result));
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(15F * result2));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(20f * result2));
        }
    }

    private void applyReloadTransforms(PoseStack matrixStack, HumanoidArm hand, float partialTicks, ItemStack modifiedGun) {
        /*float reloadProgress = ReloadHandler.get().getReloadProgress(partialTicks, stack);
        matrixStack.translate(0, 0.35 * reloadProgress, 0);
        matrixStack.translate(0, 0, -0.1 * reloadProgress);
        matrixStack.rotate(Vector3f.XP.rotationDegrees(45F * reloadProgress));*/

        float reloadProgress = ReloadHandler.get().getReloadProgress(partialTicks,  modifiedGun);

        if(reloadProgress > 0) {
            float leftHanded = hand == HumanoidArm.LEFT ? -1 : 1;

            matrixStack.translate(-0.25 * leftHanded, -0.1, 0);
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(45F * leftHanded));
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(-25F));
        }
    }

    public float kickReduction = 0;
    public float recoilReduction = 0;
    public double kick = 0;
    public float recoilLift = 0;
    public float recoilSwayAmount = 0;
    public float recoilSway = 0;
    public float weaponsHorizontalAngle = 0;

    private void applyRecoilTransforms(PoseStack matrixStack, ItemStack item, Gun gun)
    {
        Minecraft mc = Minecraft.getInstance();
        double recoilNormal = RecoilHandler.get().getGunRecoilNormal();
        if (Gun.hasAttachmentEquipped(item, gun, IAttachment.Type.SCOPE)) {
            recoilNormal -= recoilNormal * (0.25 * AimingHandler.get().getNormalisedAdsProgress());
        }
        this.kickReduction = 1.0F - GunModifierHelper.getKickReduction(item);
        this.recoilReduction = 1.0F - GunModifierHelper.getRecoilModifier(item);
        this.kick = gun.getGeneral().getRecoilKick() * 0.0625 * recoilNormal * RecoilHandler.get().getAdsRecoilReduction(gun);
        //this.recoilLift = ((float) (gun.getGeneral().getRecoilAngle() * recoilNormal) * (float) RecoilHandler.get().getAdsRecoilReduction(gun));
        this.recoilLift = ((float) (RecoilHandler.get().getGunRecoilAngle() * recoilNormal) * (float) RecoilHandler.get().getAdsRecoilReduction(gun));
        this.recoilSwayAmount = ((float) (2F + 1F * (1.0 - AimingHandler.get().getNormalisedAdsProgress())));// * 1.5f;
        this.recoilSway = ((float) ((RecoilHandler.get().getGunRecoilRandom() * this.recoilSwayAmount - this.recoilSwayAmount / 2F) * recoilNormal)) / 2;
        if (item.getTag().getInt("CurrentFireMode") == 1)
            this.recoilSway *= 0.375;
        if (mc.player.isCrouching()) {
            this.recoilSway *= 0.75;
            this.recoilLift *= 0.875;
        }
    //    this.weaponsHorizontalAngle = ((float) (gun.getGeneral().getHorizontalRecoilAngle() * recoilNormal) * (float) RecoilHandler.get().getAdsRecoilReduction(gun));
        this.weaponsHorizontalAngle = ((float) (RecoilHandler.get().getGunHorizontalRecoilAngle() * recoilNormal) * (float) RecoilHandler.get().getAdsRecoilReduction(gun));
        float newKick = recoilDynamics.update(0.05f, (float) kick * kickReduction);
        float newSway = swayDynamics.update(0.05f, recoilSway * recoilReduction * weaponsHorizontalAngle);
        matrixStack.translate(0, 0, newKick);
        matrixStack.translate(0, 0, 0.35);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(newSway * 0.5f));
        matrixStack.mulPose(Vector3f.ZN.rotationDegrees(newSway)); // seems to be interesting to increase the force of
        //matrixStack.rotate(Vector3f.ZP.rotationDegrees(recoilSway * 2.5f * recoilReduction)); // seems to be interesting to increase the force of
        if(gun.getGeneral().getWeaponRecoilOffset() != 0)
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(this.recoilLift * this.recoilReduction));
        matrixStack.translate(0, 0, -0.35);
    }
    private int backwardTicker = 0;
    public void applyBobbingTransforms(PoseStack matrixStack, boolean convert){
        matrixStack.translate(0, 0, 0.25);
        float amplifier = bobbingDynamics.update(0.05f, (float) ((sprintTransition/2f + 1) * (1 - AimingHandler.get().getNormalisedAdsProgress() * 0.75) * (RecoilHandler.get().getRecoilProgress() == 0 ? 1 : 0)) );
        float speedUp = speedUpDynamics.update(0.05f, speedUpProgress * (1 - sOT) * (float) (1 - AimingHandler.get().getNormalisedAdsProgress())) ;
        float delta = -Mth.sin(walkingDistance1 * (float) Math.PI) * walkingCameraYaw * 0.5f * (convert ? -0.5f : 1) * amplifier * (8 - backwardTicker) / 8;
        float delta2 = -Mth.sin(walkingDistance1 * (float) Math.PI * 2f) * walkingCameraYaw * 0.5f * (convert ? -0.35f : 1) * amplifier * (12 - backwardTicker) / 12;
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(35f*delta*(float) (1 - AimingHandler.get().getNormalisedAdsProgress())));
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(-3f*speedUp));
        matrixStack.translate(0, 0, -0.25 + 0.07 * speedUp);
        matrixStack.translate(0.45 * delta,0.25 * delta2,0);
        if(wSpeed > 0.09) matrixStack.mulPose(Vector3f.XP.rotationDegrees(delta * 5f * sprintTransition));
        else  matrixStack.mulPose(Vector3f.XP.rotationDegrees(delta * 5f));
    }

    private float velocity = 0;
    private float acceleration = 0;
    private float stepLength = 0;
    private long prevTime = new Date().getTime();
    public void applyJumpingTransforms(PoseStack matrixStack, float partialTicks){
        if(Minecraft.getInstance().player == null) return;
        double posY = Mth.lerp(partialTicks, Minecraft.getInstance().player.yOld, Minecraft.getInstance().player.getY());
        float newVelocity = (float) (posY - Minecraft.getInstance().player.yOld)/partialTicks;
        float newAcceleration = newVelocity - velocity;
        Date date = new Date();
        if(Math.abs(acceleration) < Math.abs(newAcceleration) && Math.abs(newAcceleration) > 0.05) {
            acceleration = newAcceleration;
            stepLength = acceleration / 250f;
        }
        long partialTime = date.getTime() - prevTime;
        if(acceleration > 0) {
            acceleration -= partialTime * stepLength;
            if(acceleration < 0) acceleration = 0;
        }
        if(acceleration < 0) {
            acceleration -= partialTime * stepLength;
            if(acceleration > 0) acceleration = 0;
        }
        float maxMotion = 0.3f;
        float transition = - jumpingDynamics.update(0.05f, (Math.abs(acceleration) < maxMotion ? (acceleration / maxMotion) * 0.15f : Math.abs(acceleration) / acceleration * 0.15f) * (sprintTransition/3f + 1) * (1f - 0.7f * (float) AimingHandler.get().getNormalisedAdsProgress()));
        if(transition > 0) transition *= 0.8f;
        matrixStack.translate(0, transition,0);
        velocity = newVelocity;
        prevTime = date.getTime();
    }

    private final OneDimensionalPerlinNoise noiseX = new OneDimensionalPerlinNoise(-0.003f, 0.003f, 2400);
    private final OneDimensionalPerlinNoise noiseY = new OneDimensionalPerlinNoise(-0.003f, 0.003f, 2800);
    {
        noiseY.setReverse(true);
    }

    private final OneDimensionalPerlinNoise aimed_noiseX = new OneDimensionalPerlinNoise(-0.00075f, 0.00075f, 1650);
    private final OneDimensionalPerlinNoise aimed_noiseY = new OneDimensionalPerlinNoise(-0.00135f, 0.00135f, 1650);
    {
        noiseY.setReverse(true);
    }

    private final OneDimensionalPerlinNoise additionNoiseY = new OneDimensionalPerlinNoise(-0.002f, 0.002f, 1300);

    private final OneDimensionalPerlinNoise noiseRotationY = new OneDimensionalPerlinNoise(-0.8f, 0.8f, 2000);
    private final OneDimensionalPerlinNoise aimed_noiseRotationY = new OneDimensionalPerlinNoise(-0.25f, 0.25f, 1600);
    public void applyNoiseMovementTransform(PoseStack matrixStack){
        //matrixStack.translate(noiseX.getValue()* (1 - AimingHandler.get().getNormalisedAdsProgress()), (noiseY.getValue() + additionNoiseY.getValue()) * (1 - AimingHandler.get().getNormalisedAdsProgress()), 0);
        if(AimingHandler.get().getNormalisedAdsProgress() == 1) {
            matrixStack.translate(aimed_noiseX.getValue(), aimed_noiseY.getValue() + additionNoiseY.getValue(), 0);
            matrixStack.mulPose(Vector3f.YP.rotationDegrees((float) (aimed_noiseRotationY.getValue())));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees((float) (aimed_noiseRotationY.getValue()*0.85)));
        }
        else {
            matrixStack.translate(noiseX.getValue(), noiseY.getValue() + additionNoiseY.getValue(), 0);
            matrixStack.mulPose(Vector3f.YP.rotationDegrees((float) (noiseRotationY.getValue())));
        }

    }
    // Author: https://github.com/Charles445/DamageTilt/blob/1.16/src/main/java/com/charles445/damagetilt/MessageUpdateAttackYaw.java, continued by Timeless devs
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onKnockback(LivingKnockBackEvent event)
    {
        if(event.getEntityLiving() instanceof Player)
        {
            Player player = (Player) event.getEntityLiving();
            if(player.level.isClientSide)
                return;
            if(!(player.getMainHandItem().getItem() instanceof GunItem) && !Config.CLIENT.display.cameraShakeOptionGlobal.get())
                return;

            //Server Side
            if(player instanceof ServerPlayer)
            {
                ServerPlayer serverPlayer = (ServerPlayer)player;
                if(serverPlayer.connection == null)
                    return;
                if(serverPlayer.connection.getConnection() == null)
                    return;
                PacketHandler.getPlayChannel().sendTo(new MessagePlayerShake(player), serverPlayer.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.START))
            return;

        Minecraft mc = Minecraft.getInstance();
        if (!mc.isWindowActive())
            return;

        Player player = mc.player;
        if (player == null)
            return;

        if (Minecraft.getInstance().options.getCameraType() != CameraType.FIRST_PERSON)
            return;

        ItemStack heldItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (heldItem.isEmpty())
            return;

        if (player.isUsingItem() && player.getUsedItemHand() == InteractionHand.MAIN_HAND && heldItem.getItem() instanceof GrenadeItem)
        {
            if (!((GrenadeItem) heldItem.getItem()).canCook())
                return;

            int duration = player.getTicksUsingItem();
            if (duration >= 10) {
                float cookTime = 1.0F - ((float) (duration - 10) / (float) (player.getUseItem().getUseDuration() - 10));
                if (cookTime > 0.0F) {
                    double scale = 3;
                    Window window = mc.getWindow();
                    int i = (int) ((window.getGuiScaledHeight() / 2 - 7 - 60) / scale);
                    int j = (int) Math.ceil((window.getGuiScaledWidth() / 2 - 8 * scale) / scale);

                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);

                    PoseStack stack = RenderSystem.getModelViewStack();
                    stack.pushPose();
                    {
                        stack.scale((float) scale, (float) scale, (float) scale);
                        RenderSystem.applyModelViewMatrix();
                        int progress = (int) Math.ceil((cookTime) * 17.0F) - 1;
                        PoseStack matrixStack = new PoseStack();
                        Screen.blit(matrixStack, j, i, 36, 94, 16, 4, 256, 256);
                        Screen.blit(matrixStack, j, i, 52, 94, progress, 4, 256, 256);
                    }
                    stack.popPose();
                    RenderSystem.applyModelViewMatrix();

                    RenderSystem.disableBlend();
                }
            }
            return;
        }

        /*if (heldItem.getItem() instanceof GunItem) {
            Gun gun = ((GunItem) heldItem.getItem()).getGun(); // Cooldown stuffs, i should look into this
            if (!gun.getGeneral().isAuto()) {
                float coolDown = player.getCooldownTracker().getCooldown(heldItem.getItem(), event.renderTickTime);
                if (coolDown > 0.0F) {
                    double scale = 3;
                    MainWindow window = mc.getMainWindow();
                    int i = (int) ((window.getScaledHeight() / 2 - 7 - 60) / scale);
                    int j = (int) Math.ceil((window.getScaledWidth() / 2 - 8 * scale) / scale);

                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);

                    RenderSystem.pushMatrix();
                    {
                        RenderSystem.scaled(scale, scale, scale);
                        int progress = (int) Math.ceil((coolDown + 0.05) * 17.0F) - 1;
                        MatrixStack matrixStack = new MatrixStack();
                        Screen.blit(matrixStack, j, i, 36, 94, 16, 4, 256, 256);
                        Screen.blit(matrixStack, j, i, 52, 94, progress, 4, 256, 256);
                    }
                    RenderSystem.popMatrix();

                    RenderSystem.disableBlend();
                }
            }
        }*/
    }

    @SubscribeEvent
    public void onRenderHeldItem(RenderItemEvent.Held.Pre event) {
        InteractionHand hand = Minecraft.getInstance().options.mainHand == HumanoidArm.RIGHT ? event.getHandSide() == HumanoidArm.RIGHT ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND : event.getHandSide() == HumanoidArm.LEFT ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        LivingEntity entity = event.getEntity();
        ItemStack heldItem = entity.getItemInHand(hand);

        if (hand == InteractionHand.OFF_HAND) {
            if (heldItem.getItem() instanceof GunItem) {
                event.setCanceled(true);
                return;
            }

            if (entity.getMainHandItem().getItem() instanceof GunItem) {
                Gun modifiedGun = ((GunItem) entity.getMainHandItem().getItem()).getModifiedGun(entity.getMainHandItem());
                if (!modifiedGun.getGeneral().getGripType().getHeldAnimation().canRenderOffhandItem()) {
                    event.setCanceled(true);
                    return;
                }
            }
        }

        if (heldItem.getItem() instanceof GunItem) {
            event.setCanceled(true);

            if (heldItem.getTag() != null) {
                CompoundTag compound = heldItem.getTag();
                if (compound.contains("Scale", Tag.TAG_FLOAT)) {
                    float scale = compound.getFloat("Scale");
                    event.getPoseStack().scale(scale, scale, scale);
                }
            }

            Gun gun = ((GunItem) heldItem.getItem()).getModifiedGun(heldItem);
            if (entity instanceof Player) {
                gun.getGeneral().getGripType().getHeldAnimation().applyHeldItemTransforms((Player) entity, hand, AimingHandler.get().getAimProgress((Player) entity, event.getPartialTicks()), event.getPoseStack(), event.getBufferSource());
            }
            this.renderWeapon(entity, heldItem, event.getTransformType(), event.getPoseStack(), event.getBufferSource(), event.getLight(), event.getPartialTicks());
        }
    }

    @SubscribeEvent
    public void onSetupAngles(com.tac.guns.client.event.PlayerModelEvent.SetupAngles.Post event) {
        // Dirty hack to reject first person arms
        if (event.getAgeInTicks() == 0F) {
            event.getModelPlayer().rightArm.xRot = 0;
            event.getModelPlayer().rightArm.yRot = 0;
            event.getModelPlayer().rightArm.zRot = 0;
            event.getModelPlayer().leftArm.xRot = 0;
            event.getModelPlayer().leftArm.yRot = 0;
            event.getModelPlayer().leftArm.zRot = 0;
            return;
        }

        Player player = event.getPlayer();
        ItemStack heldItem = player.getMainHandItem();
        if (!heldItem.isEmpty() && heldItem.getItem() instanceof GunItem) {
            PlayerModel model = event.getModelPlayer();
            Gun gun = ((GunItem) heldItem.getItem()).getModifiedGun(heldItem);
            gun.getGeneral().getGripType().getHeldAnimation().applyPlayerModelRotation(player, model, InteractionHand.MAIN_HAND, AimingHandler.get().getAimProgress((Player) event.getEntity(), Minecraft.getInstance().getFrameTime()));
            copyModelAngles(model.rightArm, model.rightSleeve);
            copyModelAngles(model.leftArm, model.leftSleeve);
        }
    }

    private static void copyModelAngles(ModelPart source, ModelPart dest) {
        dest.xRot = source.xRot;
        dest.yRot = source.yRot;
        dest.zRot = source.zRot;
    }

    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Pre event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getMainHandItem();
        if (!heldItem.isEmpty() && heldItem.getItem() instanceof GunItem) {
            Gun gun = ((GunItem) heldItem.getItem()).getModifiedGun(heldItem);
            gun.getGeneral().getGripType().getHeldAnimation().applyPlayerPreRender(player, InteractionHand.MAIN_HAND, AimingHandler.get().getAimProgress((Player) event.getEntity(), event.getPartialTick()), event.getPoseStack(), event.getMultiBufferSource());
        }
    }

    @SubscribeEvent
    public void onModelRender(com.tac.guns.client.event.PlayerModelEvent.Render.Pre event) {
        Player player = event.getPlayer();
        ItemStack offHandStack = player.getOffhandItem();
        if (offHandStack.getItem() instanceof GunItem) {
            switch (player.getMainArm().getOpposite()) {
                case LEFT:
                    event.getModelPlayer().leftArmPose = HumanoidModel.ArmPose.EMPTY;
                    break;
                case RIGHT:
                    event.getModelPlayer().rightArmPose = HumanoidModel.ArmPose.EMPTY;
                    break;
            }
        }
    }

    @SubscribeEvent
    public void onRenderPlayer(com.tac.guns.client.event.PlayerModelEvent.Render.Post event) {
        PoseStack matrixStack = event.getPoseStack();
        Player player = event.getPlayer();
        ItemStack heldItem = player.getOffhandItem();
        if (!heldItem.isEmpty() && heldItem.getItem() instanceof GunItem) {
            matrixStack.pushPose();
            Gun gun = ((GunItem) heldItem.getItem()).getModifiedGun(heldItem);
            if (gun.getGeneral().getGripType().getHeldAnimation().applyOffhandTransforms(player, event.getModelPlayer(), heldItem, matrixStack, Minecraft.getInstance().getFrameTime())) {
                MultiBufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
                this.renderWeapon(player, heldItem, ItemTransforms.TransformType.FIXED, matrixStack, buffer, event.getLight(), Minecraft.getInstance().getFrameTime());
            }
            matrixStack.popPose();
        }
    }

    @SubscribeEvent
    public void onRenderEntityItem(RenderItemEvent.Entity.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        event.setCanceled(this.renderWeapon(mc.player, event.getItem(), event.getTransformType(), event.getPoseStack(), event.getBufferSource(), event.getLight(), event.getPartialTicks()));
    }

    @SubscribeEvent
    public void onRenderEntityItem(RenderItemEvent.Gui.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        event.setCanceled(this.renderWeapon(mc.player, event.getItem(), event.getTransformType(), event.getPoseStack(), event.getBufferSource(), event.getLight(), event.getPartialTicks()));
    }


    @SubscribeEvent
    public void onRenderItemFrame(RenderItemEvent.ItemFrame.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        event.setCanceled(this.renderWeapon(mc.player, event.getItem(), event.getTransformType(), event.getPoseStack(), event.getBufferSource(), event.getLight(), event.getPartialTicks()));
    }

    public boolean renderWeapon(LivingEntity entity, ItemStack stack, ItemTransforms.TransformType transformType, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light, float partialTicks) {
        if (stack.getItem() instanceof GunItem) {
            matrixStack.pushPose();

            ItemStack model = ItemStack.EMPTY;
            if (stack.getTag() != null) {
                if (stack.getTag().contains("Model", Tag.TAG_COMPOUND)) {
                    model = ItemStack.of(stack.getTag().getCompound("Model"));
                }
            }

            RenderUtil.applyTransformType(model.isEmpty() ? stack : model, matrixStack, transformType, entity);

            this.renderGun(entity, transformType, model.isEmpty() ? stack : model, matrixStack, renderTypeBuffer, light, partialTicks);
            this.renderAttachments(entity, transformType, stack, matrixStack, renderTypeBuffer, light, partialTicks);
            this.renderMuzzleFlash(entity, matrixStack, renderTypeBuffer, stack, transformType);

            matrixStack.popPose();
            return true;
        }
        return false;
    }

    public boolean renderScope(LivingEntity entity, ItemStack stack, ItemTransforms.TransformType transformType, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light, float partialTicks) {
        if (stack.getItem() instanceof ScopeItem) {
            matrixStack.pushPose();

            ItemStack model = ItemStack.EMPTY;

            RenderUtil.applyTransformType(model.isEmpty() ? stack : model, matrixStack, transformType, entity);

            this.renderGun(entity, transformType, model.isEmpty() ? stack : model, matrixStack, renderTypeBuffer, light, partialTicks);//matrixStack, renderTypeBuffer, light, partialTicks);
            matrixStack.popPose();
            return true;
        }
        return false;
    }
    /*public boolean renderColored(LivingEntity entity, IBakedModel model, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, float partialTicks) {

            matrixStack.push();

            RenderUtil.applyTransformTypeIB(model, matrixStack, transformType, entity);

            this.renderColoredModel(entity, transformType, model, matrixStack, renderTypeBuffer, light, partialTicks);//matrixStack, renderTypeBuffer, light, partialTicks);
            //this.renderAttachments(entity, transformType, stack, matrixStack, renderTypeBuffer, light, partialTicks);
            //this.renderMuzzleFlash(entity, matrixStack, renderTypeBuffer, stack, transformType);

            matrixStack.pop();
            return true;

        //return false;
    }*/
    private void renderGun(LivingEntity entity, ItemTransforms.TransformType transformType, ItemStack stack, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light, float partialTicks)
    {
        /*if(ModelOverrides.hasModel(stack) && transformType.equals(ItemCameraTransforms.TransformType.GUI))
            return;*/
        if(stack.getItem() instanceof ITimelessAnimated) RenderUtil.renderModel(stack, matrixStack, renderTypeBuffer, light, OverlayTexture.NO_OVERLAY, entity);
        if (ModelOverrides.hasModel(stack)) {
            IOverrideModel model = ModelOverrides.getModel(stack);
            if (model != null) {
                model.render(partialTicks, transformType, stack, ItemStack.EMPTY, entity, matrixStack, renderTypeBuffer, light, OverlayTexture.NO_OVERLAY);
            }
        } else {
            RenderUtil.renderModel(stack, matrixStack, renderTypeBuffer, light, OverlayTexture.NO_OVERLAY, entity);
        }
    }
    /*private void renderColoredModel(LivingEntity entity, ItemCameraTransforms.TransformType transformType, IBakedModel model, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, float partialTicks)
    {
        //if(stack.getItem() instanceof ITimelessAnimated) RenderUtil.renderModel(stack, matrixStack, renderTypeBuffer, light, OverlayTexture.NO_OVERLAY, entity);
        IOverrideModel model = ModelOverrides.getModel(stack);
        if (model != null) {
            model.render(partialTicks, transformType, stack, ItemStack.EMPTY, entity, matrixStack, renderTypeBuffer, light, OverlayTexture.NO_OVERLAY);
        }
    }*/

    private void renderAttachments(LivingEntity entity, ItemTransforms.TransformType transformType, ItemStack stack, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light, float partialTicks) {
        if (stack.getItem() instanceof GunItem) {
            Gun gun = ((GunItem) stack.getItem()).getModifiedGun(stack);
            CompoundTag gunTag = stack.getOrCreateTag();
            CompoundTag attachments = gunTag.getCompound("Attachments");
            for (String tagKey : attachments.getAllKeys()) {
                IAttachment.Type type = IAttachment.Type.byTagKey(tagKey);
                if (gun.canAttachType(type)) {
                    ItemStack attachmentStack = Gun.getAttachment(type, stack);
                    if (!attachmentStack.isEmpty()) {
                        Gun.ScaledPositioned positioned = gun.getAttachmentPosition(type);
                        if (positioned != null) {
                            matrixStack.pushPose();
                            GunAnimationController controller = GunAnimationController.fromItem(stack.getItem());
                            if(controller!=null){
                                if(type!=null && type.equals(IAttachment.Type.PISTOL_SCOPE)){
                                    if(controller instanceof PistalAnimationController) {
                                        //Minecraft.getInstance().player.sendChatMessage("test");
                                        PistalAnimationController pcontroller = (PistalAnimationController) controller;
                                        controller.applyTransform(stack,pcontroller.getSlideNodeIndex(),transformType,entity,matrixStack);
                                    }
                                }else
                                    controller.applyAttachmentsTransform(stack, transformType, entity, matrixStack);
                            }
                            double displayX = positioned.getXOffset() * 0.0625;
                            double displayY = positioned.getYOffset() * 0.0625;
                            double displayZ = positioned.getZOffset() * 0.0625;
                            matrixStack.translate(displayX, displayY, displayZ);
                            matrixStack.translate(0, -0.5, 0);
                            matrixStack.scale((float) positioned.getScale(), (float) positioned.getScale(), (float) positioned.getScale());

                            IOverrideModel model = ModelOverrides.getModel(attachmentStack);
                            if (model != null) {
                                model.render(partialTicks, transformType, attachmentStack, stack, entity, matrixStack, renderTypeBuffer, light, OverlayTexture.NO_OVERLAY);
                            } else {
                                RenderUtil.renderModel(attachmentStack, stack, matrixStack, renderTypeBuffer, light, OverlayTexture.NO_OVERLAY);
                            }

                            matrixStack.popPose();
                        }
                    }
                }
            }
        }
    }

    private void renderMuzzleFlash(LivingEntity entity, PoseStack matrixStack, MultiBufferSource buffer, ItemStack weapon, ItemTransforms.TransformType transformType) {
        Gun modifiedGun = ((GunItem) weapon.getItem()).getModifiedGun(weapon);
        if (modifiedGun.getDisplay().getFlash() == null) {
            return;
        }
        if (modifiedGun.canAttachType(IAttachment.Type.BARREL) && GunModifierHelper.isSilencedFire(weapon)) return;

        if (transformType == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND || transformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND || transformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND || transformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND) {
            if (this.entityIdForMuzzleFlash.contains(entity.getId()) ) {
                float randomValue = this.entityIdToRandomValue.get(entity.getId());
                this.drawMuzzleFlash(weapon, modifiedGun, randomValue, randomValue >= 0.5F, matrixStack, buffer);
            }
        }
    }

    private void drawMuzzleFlash(ItemStack weapon, Gun modifiedGun, float random, boolean flip, PoseStack matrixStack, MultiBufferSource buffer) {
        matrixStack.pushPose();

        Gun.Positioned muzzleFlash = modifiedGun.getDisplay().getFlash();
        if (muzzleFlash == null)
            return;

        double displayX = muzzleFlash.getXOffset() * 0.0625;
        double displayY = muzzleFlash.getYOffset() * 0.0625;
        double displayZ = (muzzleFlash.getZOffset()+this.muzzleExtraOnEnch) * 0.0625;

        if(GunRenderingHandler.get().muzzleExtraOnEnch != 0)
            this.muzzleExtraOnEnch = 0;

        matrixStack.translate(displayX, displayY, displayZ);
        matrixStack.translate(0, -0.5, 0);

        ItemStack barrelStack = Gun.getAttachment(IAttachment.Type.BARREL, weapon);
        if (!barrelStack.isEmpty() && barrelStack.getItem() instanceof IBarrel) {
            Barrel barrel = ((IBarrel) barrelStack.getItem()).getProperties();
            Gun.ScaledPositioned positioned = modifiedGun.getModules().getAttachments().getBarrel();
            if (positioned != null) {
                matrixStack.translate(0, 0, -barrel.getLength() * 0.0625 * positioned.getScale());
            }
        }

        matrixStack.scale(0.5F, 0.5F, 0.0F);

        double partialSize = modifiedGun.getDisplay().getFlash().getSize() / 5.0;
        float size = (float) (modifiedGun.getDisplay().getFlash().getSize() - partialSize + partialSize * random);
        size = (float) GunModifierHelper.getMuzzleFlashSize(weapon, size);
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(360F * random));
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(flip ? 180F : 0F));
        matrixStack.translate(-size / 2, -size / 2, 0);

        Matrix4f matrix = matrixStack.last().pose();
        VertexConsumer builder = buffer.getBuffer(GunRenderType.getMuzzleFlash());
        builder.vertex(matrix, 0, 0, 0).color(1.0F, 1.0F, 1.0F, 1.0F).uv(1.0F, 1.0F).uv2(15728880).endVertex();
        builder.vertex(matrix, size, 0, 0).color(1.0F, 1.0F, 1.0F, 1.0F).uv(0, 1.0F).uv2(15728880).endVertex();
        builder.vertex(matrix, size, size, 0).color(1.0F, 1.0F, 1.0F, 1.0F).uv(0, 0).uv2(15728880).endVertex();
        builder.vertex(matrix, 0, size, 0).color(1.0F, 1.0F, 1.0F, 1.0F).uv(1.0F, 0).uv2(15728880).endVertex();

        /*float smokeSize = (float) modifiedGun.getDisplay().getFlash().getSmokeSize();
        builder = buffer.getBuffer(GunRenderType.getMuzzleSmoke());
        matrixStack.translate(size / 2,size / 2,0);
        matrixStack.translate(-smokeSize / 2, -smokeSize / 2, 0);
        builder.pos(matrix, 0, 0, 0).color(1.0F, 1.0F, 1.0F, 1.0F).tex(1.0F, 1.0F).lightmap(15728880).endVertex();
        builder.pos(matrix, smokeSize, 0, 0).color(1.0F, 1.0F, 1.0F, 1.0F).tex(0, 1.0F).lightmap(15728880).endVertex();
        builder.pos(matrix, smokeSize, smokeSize, 0).color(1.0F, 1.0F, 1.0F, 1.0F).tex(0, 0).lightmap(15728880).endVertex();
        builder.pos(matrix, 0, smokeSize, 0).color(1.0F, 1.0F, 1.0F, 1.0F).tex(1.0F, 0).lightmap(15728880).endVertex();
*/
        matrixStack.popPose();
    }

    /*private void renderReloadArm(MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, Gun modifiedGun, ItemStack stack, HandSide hand) {
        *//*if (stack.getItem() instanceof IAnimatable && stack.getItem() instanceof GunItem) {

        }*//*
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.player.ticksExisted < ReloadHandler.get().getStartReloadTick() || ReloadHandler.get().getReloadTimer() != 5)
            return;

        Item item = ForgeRegistries.ITEMS.getValue(modifiedGun.getProjectile().getItem());
        if (item == null)
            return;

        matrixStack.push();

        float interval = GunEnchantmentHelper.getReloadInterval(stack);
        float reload = ((mc.player.ticksExisted - ReloadHandler.get().getStartReloadTick() + mc.getRenderPartialTicks()) % interval) / interval;
        float percent = 1.0F - reload;
        if (percent >= 0.5F) {
            percent = 1.0F - percent;
        }
        percent *= 2F;
        percent = percent < 0.5 ? 2 * percent * percent : -1 + (4 - 2 * percent) * percent;

        int side = hand.opposite() == HandSide.RIGHT ? 1 : -1;
        matrixStack.translate(-2.75 * side * 0.0625, -0.5625, -0.5625);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180F));
        matrixStack.translate(0, -0.35 * (1.0 - percent), 0);
        matrixStack.translate(side * 0.0625, 0, 0);
        matrixStack.rotate(Vector3f.XP.rotationDegrees(90F));
        matrixStack.rotate(Vector3f.YP.rotationDegrees(35F * -side));
        matrixStack.rotate(Vector3f.XP.rotationDegrees(-75F * percent));
        matrixStack.scale(0.5F, 0.5F, 0.5F);

        RenderUtil.renderFirstPersonArm(mc.player, hand.opposite(), matrixStack, buffer, light);

        if (reload < 0.5F) {
            matrixStack.push();
            matrixStack.translate(-side * 5 * 0.0625, 15 * 0.0625, -1 * 0.0625);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(180F));
            matrixStack.scale(0.75F, 0.75F, 0.75F);
            ItemStack ammo = new ItemStack(item, modifiedGun.getReloads().getReloadAmount());
            IBakedModel model = RenderUtil.getModel(ammo);
            boolean isModel = model.isGui3d();
            this.random.setSeed(Item.getIdFromItem(item));
            int count = Math.min(modifiedGun.getReloads().getReloadAmount(), 5);
            for (int i = 0; i < count; ++i) {
                matrixStack.push();
                if (i > 0) {
                    if (isModel) {
                        float x = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                        float y = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                        float z = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                        matrixStack.translate(x, y, z);
                    } else {
                        float x = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                        float y = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                        matrixStack.translate(x, y, 0);
                    }
                }

                RenderUtil.renderModel(ammo, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, matrixStack, buffer, light, OverlayTexture.NO_OVERLAY, null);
                matrixStack.pop();

                if (!isModel) {
                    matrixStack.translate(0.0, 0.0, 0.09375F);
                }
            }
            matrixStack.pop();
        }
        matrixStack.pop();
    }*/

    /**
     * A temporary hack to get the equip progress until Forge fixes the issue.
     *
     * @return
     */
    private float getEquipProgress(float partialTicks) {
        if (this.equippedProgressMainHandField == null) {
            this.equippedProgressMainHandField = ObfuscationReflectionHelper.findField(ItemInHandRenderer.class, "mainHandHeight");
            this.equippedProgressMainHandField.setAccessible(true);
        }
        if (this.prevEquippedProgressMainHandField == null) {
            this.prevEquippedProgressMainHandField = ObfuscationReflectionHelper.findField(ItemInHandRenderer.class, "oMainHandHeight");
            this.prevEquippedProgressMainHandField.setAccessible(true);
        }
        ItemInHandRenderer firstPersonRenderer = Minecraft.getInstance().getItemInHandRenderer();
        try {
            float equippedProgressMainHand = (float) this.equippedProgressMainHandField.get(firstPersonRenderer);
            float prevEquippedProgressMainHand = (float) this.prevEquippedProgressMainHandField.get(firstPersonRenderer);
            return 1.0F - Mth.lerp(partialTicks, prevEquippedProgressMainHand, equippedProgressMainHand);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0.0F;
    }
}
