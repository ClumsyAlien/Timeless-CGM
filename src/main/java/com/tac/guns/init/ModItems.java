package com.tac.guns.init;

import com.tac.guns.GunMod;
import com.tac.guns.Reference;
import com.tac.guns.client.render.gun.model.ar15_p_renderer;
import com.tac.guns.common.GunModifiers;
import com.tac.guns.item.AmmoItem;
import com.tac.guns.item.BarrelItem;
import com.tac.guns.item.GrenadeItem;
import com.tac.guns.item.GunItem;
import com.tac.guns.item.ScopeItem;
import com.tac.guns.item.StockItem;
import com.tac.guns.item.StunGrenadeItem;
import com.tac.guns.item.TransitionalTypes.AnimatedAutomaticTimelessGunItem;
import com.tac.guns.item.TransitionalTypes.AutomaticTimelessGunItem;
import com.tac.guns.item.TransitionalTypes.TimelessAmmoItem;
import com.tac.guns.item.TransitionalTypes.TimelessGunItem;
import com.tac.guns.item.UnderBarrelItem;
import com.tac.guns.item.attachment.impl.Barrel;
import com.tac.guns.item.attachment.impl.Scope;
import com.tac.guns.item.attachment.impl.Stock;
import com.tac.guns.item.attachment.impl.UnderBarrel;
import com.tac.guns.item.gun.ar15_p_item;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import com.tac.guns.Config;

public class ModItems
{
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
    /*
    public static final RegistryObject<GunItem> PISTOL = REGISTER.register("pistol", () -> new GunItem(new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));
    public static final RegistryObject<Item> SHOTGUN = REGISTER.register("shotgun", () -> new GunItem(new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));
    public static final RegistryObject<Item> RIFLE = REGISTER.register("rifle", () -> new GunItem(new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));
    public static final RegistryObject<GunItem> GRENADE_LAUNCHER = REGISTER.register("grenade_launcher", () -> new GunItem(new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));
    public static final RegistryObject<Item> BAZOOKA = REGISTER.register("bazooka", () -> new GunItem(new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));
    public static final RegistryObject<Item> MINI_GUN = REGISTER.register("mini_gun", () -> new GunItem(new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));
    public static final RegistryObject<GunItem> ASSAULT_RIFLE = REGISTER.register("assault_rifle", () -> new GunItem(new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));
    public static final RegistryObject<Item> MACHINE_PISTOL = REGISTER.register("machine_pistol", () -> new GunItem(new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));
    public static final RegistryObject<Item> HEAVY_RIFLE = REGISTER.register("heavy_rifle", () -> new GunItem(new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));
    */

