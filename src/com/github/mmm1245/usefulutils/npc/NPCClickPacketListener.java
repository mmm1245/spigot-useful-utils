package com.github.mmm1245.usefulutils.npc;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.mmm1245.usefulutils.Main;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_16_R2.EnumHand;
import net.minecraft.server.v1_16_R2.PacketPlayInUseEntity;
import net.minecraft.server.v1_16_R2.PacketPlayInUseEntity.EnumEntityUseAction;

public class NPCClickPacketListener {
	private static Map<UUID, Channel> channels = new HashMap<>();
	
	public static void inject(Player pl) {
		CraftPlayer craftPlayer = (CraftPlayer) pl;
		Channel channel = craftPlayer.getHandle().playerConnection.networkManager.channel;
		channels.put(pl.getUniqueId(), channel);
		if(channel.pipeline().get("PacketInjectorNPC") != null) 
			return;
		channel.pipeline().addAfter("decoder", "PacketInjectorNPC", new MessageToMessageDecoder<PacketPlayInUseEntity>() {

			@SuppressWarnings("deprecation")
			@Override
			protected void decode(ChannelHandlerContext channel, PacketPlayInUseEntity packet, List<Object> arg) throws Exception {
				arg.add(packet);
				if(packet.b() != EnumEntityUseAction.INTERACT) 
					return;
				if(packet.c() == EnumHand.OFF_HAND)
					return;
				int id = (int) getValue(packet, "a");
				for(NPC npc : NPC.clickable) {
					if(npc.getID() == id) {
						(new BukkitRunnable() {
							@Override
							public void run() {
								npc.onClick(pl);
								
							}
						}).runTask(JavaPlugin.getPlugin(Main.class));
					}
				}
			}
		});
	}
	public static void uninject(Player pl) {
		Channel channel = channels.get(pl.getUniqueId());
		if(channel == null)
			return;
		if(channel.pipeline().get("PacketInjectorNPC") != null) 
			channel.pipeline().remove("PacketInjectorNPC");
	}
	private static Object getValue(Object instance, String name) {
		try {
			Field field;
			field = instance.getClass().getDeclaredField(name);
			field.setAccessible(true);
			return field.get(instance);
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
