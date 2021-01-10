package com.github.mmm1245.usefulutils.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IOnSlotClick {
	public void onSlotClick(ItemStack is, int slot, GUI gui, Player pl);
}
