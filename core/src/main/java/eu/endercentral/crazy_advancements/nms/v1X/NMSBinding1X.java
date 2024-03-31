package eu.endercentral.crazy_advancements.nms.v1X;

import eu.endercentral.crazy_advancements.NameKey;
import eu.endercentral.crazy_advancements.nms.INMSBinding;
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

public class NMSBinding1X implements INMSBinding {
    @Override
    public void setActiveTab(Player player, NameKey rootAdvancement) {

    }

    @Override
    public WCriterion newCriterion() {
        return null;
    }

    @Override
    public IAdvancementPacketReceiver newPacketReceiver() {
        return null;
    }

    @Override
    public WComponent toNmsComponent(BaseComponent json) {
        return null;
    }

    @Override
    public WAdvancementRequirements newRequirements(List<List<String>> requirements) {
        return null;
    }

    @Override
    public IAdvancementProgress newProgress() {
        return null;
    }

    @Override
    public WAdvancementsPacket build(AdvancementsPacket packet) {
        return null;
    }

    @Override
    public WAdvancementsPacket build(ToastPacket packet) {
        return null;
    }

    @Override
    public void send(Player player, WPacket packet) {

    }
}
