package com.github.mmm1245.usefulutils.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import com.github.mmm1245.usefulutils.gui.GUI;
import com.github.mmm1245.usefulutils.gui.IOnSlotClick;

public class GUIListener implements Listener{
	@EventHandler
	public void itemDrag(InventoryDragEvent e) {
		if(e.getInventory().getHolder() instanceof GUI)
			e.setCancelled(true);
	}
	@EventHandler
	public void itemClick(InventoryClickEvent e) {
		if(e.getInventory().getHolder() instanceof GUI) {
			if(e.getClickedInventory() == e.getInventory()) {
				IOnSlotClick slotClick = ((GUI)e.getInventory().getHolder()).slotClicks[e.getSlot()];
				if(slotClick != null)
					slotClick.onSlotClick(e.getCurrentItem(), e.getSlot(), (GUI) e.getInventory().getHolder(), (Player) e.getWhoClicked());
			}
			e.setCancelled(true);
		}
	}
}
