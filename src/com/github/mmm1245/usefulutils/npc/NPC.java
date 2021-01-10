package com.github.mmm1245.usefulutils.npc;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.server.v1_16_R2.EntityPlayer;
import net.minecraft.server.v1_16_R2.MinecraftServer;
import net.minecraft.server.v1_16_R2.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_16_R2.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_16_R2.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_16_R2.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_16_R2.PlayerConnection;
import net.minecraft.server.v1_16_R2.PlayerInteractManager;
import net.minecraft.server.v1_16_R2.WorldServer;

public class NPC {
	static ArrayList<NPC> clickable = new ArrayList<>();
	
	private GameProfile gameProfile;
	private EntityPlayer npc;
	private IOnNPCClick clickEvent;
	public NPC(String name, Location location, SkinTexture texture, IOnNPCClick onClick) {
		MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
		gameProfile = new GameProfile(UUID.randomUUID(), name);
		if(texture != null)
			gameProfile.getProperties().put("textures", texture.getTextureProperty());
		npc = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));
		npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
		if(onClick != null) {
			clickEvent = onClick;
			clickable.add(this);
		}
	}
	public void addNPC(Player pl) {
		PlayerConnection connection = ((CraftPlayer) pl).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc));
		connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
		connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw*256/360)));
	}
	public void addNPC() {
		for(Player pl : Bukkit.getOnlinePlayers()) {
			PlayerConnection connection = ((CraftPlayer) pl).getHandle().playerConnection;
			connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc));
			connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
			connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw*256/360)));
		}
	}
	public void onClick(Player pl) {
		if(clickEvent == null) {
			Bukkit.getLogger().warning("event called for unclickable npc, this shouldnt happen!");
			return;
		}
		clickEvent.onNPCClick(this, pl);
	}
	public int getID() {
		return npc.getId();
	}
	public void cleanup() {
		clickable.remove(this);
	}
}
