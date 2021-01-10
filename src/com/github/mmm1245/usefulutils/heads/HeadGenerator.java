package com.github.mmm1245.usefulutils.heads;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.UUID;

import javax.xml.crypto.dsig.dom.DOMValidateContext;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class HeadGenerator {
	private static Method headSetProfile = initSetProfile();
	
	public static ItemStack createFromName(String name) {
		if(name == null) {
			throw new IllegalArgumentException("Name can't be null");
		}
		ItemStack head = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta headMeta = (SkullMeta) head.getItemMeta();
		headMeta.setOwner(name);
		head.setItemMeta(headMeta);
		return head;
	}
	public static ItemStack createFromUUID(UUID uuid) {
		if(uuid == null) {
			throw new IllegalArgumentException("UUID can't be null");
		}
		ItemStack head = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta headMeta = (SkullMeta) head.getItemMeta();
		headMeta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
		head.setItemMeta(headMeta);
		return head;
	}
	public static ItemStack createFromData(String data) {
		if(data == null) {
			throw new IllegalArgumentException("Data can't be null");
		}
		ItemStack head = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta headMeta = (SkullMeta) head.getItemMeta();
		setSkullData(headMeta, data);
		head.setItemMeta(headMeta);
		return head;
	}
	
	public static ItemStack createFromUrl(String url) {
		if(url == null) {
			throw new IllegalArgumentException("Url can't be null");
		}
		URI parsedUrl;
		try {
			parsedUrl = new URI(url);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Invalid Url");
		}
		String notEncoded = "{\"textures\":{\"SKIN\":{\"url\":\"" + parsedUrl.toString() + "\"}}}";
		String data = Base64.getEncoder().encodeToString(notEncoded.getBytes());
		
		
		ItemStack head = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta headMeta = (SkullMeta) head.getItemMeta();
		setSkullData(headMeta, data);
		head.setItemMeta(headMeta);
		return head;
	}
	
	
	private static GameProfile createProfileWithTexture(String texturedata) {
		UUID uuid = UUID.randomUUID();
		GameProfile profile = new GameProfile(uuid, "yJQdYE2DzwQ9sDxG");
		profile.getProperties().put("textures", new Property("textures", texturedata));
		return profile;
	}
	
	private static Method initSetProfile() {
		try {
			Method returnMethod;
			returnMethod = SkullMeta.class.getDeclaredMethod("setProfile", GameProfile.class);
			returnMethod.setAccessible(true);
			return returnMethod;
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}
		
	}
	
	private static void setSkullData(SkullMeta skull, String data) {
		if(headSetProfile == null) {
			Bukkit.getLogger().warning("Cannot set head meta in this version, higher version of server is required!");
			return;
		}
		try {
			headSetProfile.invoke(skull, createProfileWithTexture(data));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
