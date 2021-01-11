package com.github.mmm1245.usefulutils.items;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemGen {
	public static ItemStack createItem(Material material, int amount, String name, String... lore) {
		ItemStack is = new ItemStack(material, amount);
		ItemMeta im = is.getItemMeta();
		if(name != null)
			im.setDisplayName(name);
		
		ArrayList<String> lores = new ArrayList<String>();
		for(int i = 0;i < lore.length;i++)
			lores.add(lore[i]);
		if(lore.length != 0)
			im.setLore(lores);
		is.setItemMeta(im);
		return is;
	}
	public static ItemStack createItem(Material material, String name, String... lore) {
		ItemStack is = new ItemStack(material);
		ItemMeta im = is.getItemMeta();
		if(name != null)
			im.setDisplayName(name);
		
		ArrayList<String> lores = new ArrayList<String>();
		for(int i = 0;i < lore.length;i++)
			lores.add(lore[i]);
		if(lore.length != 0)
			im.setLore(lores);
		is.setItemMeta(im);
		return is;
	}
	public static ItemStack createItemCMD(Material material, int amount, int customModelData, String name, String... lore) {
		ItemStack is = new ItemStack(material, amount);
		ItemMeta im = is.getItemMeta();
		im.setCustomModelData(customModelData);
		if(name != null)
			im.setDisplayName(name);
		
		ArrayList<String> lores = new ArrayList<String>();
		for(int i = 0;i < lore.length;i++)
			lores.add(lore[i]);
		if(lore.length != 0)
			im.setLore(lores);
		is.setItemMeta(im);
		return is;
	}
	public static ItemStack createItemCMD(Material material, int customModelData, String name, String... lore) {
		ItemStack is = new ItemStack(material);
		ItemMeta im = is.getItemMeta();
		im.setCustomModelData(customModelData);
		if(name != null)
			im.setDisplayName(name);
		
		ArrayList<String> lores = new ArrayList<String>();
		for(int i = 0;i < lore.length;i++)
			lores.add(lore[i]);
		if(lore.length != 0)
			im.setLore(lores);
		is.setItemMeta(im);
		return is;
	}
}
