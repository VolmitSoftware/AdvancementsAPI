package eu.endercentral.crazy_advancements;

import eu.endercentral.crazy_advancements.nms.NMS;
import eu.endercentral.crazy_advancements.nms.api.WComponent;
import net.md_5.bungee.api.chat.BaseComponent;

/**
 * Represents a Message in JSON Format
 * 
 * @author Axel
 *
 */
public class JSONMessage {
	
	private final BaseComponent json;
	
	/**
	 * Constructor for creating a JSON Message
	 * 
	 * @param json A JSON representation of an ingame Message <a href="https://www.spigotmc.org/wiki/the-chat-component-api/">Read More</a>
	 */
	public JSONMessage(BaseComponent json) {
		this.json = json;
	}
	
	/**
	 * Gets the Message as a BaseComponent
	 * 
	 * @return the BaseComponent of an ingame Message
	 */
	public BaseComponent getJson() {
		return json;
	}
	
	/**
	 * Gets an NMS representation of an ingame Message
	 * 
	 * @return An {@link WComponent} representation of an ingame Message
	 */
	public WComponent getBaseComponent() {
		return NMS.get().toNmsComponent(json);
	}
	
	@Override
	public String toString() {
		return json.toPlainText();
	}
	
}