    public static final RegistryObject<TimelessGunItem> M1911 = REGISTER.register("m1911", TimelessGunItem::new);
    public static final RegistryObject<TimelessGunItem> M1894 = REGISTER.register("m1894", TimelessGunItem::new);
    public static final RegistryObject<TimelessGunItem> M1851 = REGISTER.register("m1851", TimelessGunItem::new);
    public static final RegistryObject<TimelessGunItem> M1928 = REGISTER.register("m1928", () -> new AutomaticTimelessGunItem(properties -> properties, Config.SERVER.gunHandlingCustomization.M1928_trigMax));
    public static final RegistryObject<TimelessGunItem> MOSIN = REGISTER.register("mosin", TimelessGunItem::new);
    public static final RegistryObject<TimelessGunItem> AK47 = REGISTER.register("ak47", () -> new AutomaticTimelessGunItem(properties -> properties, Config.SERVER.gunHandlingCustomization.AK47_trigMax));
    public static final RegistryObject<TimelessGunItem> M60 = REGISTER.register("m60", () -> new AutomaticTimelessGunItem(properties -> properties, Config.SERVER.gunHandlingCustomization.M60_trigMax));
    public static final RegistryObject<TimelessGunItem> M1917 = REGISTER.register("m1917", TimelessGunItem::new);
    public static final RegistryObject<TimelessGunItem> GLOCK_17 = REGISTER.register("glock_17", () -> new TimelessGunItem(properties -> properties.tab(GunMod.GROUP)));
    public static final RegistryObject<TimelessGunItem> DP_28 = REGISTER.register("dp28", () -> new AutomaticTimelessGunItem(properties -> properties, Config.SERVER.gunHandlingCustomization.DP28_trigMax));
    public static final RegistryObject<TimelessGunItem> M16A1 = REGISTER.register("m16a1", () -> new AutomaticTimelessGunItem(properties -> properties, Config.SERVER.gunHandlingCustomization.M16A1_trigMax));
    public static final RegistryObject<TimelessGunItem> MK18 = REGISTER.register("mk18", () -> new TimelessGunItem(properties -> properties.tab(GunMod.GROUP)));
    public static final RegistryObject<TimelessGunItem> STI2011 = REGISTER.register("sti2011", () -> new TimelessGunItem(properties -> properties.tab(GunMod.GROUP)));
    public static final RegistryObject<TimelessGunItem> AK74 = REGISTER.register("ak74", () -> new AutomaticTimelessGunItem(properties -> properties.tab(GunMod.GROUP), Config.SERVER.gunHandlingCustomization.AK74_trigMax));
    public static final RegistryObject<TimelessGunItem> M92FS = REGISTER.register("m92fs", () -> new TimelessGunItem(properties -> properties.tab(GunMod.GROUP)));
    public static final RegistryObject<TimelessGunItem> AR15_HELLMOUTH = REGISTER.register("ar_15_hellmouth", () -> new AutomaticTimelessGunItem(properties -> properties.tab(GunMod.GROUP), Config.SERVER.gunHandlingCustomization.AR15HM_trigMax));
    public static final RegistryObject<TimelessGunItem> AR15_P = REGISTER.register("ar_15_p", () -> new ar15_p_item(properties -> properties.tab(GunMod.GROUP)));
    public static final RegistryObject<TimelessGunItem> VECTOR45 = REGISTER.register("vector45", () -> new AutomaticTimelessGunItem(properties -> properties.tab(GunMod.GROUP), Config.SERVER.gunHandlingCustomization.VECTOR45_trigMax));
    public static final RegistryObject<TimelessGunItem> MICRO_UZI = REGISTER.register("micro_uzi", () -> new TimelessGunItem(properties -> properties.tab(GunMod.GROUP)));
    public static final RegistryObject<TimelessGunItem> M4 = REGISTER.register("m4", () -> new AutomaticTimelessGunItem(properties -> properties.tab(GunMod.GROUP), Config.SERVER.gunHandlingCustomization.AR15P_trigMax));
    public static final RegistryObject<TimelessGunItem> M1911_NETHER = REGISTER.register("m1911_nether", () -> new TimelessGunItem(Item.Properties::fireResistant) {
        public int getItemEnchantability() {
            return 12;
        }
    });
    public static final RegistryObject<TimelessGunItem> MOSBERG590 = REGISTER.register("mosberg590", () -> new TimelessGunItem(properties -> properties.tab(GunMod.GROUP)));
    public static final RegistryObject<TimelessGunItem> DB_SHORT = REGISTER.register("db_short", TimelessGunItem::new);
    public static final RegistryObject<TimelessGunItem> DB_LONG = REGISTER.register("db_long", TimelessGunItem::new);
    public static final RegistryObject<TimelessGunItem> WALTHER_PPK = REGISTER.register("walther_ppk", () -> new TimelessGunItem(properties -> properties.tab(GunMod.GROUP)));
    public static final RegistryObject<TimelessGunItem> M24 = REGISTER.register("m24", () -> new TimelessGunItem(properties -> properties.tab(GunMod.GROUP)));
    // Here I also create some new Ammunition for my mod! Not a necessary piece as you can continue using the original "cgm:" ammo!
    public static final RegistryObject<AmmoItem> MAGNUM_BULLET = REGISTER.register("magnumround", TimelessAmmoItem::new);
    public static final RegistryObject<AmmoItem> BULLET_45 = REGISTER.register("round45", TimelessAmmoItem::new);
    public static final RegistryObject<AmmoItem> BULLET_30_WIN = REGISTER.register("win_30-30", TimelessAmmoItem::new);
    public static final RegistryObject<AmmoItem> BULLET_308 = REGISTER.register("bullet_308", TimelessAmmoItem::new);
    public static final RegistryObject<AmmoItem> BULLET_556 = REGISTER.register("nato_556_bullet", TimelessAmmoItem::new);
    public static final RegistryObject<AmmoItem> BULLET_9 = REGISTER.register("9mm_round", TimelessAmmoItem::new);
    public static final RegistryObject<AmmoItem> BULLET_10g = REGISTER.register("10_gauge_round", TimelessAmmoItem::new);

