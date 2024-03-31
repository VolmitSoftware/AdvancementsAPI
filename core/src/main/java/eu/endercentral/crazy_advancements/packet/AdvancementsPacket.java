package eu.endercentral.crazy_advancements.packet;

import eu.endercentral.crazy_advancements.NameKey;
import eu.endercentral.crazy_advancements.advancement.Advancement;
import eu.endercentral.crazy_advancements.nms.NMS;
import eu.endercentral.crazy_advancements.nms.api.WAdvancementsPacket;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Advancements Packet
 * 
 * @author Axel
 *
 */
public class AdvancementsPacket {
	
	private final Player player;
	private final boolean reset;
	private final List<Advancement> advancements;
	private final List<NameKey> removedAdvancements;
	
	/**
	 * Constructor for creating Advancement Packets
	 * 
	 * @param player The target Player
	 * @param reset Whether the Client will clear the Advancement Screen before adding the Advancements
	 * @param advancements A list of advancements that should be added to the Advancement Screen
	 * @param removedAdvancements A list of NameKeys which should be removed from the Advancement Screen
	 */
	public AdvancementsPacket(Player player, boolean reset, List<Advancement> advancements, List<NameKey> removedAdvancements) {
		this.player = player;
		this.reset = reset;
		this.advancements = advancements == null ? new ArrayList<>() : new ArrayList<>(advancements);
		this.removedAdvancements = removedAdvancements == null ? new ArrayList<>() : new ArrayList<>(removedAdvancements);
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
	 * Gets whether the Client will clear the Advancement Screen before adding the Advancements
	 * 
	 * @return Whether the Screen should reset
	 */
	public boolean isReset() {
		return reset;
	}
	
	/**
	 * Gets a copy of the list of the added Advancements
	 * 
	 * @return The list containing the added Advancements
	 */
	public List<Advancement> getAdvancements() {
		return new ArrayList<>(advancements);
	}
	
	/**
	 * Gets a copy of the list of the removed Advancement's NameKeys
	 * 
	 * @return The list containing the removed Advancement's NameKeys
	 */
	public List<NameKey> getRemovedAdvancements() {
		return new ArrayList<>(removedAdvancements);
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