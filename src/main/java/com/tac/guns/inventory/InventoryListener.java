package com.tac.guns.inventory;

import com.tac.guns.Reference;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Method;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class InventoryListener {

    public static Capability<IAmmoItemHandler> ITEM_HANDLER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    public static Method addSlotMethod;

    /*@SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntityJoin(EntityJoinWorldEvent event) throws InvocationTargetException, IllegalAccessException {
        if(!(event.getEntity() instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) event.getEntity();

        if(addSlotMethod == null) {
            addSlotMethod = ObfuscationReflectionHelper.findMethod(Container.class, "addSlot", Slot.class);
        }
        AmmoItemStackHandler ammoItemHandler = (AmmoItemStackHandler) player.getCapability(ITEM_HANDLER_CAPABILITY).resolve().get();
        addSlotMethod.invoke(player.container, new AmmoPackSlot(ammoItemHandler, 0, 170, 84));
        addSlotMethod.invoke(player.container, new BackpackSlot(ammoItemHandler, 1, 170, 102));
    }*/

    /*@SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) throws InvocationTargetException, IllegalAccessException {
        if(!(event.getObject() instanceof PlayerEntity)) return;
        AmmoInventoryCapability ammoInventoryCapability = new AmmoInventoryCapability(new AmmoItemStackHandler(2));
        event.addCapability(new ResourceLocation("tac", "inventory_capability"), ammoInventoryCapability);
        event.addListener(ammoInventoryCapability.getOptionalStorage()::invalidate);
    }*/
}
