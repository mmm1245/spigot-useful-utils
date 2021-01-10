package com.github.mmm1245.usefulutils.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.server.v1_16_R2.MojangsonParser;
import net.minecraft.server.v1_16_R2.NBTCompressedStreamTools;
import net.minecraft.server.v1_16_R2.NBTTagCompound;
import net.minecraft.server.v1_16_R2.NBTTagList;

public class ItemSerializer {
	public static String serialize(ItemStack itemStack) throws IOException {
		ByteArrayOutputStream barout = new ByteArrayOutputStream();
		DataOutputStream stream = new DataOutputStream(barout);
		String name = itemStack.getType().name();
		int amount = itemStack.getAmount();
		short durability = itemStack.getDurability();
		String nbt = "";
		net.minecraft.server.v1_16_R2.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
		if(nmsItemStack.hasTag()) {
			nbt = nmsItemStack.getTag().toString();
		}
		stream.writeUTF(name);
		stream.writeInt(amount);
		stream.writeShort(durability);
		stream.writeUTF(nbt);
		stream.close();
		return Base64.getEncoder().encodeToString(barout.toByteArray());
	}
	@SuppressWarnings("deprecation")
	public static ItemStack deserialize(String itemStack) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bins = new ByteArrayInputStream(Base64.getDecoder().decode(itemStack));
		DataInputStream stream = new DataInputStream(bins);
		ItemStack is = new ItemStack(Material.valueOf(stream.readUTF()), stream.readInt());
		is.setDurability(stream.readShort());
		net.minecraft.server.v1_16_R2.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(is);
		try {
			String nbt = stream.readUTF();
			if(nbt == "")
				nbt = "{}";
			nmsItemStack.setTag(MojangsonParser.parse(nbt));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CommandSyntaxException e) {
			
		}
		
		return CraftItemStack.asBukkitCopy(nmsItemStack);
	}
}