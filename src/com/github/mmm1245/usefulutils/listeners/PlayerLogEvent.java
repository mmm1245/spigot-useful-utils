package com.github.mmm1245.usefulutils.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.mmm1245.usefulutils.heads.HeadGenerator;
import com.github.mmm1245.usefulutils.hologram.FakeHologram;
import com.github.mmm1245.usefulutils.npc.IOnNPCClick;
import com.github.mmm1245.usefulutils.npc.NPC;
import com.github.mmm1245.usefulutils.npc.NPCClickPacketListener;
import com.github.mmm1245.usefulutils.npc.PlayerSkinChanger;
import com.github.mmm1245.usefulutils.npc.SkinTexture;

public class PlayerLogEvent implements Listener{
	public static FakeHologram holohram;
	@EventHandler
	public void playerLogInEvent(PlayerJoinEvent event) {
		NPCClickPacketListener.inject(event.getPlayer());
		event.getPlayer().sendMessage("aohj");
		//event.getPlayer().getInventory().addItem(HeadGenerator.createFromUUID(event.getPlayer().getUniqueId()));
		new NPC("ahoj", event.getPlayer().getLocation(), new SkinTexture(event.getPlayer()), new IOnNPCClick() {
			
			@Override
			public void onNPCClick(NPC npc, Player player) {
				player.sendMessage("clicked npc");
				
			}
		}).addNPC(event.getPlayer());
		PlayerSkinChanger.changePlayerSkin(event.getPlayer(), new SkinTexture("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMTAwNjAxOSwKICAicHJvZmlsZUlkIiA6ICJkMGI4MjE1OThmMTE0NzI1ODBmNmNiZTliOGUxYmU3MCIsCiAgInByb2ZpbGVOYW1lIiA6ICJqYmFydHl5IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzdkYTZkZGNmZTRhMjUxZDU1ZmY2MDAxYjQ5ZGU3NTE0ZThlNTBjZWE2Y2IyZWRmMWVkODA0ZmYxOGZlZWUzODQiCiAgICB9CiAgfQp9", 
																			  "Z9kiC8Li2dWe6ZgGtkK18IxjPbo7H/VHNwT5jYftzAL3qRgTCiFlpSzjkZ+mX8Bxu3ATpRcidpEep+Ipr/9lI55un1TLMPOKUbJVljKPWrtYbdvs7JcmzPIbuT0+X7UiNgnLzCMToc1CIGLPM1/JH3XfXR+0aViEMSEv3YPFMixkmGcwc3BS0/iuYXhNaK47ZNRTw216Dkc8mVRU6bZBHQpjB97Le23MWmjUkJPSM2BOg4AegbT2uSTUlvL1myafi00fjrW+fX7bsv6iwJY/M3pxO3m7e2mP7vAhzGojOeJG+MYKrjcf09sX1Pj1IdPkxKHOESRBP7jpA/1b8XLzFAetFX87ZOhvw7GiwawhuJTURPHMDB4sZgBlHDtUI2JlN4K9ySOazgtQueCzlauzbNAJeEZoJeApZU4+U/YFEiPmvgrMA3QnMIL2zexuj7sxW7eni6SGVgDbpL3gepgq5+zMyLm08xu+QN64hO5LDqJvAgDT1NATNcsFbz9Z32zBqmCA24GmWNfsappROTQw0r7J6Q5Tfz4ybCQfM9iyrBcpUYb1sSBPK0gB2NYWyUApHXFegWsAHt3Ais2xtKPxMC9sadbYQ7PWuqbqdi5gbvvsMq+FT8+OKlWl3Lclu2qpnM+3wno4QrpiI4OCutmvnHw+8Ph2/xMZiaAoEc+Ja2Y="));
		holohram = new FakeHologram(new Location(Bukkit.getWorlds().get(0), 75, 90, 60), "hologram");
		holohram.showPlayer(event.getPlayer());
	}
	@EventHandler
	public void playerLogOutEvent(PlayerQuitEvent event) {
		NPCClickPacketListener.uninject(event.getPlayer());
	}
}
