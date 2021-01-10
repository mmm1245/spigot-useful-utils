package com.github.mmm1245.usefulutils.npc;

import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class SkinTexture{
	public String texture;
	public String signature;
	public SkinTexture(String texture, String signature) {
		this.texture = texture;
		this.signature = signature;
	}
	public SkinTexture(Player pl) {
		GameProfile profile = ((CraftPlayer)pl).getHandle().getProfile();
		Property property = profile.getProperties().get("textures").iterator().next();
		this.texture = property.getValue();
		this.signature = property.getSignature();
	}
	public Property getTextureProperty() {
		return new Property("textures", this.texture, this.signature);
	}
}