package eu.endercentral.crazy_advancements.packet;

import eu.endercentral.crazy_advancements.advancement.ToastNotification;
import eu.endercentral.crazy_advancements.nms.NMS;
import eu.endercentral.crazy_advancements.nms.api.WAdvancementsPacket;
import org.bukkit.entity.Player;

/**
 * Represents an Advancements Packet for Toast Notifications
 * 
 * @author Axel
 *
 */
public class ToastPacket {
	
	private final Player player;
	private final boolean add;
	private final ToastNotification notification;
	
	/**
	 * Constructor for creating Toast Packets
	 * 
	 * @param player The target Player
	 * @param add Whether to add or remove the Advancement
	 * @param notification The Notification
	 */
	public ToastPacket(Player player, boolean add, ToastNotification notification) {
		this.player = player;
		this.add = add;
		this.notification = notification;
	}
	
	/**
	 * Gets the target Player
	 * 
	 * @return The target Player
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Gets whether the Advancement is added or removed
	 * 
	 * @return Whether the Advancement is added or removed
	 */
	public boolean isAdd() {
		return add;
	}
	
	/**
	 * Gets the Notification
	 * 
	 * @return The Notification
	 */
	public ToastNotification getNotification() {
		return notification;
	}
	
	/**
	 * Builds a packet that can be sent to a Player
	 * 
	 * @return The Packet
	 */
	public WAdvancementsPacket build() {
		return NMS.get().build(this);
	}
	
	/**
	 * Sends the Packet to the target Player
	 * 
	 */
	public void send() {
		NMS.get().send(player, build());
	}
}