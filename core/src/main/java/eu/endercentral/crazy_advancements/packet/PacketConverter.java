package eu.endercentral.crazy_advancements.packet;

import eu.endercentral.crazy_advancements.NameKey;

import java.util.HashMap;

public class PacketConverter {
	
	private static final HashMap<NameKey, Float> smallestX = new HashMap<>();
	private static final HashMap<NameKey, Float> smallestY = new HashMap<>();
	
	public static void setSmallestX(NameKey tab, float smallestX) {
		PacketConverter.smallestX.put(tab, smallestX);
	}
	
	public static float getSmallestX(NameKey key) {
		return smallestX.containsKey(key) ? smallestX.get(key) : 0;
	}
	
	public static void setSmallestY(NameKey tab, float smallestY) {
		PacketConverter.smallestY.put(tab, smallestY);
	}
	
	public static float getSmallestY(NameKey key) {
		return smallestY.containsKey(key) ? smallestY.get(key) : 0;
	}
	
	public static float generateX(NameKey tab, float displayX) {
		return displayX - getSmallestX(tab);
	}
	
	public static float generateY(NameKey tab, float displayY) {
		return displayY - getSmallestY(tab);
	}
}