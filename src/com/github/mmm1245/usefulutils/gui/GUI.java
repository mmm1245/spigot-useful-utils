package com.github.mmm1245.usefulutils.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class GUI implements InventoryHolder{
	public IOnSlotClick[] slotClicks;
	public Inventory inventory;
	public GUIData data;
	GUI(IOnSlotClick[] slotClicks, ItemStack[] itemStacks, String name, GUIData data) {
		this.slotClicks = slotClicks;
		this.inventory = Bukkit.createInventory(this, itemStacks.length, name);
		this.inventory.setContents(itemStacks);
		this.data = data;
	}
	@Override
	public Inventory getInventory() {
		return inventory;
	}
	public void showPlayer(Player pl) {
		pl.openInventory(inventory);
	}
}
