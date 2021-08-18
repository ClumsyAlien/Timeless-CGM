package com.tac.guns.item.TransitionalTypes;

import com.tac.guns.GunMod;
import com.tac.guns.item.AmmoItem;
import net.minecraft.item.Item;
import com.tac.guns.util.Process;


public class TimelessAmmoItem extends AmmoItem {
	public TimelessAmmoItem() {
		this(properties -> properties);
	}
	
	public TimelessAmmoItem(Process<Item.Properties> properties) {
		super(properties.process(new Item.Properties().stacksTo(64).tab(GunMod.GROUP)));
	}
}
