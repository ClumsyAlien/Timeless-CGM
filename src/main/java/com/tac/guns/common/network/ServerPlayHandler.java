package com.tac.guns.common.network;

import com.tac.guns.Config;
import com.tac.guns.GunMod;
import com.tac.guns.Reference;
import com.tac.guns.client.handler.MovementAdaptationsHandler;
import com.tac.guns.client.handler.ShootingHandler;
import com.tac.guns.client.screen.UpgradeBenchScreen;
import com.tac.guns.common.*;
import com.tac.guns.common.container.*;
import com.tac.guns.crafting.WorkbenchRecipe;
import com.tac.guns.crafting.WorkbenchRecipes;
import com.tac.guns.entity.ProjectileEntity;
import com.tac.guns.event.GunFireEvent;
import com.tac.guns.init.ModBlocks;
import com.tac.guns.init.ModEnchantments;
import com.tac.guns.init.ModItems;
import com.tac.guns.init.ModSyncedDataKeys;
import com.tac.guns.interfaces.IProjectileFactory;
import com.tac.guns.item.GunItem;
import com.tac.guns.item.IColored;
import com.tac.guns.item.ScopeItem;
import com.tac.guns.item.TransitionalTypes.TimelessGunItem;
import com.tac.guns.item.attachment.IAttachment;
import com.tac.guns.network.PacketHandler;
import com.tac.guns.network.message.*;
import com.tac.guns.tileentity.FlashLightSource;
import com.tac.guns.tileentity.UpgradeBenchTileEntity;
import com.tac.guns.tileentity.WorkbenchTileEntity;
import com.tac.guns.util.GunEnchantmentHelper;
import com.tac.guns.util.GunModifierHelper;
import com.tac.guns.util.InventoryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

import static net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED;


/**
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

 * Anet.minecraft.world.entity.ai.attributes.Attributesimeless devs
 */
