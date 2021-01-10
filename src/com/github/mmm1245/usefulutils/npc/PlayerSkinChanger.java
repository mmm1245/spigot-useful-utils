package com.github.mmm1245.usefulutils.npc;

import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_16_R2.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_16_R2.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_16_R2.PlayerConnection;

public class PlayerSkinChanger {
	public static void changePlayerSkin(Player pl, SkinTexture texture) {
		GameProfile profile = ((CraftPlayer)pl).getHandle().getProfile();
		PlayerConnection connection = ((CraftPlayer)pl).getHandle().playerConnection;
		
		connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer)pl).getHandle()));
		
		profile.getProperties().removeAll("textures");
		profile.getProperties().put("textures", texture.getTextureProperty());
	
		connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer)pl).getHandle()));
	}
}
