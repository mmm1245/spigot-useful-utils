package com.github.mmm1245.usefulutils;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.mmm1245.usefulutils.gui.GUI;
import com.github.mmm1245.usefulutils.gui.GUIFactory;
import com.github.mmm1245.usefulutils.gui.IOnSlotClick;
import com.github.mmm1245.usefulutils.items.ItemGen;
import com.github.mmm1245.usefulutils.items.custom.CustomItem;
import com.github.mmm1245.usefulutils.items.custom.CustomItemRegistry;
import com.github.mmm1245.usefulutils.listeners.GUIListener;
import com.github.mmm1245.usefulutils.listeners.PlayerLogEvent;
import com.github.mmm1245.usefulutils.serialization.ItemSerializer;

public class Main extends JavaPlugin{
	@Override
	public void onEnable() {
		Bukkit.getLogger().warning("spigotapi enabled");
		Bukkit.getPluginManager().registerEvents(new PlayerLogEvent(), this);
		Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
	}
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) 
			return false;
		Player pla = (Player) sender;
		if(label.equals("ser")) {
			try {
				pla.sendMessage(ItemSerializer.serialize(pla.getItemInHand()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		if(label.equals("deser") && args.length == 1) {
			try {
				pla.getInventory().addItem((ItemSerializer.deserialize(args[0])));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		if(label.equals("test")) {
			try {
				pla.getInventory().addItem((ItemSerializer.deserialize(ItemSerializer.serialize(pla.getItemInHand()))));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		if(label.equals("gui")) {
			GUIFactory factory = new GUIFactory("randomgui", 27);
			factory.set(5, ItemGen.createItem(Material.ACACIA_BOAT, 1, "lol"), new IOnSlotClick() {
				
				@Override
				public void onSlotClick(ItemStack is, int slot, GUI gui, Player pl) {
					pl.sendMessage("clicked: " + is.getItemMeta().getDisplayName());
				}
			});
			factory.set(10, ItemGen.createItem(Material.ACACIA_BOAT, 1, "lolb"), new IOnSlotClick() {
				
				@Override
				public void onSlotClick(ItemStack is, int slot, GUI gui, Player pl) {
					pl.sendMessage("bclicked: " + is.getItemMeta().getDisplayName());
				}
			});
			factory.create().showPlayer(pla);
			return true;
		}
		if(label.equals("holo")) {
			PlayerLogEvent.holohram.setText("zmena");
			PlayerLogEvent.holohram.update(pla);
			return true;
		}
		if(label.equals("item") && (args.length == 1 || args.length == 2)) {
			if(!pla.isOp()) {
				pla.sendMessage(ChatColor.RED + "You must be server operator to execute this command");
				return true;
			}
			CustomItem item = CustomItemRegistry.get(args[0]);
			if(item == null) {
				pla.sendMessage(ChatColor.RED + "Item " + args[0] + " doesn't exist");
				return true;
			}
			if(args.length == 1) {
				pla.getInventory().addItem(item.create());
			} else {
				pla.getInventory().addItem(item.create(Integer.valueOf(args[1])));
			}
			return true;
		}
		return false;
	}
}