    public static final RegistryObject<Item> COYOTE_SIGHT = REGISTER.register("coyote_sight", () -> new ScopeItem(Scope.create(0.1F, 1.645F, GunModifiers.SLOW_ADS).viewFinderOffset(0.3), new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));

    public static final RegistryObject<Item> MIDRANGE_DOT_SCOPE = REGISTER.register("midrange_dot_scope", () -> new ScopeItem(Scope.create(0.35F, 1.485F, GunModifiers.SLOW_ADS).viewFinderOffset(0.3), new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));

    // CGM ammo
    /*
    public static final RegistryObject<Item> BASIC_BULLET = REGISTER.register("basic_bullet", () -> new AmmoItem(new Item.Properties().tab(GunMod.GROUP)));
    public static final RegistryObject<Item> ADVANCED_AMMO = REGISTER.register("advanced_bullet", () -> new AmmoItem(new Item.Properties().tab(GunMod.GROUP)));
    public static final RegistryObject<Item> SHELL = REGISTER.register("shell", () -> new AmmoItem(new Item.Properties().tab(GunMod.GROUP)));
    public static final RegistryObject<Item> MISSILE = REGISTER.register("missile", () -> new AmmoItem(new Item.Properties().tab(GunMod.GROUP)));
    public static final RegistryObject<Item> GRENADE = REGISTER.register("grenade", () -> new GrenadeItem(new Item.Properties().tab(GunMod.GROUP), 20 * 4));
    public static final RegistryObject<Item> STUN_GRENADE = REGISTER.register("stun_grenade", () -> new StunGrenadeItem(new Item.Properties().tab(GunMod.GROUP), 72000));
    */
    
    /* Scope Attachments */
    public static final RegistryObject<Item> SHORT_SCOPE = REGISTER.register("short_scope", () -> new ScopeItem(Scope.create(0.1F, 1.55F, GunModifiers.SLOW_ADS).viewFinderOffset(0.3), new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));
    public static final RegistryObject<Item> MEDIUM_SCOPE = REGISTER.register("medium_scope", () -> new ScopeItem(Scope.create(0.25F, 1.625F, GunModifiers.SLOW_ADS).viewFinderOffset(0.3), new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));
    public static final RegistryObject<Item> LONG_SCOPE = REGISTER.register("long_scope", () -> new ScopeItem(Scope.create(0.4F, 1.4F, GunModifiers.SLOWER_ADS).viewFinderOffset(0.275), new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));

    /* Barrel Attachments */
    public static final RegistryObject<Item> SILENCER = REGISTER.register("silencer", () -> new BarrelItem(Barrel.create(8.0F, GunModifiers.SILENCED, GunModifiers.REDUCED_DAMAGE), new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));

    /* Stock Attachments */
    public static final RegistryObject<Item> LIGHT_STOCK = REGISTER.register("light_stock", () -> new StockItem(Stock.create(GunModifiers.BETTER_CONTROL), new Item.Properties().stacksTo(1).tab(GunMod.GROUP), false));
    public static final RegistryObject<Item> TACTICAL_STOCK = REGISTER.register("tactical_stock", () -> new StockItem(Stock.create(GunModifiers.STABILISED), new Item.Properties().stacksTo(1).tab(GunMod.GROUP), false));
    public static final RegistryObject<Item> WEIGHTED_STOCK = REGISTER.register("weighted_stock", () -> new StockItem(Stock.create(GunModifiers.SUPER_STABILISED), new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));

    /* Under Barrel Attachments */
    public static final RegistryObject<Item> LIGHT_GRIP = REGISTER.register("light_grip", () -> new UnderBarrelItem(UnderBarrel.create(GunModifiers.LIGHT_RECOIL), new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));
    public static final RegistryObject<Item> SPECIALISED_GRIP = REGISTER.register("specialised_grip", () -> new UnderBarrelItem(UnderBarrel.create(GunModifiers.REDUCED_RECOIL), new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));
}
