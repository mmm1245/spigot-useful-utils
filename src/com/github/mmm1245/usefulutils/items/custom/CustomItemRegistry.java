package com.github.mmm1245.usefulutils.items.custom;

import java.util.HashMap;

public class CustomItemRegistry {
	private static HashMap<String,CustomItem> registered = new HashMap<>();
	public static boolean register(CustomItem customItem, String name) {
		if(registered.containsKey(name))
			return false;
		registered.put(name, customItem);
		return true;
	}
	public static boolean has(String name) {
		return registered.containsKey(name);
	}
	public static CustomItem get(String name) {
		return registered.get(name);
	}
}
