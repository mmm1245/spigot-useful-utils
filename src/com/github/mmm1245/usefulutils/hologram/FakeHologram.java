package com.github.mmm1245.usefulutils.hologram;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_16_R2.ChatComponentText;
import net.minecraft.server.v1_16_R2.EntityArmorStand;
import net.minecraft.server.v1_16_R2.EntityChicken;
import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_16_R2.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_16_R2.WorldServer;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;

public class FakeHologram {
	public EntityArmorStand stand;
	public FakeHologram(Location loc, String name) {
		WorldServer s = ((CraftWorld)loc.getWorld()).getHandle();
		stand = new EntityArmorStand(EntityTypes.ARMOR_STAND, s);
       
		stand.setLocation(loc.getX(), loc.getY(), loc.getZ(), 0, 0);
		stand.setCustomName(new ChatComponentText(name));
		stand.setCustomNameVisible(true);
		stand.setNoGravity(true);
		stand.setInvisible(true);
		stand.setBasePlate(false);
		stand.setSmall(true);
	}
	public void setText(String text) {
		stand.setCustomName(new ChatComponentText(text));
	}
	public void update(Player pl) {
		PacketPlayOutEntityMetadata metaPacket = new PacketPlayOutEntityMetadata(stand.getId(), stand.getDataWatcher(), false);
        ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(metaPacket);
	}
	public void updateAll() {
		for(Player pl : Bukkit.getOnlinePlayers()) {
			update(pl);
		}
	}
	public void showPlayer(Player pl) {
		PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);
        ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(packet);
        PacketPlayOutEntityMetadata metaPacket = new PacketPlayOutEntityMetadata(stand.getId(), stand.getDataWatcher(), false);
        ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(metaPacket);
	}
	public void showAll() {
		for(Player pl : Bukkit.getOnlinePlayers()) {
			showPlayer(pl);
		}
	}
}
