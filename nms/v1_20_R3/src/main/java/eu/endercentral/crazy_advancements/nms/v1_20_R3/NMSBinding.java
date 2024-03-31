package eu.endercentral.crazy_advancements.nms.v1_20_R3;

import eu.endercentral.crazy_advancements.JSONMessage;
import eu.endercentral.crazy_advancements.NameKey;
import eu.endercentral.crazy_advancements.advancement.Advancement;
import eu.endercentral.crazy_advancements.advancement.AdvancementDisplay;
import eu.endercentral.crazy_advancements.advancement.AdvancementFlag;
import eu.endercentral.crazy_advancements.advancement.ToastNotification;
import eu.endercentral.crazy_advancements.nms.INMSBinding;
import eu.endercentral.crazy_advancements.nms.api.IAdvancementPacketReceiver;
import eu.endercentral.crazy_advancements.nms.api.IAdvancementProgress;
import eu.endercentral.crazy_advancements.nms.api.WAdvancementRequirements;
import eu.endercentral.crazy_advancements.nms.api.WAdvancementsPacket;
import eu.endercentral.crazy_advancements.nms.api.WComponent;
import eu.endercentral.crazy_advancements.nms.api.WCriterion;
import eu.endercentral.crazy_advancements.nms.api.WPacket;
import eu.endercentral.crazy_advancements.packet.AdvancementsPacket;
import eu.endercentral.crazy_advancements.packet.PacketConverter;
import eu.endercentral.crazy_advancements.packet.ToastPacket;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class NMSBinding implements INMSBinding {
    private static final AdvancementRewards advancementRewards = new AdvancementRewards(0, new ArrayList<>(), new ArrayList<>(), Optional.empty());

    @Override
    public void setActiveTab(Player player, NameKey rootAdvancement) {
        ClientboundSelectAdvancementsTabPacket packet = new ClientboundSelectAdvancementsTabPacket(toNMS(rootAdvancement));
        ((CraftPlayer)player).getHandle().connection.send(packet);
    }

    @Override
    public WCriterion newCriterion() {
        return new WCriterion(new Criterion<>(new ImpossibleTrigger(), new ImpossibleTrigger.TriggerInstance()));
    }

    @Override
    public IAdvancementPacketReceiver newPacketReceiver() {
        return new AdvancementPacketReceiver();
    }

    @Override
    public WComponent toNmsComponent(BaseComponent json) {
        return new WComponent(Component.Serializer.fromJson(ComponentSerializer.toString(json)));
    }

    @Override
    public WAdvancementRequirements newRequirements(List<List<String>> requirements) {
        return new WAdvancementRequirements(new AdvancementRequirements(requirements));
    }

    @Override
    public IAdvancementProgress newProgress() {
        return new AdvancementProgress();
    }

    @Override
    public WAdvancementsPacket build(AdvancementsPacket builder) {
        //Create Lists
        List<net.minecraft.advancements.AdvancementHolder> advancements = new ArrayList<>();
        Set<ResourceLocation> removedAdvancements = new HashSet<>();
        Map<ResourceLocation, net.minecraft.advancements.AdvancementProgress> progress = new HashMap<>();

        //Populate Lists
        for(Advancement advancement : builder.getAdvancements()) {
            net.minecraft.advancements.Advancement nmsAdvancement = toNmsAdvancement(advancement);
            advancements.add(new AdvancementHolder(toNMS(advancement.getName()), nmsAdvancement));
            progress.put(toNMS(advancement.getName()), advancement.getProgress(builder.getPlayer()).getNmsProgress().get());
        }
        for(NameKey removed : builder.getRemovedAdvancements()) {
            removedAdvancements.add(toNMS(removed));
        }

        //Create Packet
        ClientboundUpdateAdvancementsPacket packet = new ClientboundUpdateAdvancementsPacket(builder.isReset(), advancements, removedAdvancements, progress);
        return new WAdvancementsPacket(packet);
    }

    @Override
    public WAdvancementsPacket build(ToastPacket builder) {

        //Create Lists
        List<AdvancementHolder> advancements = new ArrayList<>();
        Set<ResourceLocation> removedAdvancements = new HashSet<>();
        Map<ResourceLocation, net.minecraft.advancements.AdvancementProgress> progress = new HashMap<>();

        //Populate Lists
        if(builder.isAdd()) {
            advancements.add(new AdvancementHolder(toNMS(ToastNotification.NOTIFICATION_NAME), toNmsToastAdvancement(builder.getNotification())));
            progress.put(toNMS(ToastNotification.NOTIFICATION_NAME), ToastNotification.NOTIFICATION_PROGRESS.getNmsProgress().get());
        } else {
            removedAdvancements.add(toNMS(ToastNotification.NOTIFICATION_NAME));
        }

        //Create Packet
        ClientboundUpdateAdvancementsPacket packet = new ClientboundUpdateAdvancementsPacket(false, advancements, removedAdvancements, progress);
        return new WAdvancementsPacket(packet);
    }

    @Override
    public void send(Player player, WPacket packet) {
        ((CraftPlayer)player).getHandle().connection.send(packet.get());
    }

    private Map<String, Criterion<?>> unwrap(Map<String, WCriterion> criteria) {
        Map<String, Criterion<?>> unwrapped = new HashMap<>();
        criteria.forEach((name, criterion) -> unwrapped.put(name, criterion.get()));
        return unwrapped;
    }

    private ResourceLocation toNMS(NameKey key) {
        if (key == null) return null;
        return new ResourceLocation(key.getNamespace(), key.getKey());
    }

    private AdvancementType toNMS(AdvancementDisplay.AdvancementFrame frame) {
        return switch (frame) {
            case GOAL -> AdvancementType.GOAL;
            case TASK -> AdvancementType.TASK;
            case CHALLENGE -> AdvancementType.CHALLENGE;
        };
    }

    private net.minecraft.advancements.Advancement toNmsAdvancement(Advancement advancement) {
        AdvancementDisplay display = advancement.getDisplay();

        ItemStack icon = CraftItemStack.asNMSCopy(display.getIcon());

        boolean hasBackgroundTexture = display.getBackgroundTexture() != null;
        Optional<ResourceLocation> backgroundTexture = hasBackgroundTexture ? Optional.of(new ResourceLocation(display.getBackgroundTexture())) : Optional.empty();

        float x = PacketConverter.generateX(advancement.getTab(), display.generateX());
        float y = PacketConverter.generateY(advancement.getTab(), display.generateY());

        net.minecraft.advancements.DisplayInfo advDisplay = new net.minecraft.advancements.DisplayInfo(icon, display.getTitle().getBaseComponent().get(), display.getDescription().getBaseComponent().get(), backgroundTexture, toNMS(display.getFrame()), false, false, advancement.hasFlag(
                AdvancementFlag.SEND_WITH_HIDDEN_BOOLEAN));
        advDisplay.setLocation(x, y);

        Optional<ResourceLocation> parent = advancement.getParent() == null ? Optional.empty() : Optional.of(toNMS(advancement.getParent().getName()));
        net.minecraft.advancements.Advancement adv = new net.minecraft.advancements.Advancement(parent, Optional.of(advDisplay), advancementRewards,
                unwrap(advancement.getCriteria().getCriteria()),
                advancement.getCriteria().getAdvancementRequirements().get(), false);

        return adv;
    }

    private net.minecraft.advancements.Advancement toNmsToastAdvancement(ToastNotification notification) {
        ItemStack icon = CraftItemStack.asNMSCopy(notification.getIcon());
        net.minecraft.advancements.DisplayInfo advDisplay = new net.minecraft.advancements.DisplayInfo(icon, notification.getMessage().getBaseComponent().get(), new JSONMessage(new TextComponent("Toast Notification")).getBaseComponent().get(), Optional.empty(), toNMS(notification.getFrame()), true, false, true);
        net.minecraft.advancements.Advancement adv = new net.minecraft.advancements.Advancement(Optional.empty(), Optional.of(advDisplay), advancementRewards, unwrap(ToastNotification.NOTIFICATION_CRITERIA.getCriteria()), ToastNotification.NOTIFICATION_CRITERIA.getAdvancementRequirements().get(), false);
        return adv;
    }
}
