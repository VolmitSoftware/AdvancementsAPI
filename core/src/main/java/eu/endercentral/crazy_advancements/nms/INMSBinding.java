package eu.endercentral.crazy_advancements.nms;

import eu.endercentral.crazy_advancements.NameKey;
import eu.endercentral.crazy_advancements.nms.api.IAdvancementPacketReceiver;
import eu.endercentral.crazy_advancements.nms.api.IAdvancementProgress;
import eu.endercentral.crazy_advancements.nms.api.WAdvancementRequirements;
import eu.endercentral.crazy_advancements.nms.api.WAdvancementsPacket;
import eu.endercentral.crazy_advancements.nms.api.WComponent;
import eu.endercentral.crazy_advancements.nms.api.WCriterion;
import eu.endercentral.crazy_advancements.nms.api.WPacket;
import eu.endercentral.crazy_advancements.packet.AdvancementsPacket;
import eu.endercentral.crazy_advancements.packet.ToastPacket;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

import java.util.List;

public interface INMSBinding {

    void setActiveTab(Player player, NameKey rootAdvancement);
    WCriterion newCriterion();
    IAdvancementPacketReceiver newPacketReceiver();
    WComponent toNmsComponent(BaseComponent json);
    WAdvancementRequirements newRequirements(List<List<String>> requirements);
    IAdvancementProgress newProgress();
    WAdvancementsPacket build(AdvancementsPacket packet);
    WAdvancementsPacket build(ToastPacket packet);
    void send(Player player, WPacket packet);
}
