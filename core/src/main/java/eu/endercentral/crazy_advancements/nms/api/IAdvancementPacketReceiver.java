package eu.endercentral.crazy_advancements.nms.api;

import org.bukkit.entity.Player;

public interface IAdvancementPacketReceiver {

    void initPlayer(Player player);
    void close(Player player);
}