public class ServerPlayHandler
{
    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);
    private static final Predicate<LivingEntity> HOSTILE_ENTITIES = entity -> entity.getSoundSource() == SoundSource.HOSTILE && !Config.COMMON.aggroMobs.exemptEntities.get().contains(entity.getType().getRegistryName().toString());

    /**
     * Fires the weapon the player is currently holding.
     * This is only intended for use on the logical server.
     *
     * @param player the player for who's weapon to fire
     */
    public static void handleShoot(MessageShoot message, ServerPlayer player)
    {
        if(!player.isSpectator())
        {
            Level world = player.level;
            ItemStack heldItem = player.getItemInHand(InteractionHand.MAIN_HAND);
            if(heldItem.getItem() instanceof GunItem && (Gun.hasAmmo(heldItem) || player.isCreative()))
            {
                GunItem item = (GunItem) heldItem.getItem();
                Gun modifiedGun = item.getModifiedGun(heldItem);
                if(modifiedGun != null)
                {
                    if(MinecraftForge.EVENT_BUS.post(new GunFireEvent.Pre(player, heldItem)))
                        return;

                    /* Updates the yaw and pitch with the clients current yaw and pitch */
                    player.setYRot(message.getRotationYaw());
                    player.setXRot(message.getRotationPitch());

                    ShootTracker tracker = ShootTracker.getShootTracker(player);
                    if(tracker.hasCooldown(item))
                    {
                        GunMod.LOGGER.warn(player.getName().getContents() + "(" + player.getUUID() + ") tried to fire before cooldown finished or server is lagging? Remaining milliseconds: " + tracker.getRemaining(item));
                        ShootingHandler.get().setShootingError(true);
                        return;
                    }
                    tracker.putCooldown(heldItem, item, modifiedGun);

                    if(ModSyncedDataKeys.RELOADING.getValue(player))
                    {
                        //SyncedPlayerData.instance().set(player, ModSyncedDataKeys.RELOADING, false);
                    }

                    if(!modifiedGun.getGeneral().isAlwaysSpread() && modifiedGun.getGeneral().getSpread() > 0.0F)
                    {
                        SpreadTracker.get(player).update(player, item);
                    }

                    int count = modifiedGun.getGeneral().getProjectileAmount();
                    Gun.Projectile projectileProps = modifiedGun.getProjectile();
                    ProjectileEntity[] spawnedProjectiles = new ProjectileEntity[count];
                    for(int i = 0; i < count; i++)
                    {
                        IProjectileFactory factory = ProjectileManager.getInstance().getFactory(projectileProps.getItem());
                        ProjectileEntity projectileEntity = factory.create(world, player, heldItem, item, modifiedGun);
                        projectileEntity.setWeapon(heldItem);
                        projectileEntity.setAdditionalDamage(Gun.getAdditionalDamage(heldItem));
                        world.addFreshEntity(projectileEntity);
                        spawnedProjectiles[i] = projectileEntity;
                        projectileEntity.tick();
                    }
                    if(!projectileProps.isVisible())
                    {
                        MessageBulletTrail messageBulletTrail = new MessageBulletTrail(spawnedProjectiles, projectileProps, player.getId());
                        PacketHandler.getPlayChannel().send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(player.getX(), player.getY(), player.getZ(), Config.COMMON.network.projectileTrackingRange.get(), player.level.dimension())), messageBulletTrail);
                    }

                    MinecraftForge.EVENT_BUS.post(new GunFireEvent.Post(player, heldItem));

                    if(Config.COMMON.aggroMobs.enabled.get())
                    {
                        double radius = GunModifierHelper.getModifiedFireSoundRadius(heldItem, Config.COMMON.aggroMobs.range.get());
                        double x = player.getX();
                        double y = player.getY() + 0.5;
                        double z = player.getZ();
                        AABB box = new AABB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius);
                        radius *= radius;
                        double dx, dy, dz;
                        for(LivingEntity entity : world.getEntitiesOfClass(LivingEntity.class, box, HOSTILE_ENTITIES))
                        {
                            dx = x - entity.getX();
                            dy = y - entity.getY();
                            dz = z - entity.getZ();
                            if(dx * dx + dy * dy + dz * dz <= radius)
                            {
                                entity.setLastHurtByMob(Config.COMMON.aggroMobs.angerHostileMobs.get() ? player : entity);
                            }
                        }
                    }

                    boolean silenced = GunModifierHelper.isSilencedFire(heldItem);
                    ResourceLocation fireSound = silenced ? modifiedGun.getSounds().getSilencedFire() : modifiedGun.getSounds().getFire();
                    if(fireSound != null)
                    {
                        double posX = player.getX();
                        double posY = player.getY() + player.getEyeHeight();
                        double posZ = player.getZ();
                        float volume = GunModifierHelper.getFireSoundVolume(heldItem);
                        float pitch = 0.9F + world.random.nextFloat() * 0.2F;
                        double radius = GunModifierHelper.getModifiedFireSoundRadius(heldItem, Config.SERVER.gunShotMaxDistance.get());
                        boolean muzzle = modifiedGun.getDisplay().getFlash() != null;
                        MessageGunSound messageSound = new MessageGunSound(fireSound, SoundSource.PLAYERS, (float) posX, (float) posY, (float) posZ, volume, pitch, player.getId(), muzzle, false);
                        PacketDistributor.TargetPoint targetPoint = new PacketDistributor.TargetPoint(posX, posY, posZ, radius, player.level.dimension());
                        PacketHandler.getPlayChannel().send(PacketDistributor.NEAR.with(() -> targetPoint), messageSound);
                    }

                    if(!player.isCreative())
                    {
                        CompoundTag tag = heldItem.getOrCreateTag();
                        if(!tag.getBoolean("IgnoreAmmo"))
                        {
                            int level = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.RECLAIMED.get(), heldItem);
                            if(level == 0 || player.level.random.nextInt(4 - Mth.clamp(level, 1, 2)) != 0)
                            {
                                tag.putInt("AmmoCount", Math.max(0, tag.getInt("AmmoCount") - 1));
                            }
                        }
                    }
                }
            }
            else
            {
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, 0.8F);
            }
        }
    }

    /**
     * Crafts the specified item at the workstation the player is currently using.
     * This is only intended for use on the logical server.
     *
     * @param player the player who is crafting
     * @param id     the id of an item which is registered as a valid workstation recipe
     * @param pos    the block position of the workstation the player is using
     */
    public static void handleCraft(ServerPlayer player, ResourceLocation id, BlockPos pos)
    {
        Level world = player.level;

        if(player.containerMenu instanceof WorkbenchContainer)
        {
            WorkbenchContainer workbench = (WorkbenchContainer) player.containerMenu;
            if(workbench.getPos().equals(pos))
            {
                WorkbenchRecipe recipe = WorkbenchRecipes.getRecipeById(world, id);
                if(recipe == null || !recipe.hasMaterials(player))
                {
                    return;
                }

                //List<ItemStack> materials = recipe.getMaterials();
                //if(materials != null)
                {
                    recipe.consumeMaterials(player);

                    WorkbenchTileEntity workbenchTileEntity = workbench.getWorkbench();

                    /* Gets the color based on the dye */
                    ItemStack stack = recipe.getItem();
                    ItemStack dyeStack = workbenchTileEntity.getInventory().get(0);
                    if(dyeStack.getItem() instanceof DyeItem)
                    {
                        DyeItem dyeItem = (DyeItem) dyeStack.getItem();
                        float[] textureDiffuseColors = dyeItem.getDyeColor().getTextureDiffuseColors();
                        int color = ((int)(textureDiffuseColors[2] * 255)) << 16 | ((int)(textureDiffuseColors[1] * 255)) << 8 | ((int)(textureDiffuseColors[0] * 255));

                        if(stack.getItem() instanceof IColored && ((IColored) stack.getItem()).canColor(stack))
                        {
                            IColored colored = (IColored) stack.getItem();
                            colored.setColor(stack, color);
                            workbenchTileEntity.getInventory().set(0, ItemStack.EMPTY);
                        }
                    }

                    if(stack.getItem() instanceof TimelessGunItem)
                    {
                        if(stack.getTag() == null)
                        {
                            stack.getOrCreateTag();
                        }
                        GunItem gunItem = (GunItem) stack.getItem();
                        Gun gun = gunItem.getModifiedGun(stack);
                        int[] gunItemFireModes = stack.getTag().getIntArray("supportedFireModes");
                        if(ArrayUtils.isEmpty(gunItemFireModes))
                        {
                            gunItemFireModes = gun.getGeneral().getRateSelector();
                            stack.getTag().putIntArray("supportedFireModes", gunItemFireModes);
                        }
                        else if(!Arrays.equals(gunItemFireModes, gun.getGeneral().getRateSelector()))
                        {
                            stack.getTag().putIntArray("supportedFireModes", gun.getGeneral().getRateSelector());
                        }
                    }
                    Containers.dropItemStack(world, pos.getX() + 0.5, pos.getY() + 1.125, pos.getZ() + 0.5, stack);
                }
            }
        }
    }

    /**
     * @param player
     */
    public static void handleUnload(ServerPlayer player)
    {
        ItemStack stack = player.getMainHandItem();
        if(stack.getItem() instanceof GunItem)
        {
            CompoundTag tag = stack.getTag();
            GunItem gunItem = (GunItem) stack.getItem();
            Gun gun = gunItem.getModifiedGun(stack);
            if(tag != null && tag.contains("AmmoCount", Tag.TAG_INT))
            {
                int count = tag.getInt("AmmoCount");
                tag.putInt("AmmoCount", 0);

                ResourceLocation id = gun.getProjectile().getItem();

                Item item = ForgeRegistries.ITEMS.getValue(id);
                if(item == null)
                {
                    return;
                }

                int maxStackSize = item.getMaxStackSize();
                int stacks = count / maxStackSize;
                for(int i = 0; i < stacks; i++)
                {
                    spawnAmmo(player, new ItemStack(item, maxStackSize));
                }

                int remaining = count % maxStackSize;
                if(remaining > 0)
                {
                    spawnAmmo(player, new ItemStack(item, remaining));
                }
            }
            ResourceLocation reloadSound = gun.getSounds().getCock();
            if(reloadSound != null)
            {
                MessageGunSound message = new MessageGunSound(reloadSound, SoundSource.PLAYERS, (float) player.getX(), (float) player.getY() + 1.0F, (float) player.getZ(), 1.0F, 1.0F, player.getId(), false, true);
                PacketHandler.getPlayChannel().send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(player.getX(), (player.getY() + 1.0), player.getZ(), 16.0, player.level.dimension())), message);
            }
        }
    }

    /**
     * @param player
     * @param stack
     */
    private static void spawnAmmo(ServerPlayer player, ItemStack stack)
    {
        player.getInventory().add(stack);
        if(stack.getCount() > 0)
        {
            player.level.addFreshEntity(new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), stack.copy()));
        }
    }

    /**
     * @param player
     */
    public static void handleAttachments(ServerPlayer player)
    {
        ItemStack heldItem = player.getMainHandItem();
        if(heldItem.getItem() instanceof GunItem || heldItem.getItem() instanceof ScopeItem)
        {
            NetworkHooks.openGui(player, new SimpleMenuProvider((windowId, playerInventory, player1) -> new AttachmentContainer(windowId, playerInventory, heldItem), new TranslatableComponent("container.tac.attachments")));
        }
    }

    /**
     * @param player
     */
    public static void handleColorbenchGui(ServerPlayer player)
    {
        ItemStack heldItem = player.getMainHandItem();
        if(heldItem.getItem() instanceof GunItem || heldItem.getItem() instanceof ScopeItem)
        {
            NetworkHooks.openGui(player, new SimpleMenuProvider((windowId, playerInventory, player1) -> new ColorBenchContainer(windowId, playerInventory), new TranslatableComponent("container.tac.color_bench")));
        }
    }

    /**
     * @param player
     */
    public static void handleInspection(ServerPlayer player)
    {
        ItemStack heldItem = player.getMainHandItem();
        if(heldItem.getItem() instanceof GunItem)
        {
            NetworkHooks.openGui(player, new SimpleMenuProvider((windowId, playerInventory, player1) -> new InspectionContainer(windowId, playerInventory, heldItem), new TranslatableComponent("container.tac.inspection")));
        }
    }

    /**
     * @param player
     */
    public static void handleFireMode(ServerPlayer player)
    {
        ItemStack heldItem = player.getMainHandItem();
        if(heldItem.getItem() instanceof GunItem)
        {
            if(heldItem.getTag() == null)
            {
                heldItem.getOrCreateTag();
            }
            GunItem gunItem = (GunItem) heldItem.getItem();
            Gun gun = gunItem.getModifiedGun(heldItem);
            int[] gunItemFireModes = heldItem.getTag().getIntArray("supportedFireModes");
            if(ArrayUtils.isEmpty(gunItemFireModes))
            {
                gunItemFireModes = gun.getGeneral().getRateSelector();
                heldItem.getTag().putIntArray("supportedFireModes", gunItemFireModes);
            }
            else if(!Arrays.equals(gunItemFireModes, gun.getGeneral().getRateSelector()))
            {
                heldItem.getTag().putIntArray("supportedFireModes", gun.getGeneral().getRateSelector());
            }
            int toCheck = ArrayUtils.indexOf(gunItemFireModes, heldItem.getTag().getInt("CurrentFireMode"));
            if(toCheck >= (heldItem.getTag().getIntArray("supportedFireModes").length))
            {
                heldItem.getTag().remove("CurrentFireMode");
                heldItem.getTag().putInt("CurrentFireMode", gunItemFireModes[0]);
            }
            else
            {
                heldItem.getTag().remove("CurrentFireMode");
                heldItem.getTag().putInt("CurrentFireMode", heldItem.getTag().getIntArray("supportedFireModes")[toCheck+1]);
            }

            if(!Config.COMMON.gameplay.safetyExistence.get() && heldItem.getTag().getInt("CurrentFireMode") == 0 && gunItemFireModes.length > 1)
            {
                heldItem.getTag().remove("CurrentFireMode");
                heldItem.getTag().putInt("CurrentFireMode", heldItem.getTag().getIntArray("supportedFireModes")[1]);
            }
            else if(!Config.COMMON.gameplay.safetyExistence.get() && heldItem.getTag().getInt("CurrentFireMode") == 0)
            {
                heldItem.getTag().remove("CurrentFireMode");
                heldItem.getTag().putInt("CurrentFireMode", heldItem.getTag().getIntArray("supportedFireModes")[0]);
            }

            ResourceLocation fireModeSound = gun.getSounds().getCock(); // Use cocking sound for now
            if(fireModeSound != null && player.isAlive())
            {
                MessageGunSound messageSound = new MessageGunSound(fireModeSound, SoundSource.PLAYERS, (float) player.getX(), (float) (player.getY() + 1.0), (float) player.getZ(), 1F, 1F, player.getId(), false, false);
                PacketHandler.getPlayChannel().send(PacketDistributor.PLAYER.with(() ->(ServerPlayer) player), messageSound);
            }
        }
    }

    /**
     * @param player
     */
    public static void EmptyMag(ServerPlayer player)
    {
        ItemStack heldItem = player.getMainHandItem();
        if(heldItem.getItem() instanceof GunItem)
        {
            GunItem gunItem = (GunItem) heldItem.getItem();
            Gun gun = gunItem.getModifiedGun(heldItem);
            ResourceLocation fireModeSound = gun.getSounds().getCock(); // Use cocking sound for now
            if(fireModeSound != null && player.isAlive())
            {
                MessageGunSound messageSound = new MessageGunSound(fireModeSound, SoundSource.PLAYERS, (float) player.getX(), (float) (player.getY() + 1.0), (float) player.getZ(), 1.2F, 0.75F, player.getId(), false, false);
                PacketHandler.getPlayChannel().send(PacketDistributor.PLAYER.with(() ->(ServerPlayer) player), messageSound);
            }
        }
    }
    //ARCHIVE, THIS MAY CHANGE IN THE FUTURE CAUSE WTF
    /*public static void handleFireMode(ServerPlayerEntity player)
    {
        ItemStack heldItem = player.getHeldItemMainhand();
        if(heldItem.getItem() instanceof TimelessGunItem)
        {
            TimelessGunItem gunItem = (TimelessGunItem) heldItem.getItem();
            Gun gun = gunItem.getModifiedGun(heldItem.getStack());
            int[] gunItemFireModes = heldItem.getTag().getIntArray("supportedFireModes");
            if(ArrayUtils.isEmpty(gunItemFireModes))
            {
                gunItemFireModes = gun.getGeneral().getRateSelector();
                heldItem.getTag().putIntArray("supportedFireModes", gunItemFireModes);
            }
            else if(!Arrays.equals(gunItemFireModes, gun.getGeneral().getRateSelector()))
            {
                heldItem.getTag().putIntArray("supportedFireModes", gun.getGeneral().getRateSelector());
            }
            //int currMode = gun.getGeneral().getRateSelector().length >  ? heldItem.getTag().getInt("CurrentFireMode") : gun.getGeneral().getRateSelector()[0];
            int toCheck = ArrayUtils.indexOf(gunItemFireModes, heldItem.getTag().getInt("CurrentFireMode"));
            if(toCheck >= (heldItem.getTag().getIntArray("supportedFireModes").length))
            {
                heldItem.getTag().remove("CurrentFireMode");
                heldItem.getTag().putInt("CurrentFireMode", gunItemFireModes[0]);
            }
            else
            {
                heldItem.getTag().remove("CurrentFireMode");
                heldItem.getTag().putInt("CurrentFireMode", heldItem.getTag().getIntArray("supportedFireModes")[toCheck+1]);
            }

            if(!Config.COMMON.gameplay.safetyExistence.get() && heldItem.getTag().getInt("CurrentFireMode") == 0 && gunItemFireModes.length > 1)
            {
                heldItem.getTag().remove("CurrentFireMode");
                heldItem.getTag().putInt("CurrentFireMode", heldItem.getTag().getIntArray("supportedFireModes")[1]);
            }
            else if(!Config.COMMON.gameplay.safetyExistence.get() && heldItem.getTag().getInt("CurrentFireMode") == 0)
            {
                heldItem.getTag().remove("CurrentFireMode");
                heldItem.getTag().putInt("CurrentFireMode", heldItem.getTag().getIntArray("supportedFireModes")[0]);
            }

            ResourceLocation fireModeSound = gun.getSounds().getCock(); // Use cocking sound for now
            if(fireModeSound != null && player.isAlive())
            {
                MessageGunSound messageSound = new MessageGunSound(fireModeSound, SoundCategory.PLAYERS, (float) player.getPosX(), (float) (player.getPosY() + 1.0), (float) player.getPosZ(), 1F, 1F, player.getEntityId(), false, false);
                PacketHandler.getPlayChannel().send(PacketDistributor.PLAYER.with(() ->(ServerPlayerEntity) player), messageSound);
            }
        }
    }*/

    /**
     * @param player
     * @param lookingRange maximum range to attempt flashlight rendering
     */
    public static void handleFlashLight(ServerPlayer player, int[] lookingRange)
    {
        if(player.getMainHandItem().getItem() instanceof GunItem)
        {
            if(Gun.getAttachment(IAttachment.Type.SIDE_RAIL,player.getMainHandItem()) != null) {
                LevelAccessor world = player.level;
                BlockEntity tile = null;
                for (int itor: lookingRange)
                {
                    int x = lookingAt(player, itor).getX();
                    int y = lookingAt(player, itor).getY();
                    int z = lookingAt(player, itor).getZ();
                    boolean createLight = false;
                    for (int i = 0; i < 5; ++i) {
                        tile = world.getBlockEntity(new BlockPos(x, y, z));
                        if (tile instanceof FlashLightSource) {
                            createLight = true;
                            break;
                        }

                        if (!world.isEmptyBlock(new BlockPos(x, y, z))) {
                            int pX = (int) player.position().x();
                            int pY = (int) player.position().y();
                            int pZ = (int) player.position().z();
                            if (pX > x) {
                                ++x;
                            } else if (pX < x) {
                                --x;
                            }
                            if (pY > y) {
                                ++y;
                            } else if (pY < y) {
                                --y;
                            }
                            if (pZ > z) {
                                ++z;
                            } else if (pZ < z) {
                                --z;
                            }
                        } else if (world.isEmptyBlock(new BlockPos(x, y, z))) {
                            createLight = true;
                            break;
                        }
                    }

                    if (createLight) {
                        tile = world.getBlockEntity(new BlockPos(x, y, z));
                        if (tile instanceof FlashLightSource) {
                            ((FlashLightSource) tile).ticks = 0;
                        } else if (world.getBlockState(new BlockPos(x, y, z)).getBlock() != ModBlocks.FLASHLIGHT_BLOCK.get()) { //
                            world.setBlock(new BlockPos(x, y, z), (ModBlocks.FLASHLIGHT_BLOCK.get()).defaultBlockState(), 3);
                        }
                        world.setBlock(new BlockPos(x, y, z), (ModBlocks.FLASHLIGHT_BLOCK.get()).defaultBlockState(), 3);
                    }
                }
            }
        }
    }
    protected static BlockPos lookingAt(Player player, int rangeL)
    {
        //RayTraceResult entityPos = lookingAtEntity(player,rangeL);GunMod.LOGGER.log(Level.FATAL, entityPos.getType().toString());
        ///if(entityPos instanceof EntityRayTraceResult)
         //   return new BlockPos(entityPos.getHitVec());
        //else
            return ((BlockHitResult)player.pick((double)rangeL, 0.0F, false)).getBlockPos();

    }
    protected static HitResult lookingAtEntity(Player player, int rangeL)
    {
        return ((HitResult)player.pick((double)rangeL, 0.0F, false));
    }
    /**
     * @param player
     */
    /*public static void handleIronSightSwitch(ServerPlayerEntity player)
    {
        ItemStack heldItem = player.getHeldItemMainhand();
        if(heldItem.getItem() instanceof TimelessGunItem)
        {
            Gun gun = ((TimelessGunItem) heldItem.getItem()).getModifiedGun(heldItem.getStack());

            if(!ArrayUtils.isEmpty(gun.getModules().getZoom()))
            {
                int currentZoom = heldItem.getTag().getInt("currentZoom");

                if(currentZoom == (gun.getModules().getZoom().length-2))
                {
                    heldItem.getTag().remove("currentZoom");
                }
                else
                {
                    heldItem.getTag().remove("currentZoom");
                    heldItem.getTag().putInt("currentZoom",currentZoom+1);
                }
            }
        }
    }*/


    private static final UUID speedUptId = UUID.fromString("923e4567-e89b-42d3-a456-556642440000");

    // https://forums.minecraftforge.net/topic/40878-1102-solved-increasedecrease-walkspeed-without-fov-change/ Adapted for 1.16.5 use and for proper server function
    private static void changeGunSpeedMod(ServerPlayer entity, String name, double modifier)
    {
        AttributeModifier speedModifier = (new AttributeModifier(speedUptId, name, modifier, AttributeModifier.Operation.MULTIPLY_TOTAL));
        AttributeInstance attributeInstance = entity.getAttribute(MOVEMENT_SPEED);

        if (attributeInstance.getModifier(speedUptId) != null) {
            attributeInstance.removeModifier(speedModifier);
        }
        attributeInstance.addPermanentModifier(speedModifier);
    }

    private static void removeGunSpeedMod(ServerPlayer entity, String name, double modifier)
    {
        AttributeModifier speedModifier = (new AttributeModifier(speedUptId, name, modifier, AttributeModifier.Operation.MULTIPLY_TOTAL));
        AttributeInstance attributeInstance = entity.getAttribute(MOVEMENT_SPEED);

        if (attributeInstance.getModifier(speedUptId) != null) {
            attributeInstance.removeModifier(speedModifier);
        }
    }

    public static void handleMovementUpdate(ServerPlayer player, boolean handle)
    {
        if (player == null)
            return;
        if(player.isSpectator())
            return;
        if(!player.isAlive())
            return;

        ItemStack heldItem = player.getMainHandItem();
        if(player.getAttribute(MOVEMENT_SPEED) != null && MovementAdaptationsHandler.get().isReadyToReset())
        {
            removeGunSpeedMod(player,"GunSpeedMod", 0.1);
            MovementAdaptationsHandler.get().setReadyToReset(false);
            MovementAdaptationsHandler.get().setReadyToUpdate(true);
        }
        player.onUpdateAbilities();

        if (!(heldItem.getItem() instanceof TimelessGunItem))
            return;

        Gun gun = ((TimelessGunItem) heldItem.getItem()).getGun();
        //if(MovementAdaptationsHandler.get().previousGun == null || gun.serializeNBT().getId() == MovementAdaptationsHandler.get().previousGun)
            if (((gun.getGeneral().getWeightKilo() > 0) && MovementAdaptationsHandler.get().isReadyToUpdate()) || MovementAdaptationsHandler.get().getPreviousWeight() != gun.getGeneral().getWeightKilo())
            {
                float speed = (float)player.getAttribute(MOVEMENT_SPEED).getValue() / (1+((gun.getGeneral().getWeightKilo()*(1+GunModifierHelper.getModifierOfWeaponWeight(heldItem)) + GunModifierHelper.getAdditionalWeaponWeight(heldItem) - GunEnchantmentHelper.getWeightModifier(heldItem)) * 0.0275f)); // * 0.01225f));// //(1+GunModifierHelper.getModifierOfWeaponWeight(heldItem)) + GunModifierHelper.getAdditionalWeaponWeight(heldItem)) / 3.775F));
                if(player.isSprinting())
                    speed = Math.max(Math.min(speed, 0.12F), 0.075F) * 0.775F;
                else
                    speed = Math.max(Math.min(speed, 0.095F), 0.075F);
                changeGunSpeedMod(player, "GunSpeedMod", -((double)((0.1 - speed)*10)));//*1000

                MovementAdaptationsHandler.get().setReadyToReset(true);
                MovementAdaptationsHandler.get().setReadyToUpdate(false);
                MovementAdaptationsHandler.get().setSpeed(speed);
            }
            else
                MovementAdaptationsHandler.get().setSpeed((float)player.getAttribute(MOVEMENT_SPEED).getValue());
        player.onUpdateAbilities();

        MovementAdaptationsHandler.get().setPreviousWeight(gun.getGeneral().getWeightKilo());
        //DEBUGGING AND BALANCE TOOL
        //player.sendStatusMessage(new TranslationTextComponent(SyncedPlayerData.instance().get(player, ModSyncedDataKeys.MOVING)+""), true);
        //new TranslationTextComponent("Speed is: " + player
                // .getAttribute(MOVEMENT_SPEED).getValue()) ,true);
    }

    public static void handleGunID(ServerPlayer player, boolean regenerate)
    {
        if(!player.isAlive())
            return;
        if(NetworkGunManager.get() != null && NetworkGunManager.get().StackIds != null) {
            if (player.getMainHandItem().getItem() instanceof TimelessGunItem && player.getMainHandItem().getTag() != null) {
                if (regenerate||!player.getMainHandItem().getTag().contains("ID")) {
                    UUID id;
                    while (true) {
                        LOGGER.log(org.apache.logging.log4j.Level.INFO, "NEW UUID GEN FOR TAC GUN");
                        id = UUID.randomUUID();
                        if (NetworkGunManager.get().Ids.add(id))
                            break;
                    }
                    player.getMainHandItem().getTag().putUUID("ID", id);
                    NetworkGunManager.get().StackIds.put(id, player.getMainHandItem());
                }
                initLevelTracking(player.getMainHandItem());
            }
        }
    }

    private static void initLevelTracking(ItemStack gunStack)
    {
        if(gunStack.getTag().get("level") == null) {
            gunStack.getTag().putInt("level", 1);
        }
        if(gunStack.getTag().get("levelDmg") == null) {
            gunStack.getTag().putFloat("levelDmg", 0f);
        }
    }

    public static void handleUpgradeBenchItem(MessageSaveItemUpgradeBench message, ServerPlayer player)
    {
        if(!player.isSpectator())
        {
            Level world = player.level;
            ItemStack heldItem = player.getItemInHand(InteractionHand.MAIN_HAND);
            BlockEntity tileEntity = world.getBlockEntity(message.getPos());

            if(player.isCrouching())
            {
                NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) tileEntity, tileEntity.getBlockPos());
                return;
            }
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, 0.8F);
            //if item is empty or air stack in hand, take weapon, if holding weapon, take or replace weapon
            if (tileEntity != null)
            {
                // React to adding an extra Module item
                //if()

                if (!(((UpgradeBenchTileEntity) tileEntity).getItem(0).getItem() instanceof GunItem) && heldItem.getItem() instanceof GunItem)
                {
                    ((UpgradeBenchTileEntity) tileEntity).setItem(0, heldItem);
                    player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.AIR));
                    // I hate this last part, this is used in order to reset the TileRenderer,
                    // without this the item stack is added, but the visual is only reset on
                    // entering GUI, gotta Check what Yor said about this portion.
                    NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) tileEntity, tileEntity.getBlockPos());
                    player.closeContainer();
                }
                else if (heldItem.getItem() == ModItems.MODULE.get() && ((UpgradeBenchTileEntity) tileEntity).getItem(1).getCount() < 3)
                {
                    if( ((UpgradeBenchTileEntity) tileEntity).getItem(1).getItem() != ModItems.MODULE.get() ) {
                        ((UpgradeBenchTileEntity) tileEntity).setItem(1,
                                heldItem.copy());
                        ((UpgradeBenchTileEntity) tileEntity).getItem(1).setCount(1);
                    }
                    else {
                        ((UpgradeBenchTileEntity) tileEntity).getItem(1).setCount(((UpgradeBenchTileEntity) tileEntity).getItem(1).getCount() + 1);
                    }
                    player.getItemInHand(InteractionHand.MAIN_HAND).setCount(player.getItemInHand(InteractionHand.MAIN_HAND).getCount()-1);
                    /// I hate this last part, this is used in order to reset the TileRenderer,
                    // without this the item stack is added, but the visual is only reset on
                    // entering GUI, gotta Check what Yor said about this portion.
                    NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) tileEntity, tileEntity.getBlockPos());
                    player.closeContainer();
                }
                else
                {
                    player.getInventory().add(((UpgradeBenchTileEntity) tileEntity).getItem(0));
                    ((UpgradeBenchTileEntity) tileEntity).setItem(0, ItemStack.EMPTY);
                    // I hate this last part, this is used in order to reset the TileRenderer,
                    // without this the item stack is added, but the visual is only reset on
                    // entering GUI, gotta Check what Yor said about this portion.
                    NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) tileEntity, tileEntity.getBlockPos());
                    player.closeContainer();
                }
            }
        }
    }
    //remove enchant minecraft "modding" 1.16
    /**
     * Crafts the specified item at the workstation the player is currently using.
     * This is only intended for use on the logical server.
     *
     * @param player the player who is crafting
     */
    public static void handleUpgradeBenchApply(MessageUpgradeBenchApply message, ServerPlayer player)
    {
        if(player.containerMenu instanceof UpgradeBenchContainer)
        {
            UpgradeBenchContainer workbench = (UpgradeBenchContainer) player.containerMenu;
            UpgradeBenchScreen.RequirementItem req =
                    GunEnchantmentHelper.upgradeableEnchs.get(message.reqKey);
            if(workbench.getPos().equals(message.pos))
            {
                ItemStack toUpdate = workbench.getBench().getInventory().get(0);
                int currLevel =
                        EnchantmentHelper.getItemEnchantmentLevel(req.enchantment, toUpdate);
                if(toUpdate.getTag() == null)
                    return;
                int currWeaponLevel = toUpdate.getTag().getInt("level");
                TimelessGunItem gunItem = (TimelessGunItem) toUpdate.getItem();
                if(workbench.getBench().getItem(1).getCount() >= req.getModuleCount()[currLevel] && currWeaponLevel >= req.getLevelReq()[currLevel] && gunItem.getGun().getGeneral().getUpgradeBenchMaxUses() > toUpdate.getTag().getInt("upgradeBenchUses"))
                {
                    if (currLevel > 0) {
                        Map<Enchantment, Integer> listNBT =
                                EnchantmentHelper.deserializeEnchantments(toUpdate.getEnchantmentTags());
                        listNBT.replace(req.enchantment, currLevel + 1);
                        EnchantmentHelper.setEnchantments(listNBT, toUpdate);
                    } else
                        toUpdate.enchant(req.enchantment, 1);

                    workbench.getBench().getItem(1).setCount(workbench.getBench().getItem(1).getCount()-req.getModuleCount()[currLevel]);
                    toUpdate.getTag().putInt("upgradeBenchUses", toUpdate.getTag().getInt(
                            "upgradeBenchUses")+1);
                }
                else
                    player.displayClientMessage(new TranslatableComponent("Cannot apply enchants anymore"), true);
            }
        }
    }
}
