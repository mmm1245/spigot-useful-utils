package com.github.mmm1245.usefulutils.items.custom;

import org.bukkit.inventory.ItemStack;

public abstract class CustomItem {
	String internalName;
	public CustomItem(String registryName) {
		CustomItemRegistry.register(this, registryName);
		this.internalName = registryName;
	}
	public abstract ItemStack create();
	public abstract ItemStack create(int amount);
	public abstract boolean isSame(ItemStack is);
}