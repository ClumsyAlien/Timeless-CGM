package com.tac.guns.init;

import com.mrcrayfish.obfuscate.common.data.Serializers;
import com.mrcrayfish.obfuscate.common.data.SyncedDataKey;
import com.mrcrayfish.obfuscate.common.data.SyncedPlayerData;
import com.tac.guns.Reference;
import net.minecraft.util.ResourceLocation;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class ModSyncedDataKeys
{
    public static final SyncedDataKey<Boolean> AIMING = SyncedDataKey.builder(Serializers.BOOLEAN)
            .id(new ResourceLocation(Reference.MOD_ID, "aiming"))
            .defaultValueSupplier(() -> false)
            .resetOnDeath()
            .build();

    public static final SyncedDataKey<Boolean> SHOOTING = SyncedDataKey.builder(Serializers.BOOLEAN)
            .id(new ResourceLocation(Reference.MOD_ID, "shooting"))
            .defaultValueSupplier(() -> false)
            .resetOnDeath()
            .build();

    public static final SyncedDataKey<Boolean> RELOADING = SyncedDataKey.builder(Serializers.BOOLEAN)
            .id(new ResourceLocation(Reference.MOD_ID, "reloading"))
            .defaultValueSupplier(() -> false)
            .resetOnDeath()
            .build();

    public static final SyncedDataKey<Float> MOVING = SyncedDataKey.builder(Serializers.FLOAT)
            .id(new ResourceLocation(Reference.MOD_ID, "moving"))
            .defaultValueSupplier(() -> 0f)
            .resetOnDeath()
            .build();

    // Create new moving tag, can use a number to be speed dependent, or another simple boolean

    public static void register()
    {
        SyncedPlayerData.instance().registerKey(AIMING);
        SyncedPlayerData.instance().registerKey(SHOOTING);
        SyncedPlayerData.instance().registerKey(RELOADING);
        SyncedPlayerData.instance().registerKey(MOVING);
    }
}
