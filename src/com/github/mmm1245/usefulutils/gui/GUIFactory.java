package com.github.mmm1245.usefulutils.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class GUIFactory{
	String name;
	ItemStack[] itemStacks;
	IOnSlotClick[] slotClicks;
	int size;
	public GUIFactory(String name, int size) {
		this.name = name;
		this.size = size;
		this.itemStacks = new ItemStack[size];
		this.slotClicks = new IOnSlotClick[size];
	}
	public void set(int slot, ItemStack is, IOnSlotClick onSlotClick) {
		if(slot < 0)
			return;
		if(slot >= this.size)
			return;
		this.itemStacks[slot] = is;
		this.slotClicks[slot] = onSlotClick;
	}
	public GUI create() {
		return new GUI(slotClicks, itemStacks, name, null);
	}
	public GUI create(GUIData data) {
		return new GUI(slotClicks, itemStacks, name, data);
	}
}
