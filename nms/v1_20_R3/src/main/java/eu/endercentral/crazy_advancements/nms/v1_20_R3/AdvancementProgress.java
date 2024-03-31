package eu.endercentral.crazy_advancements.nms.v1_20_R3;

import eu.endercentral.crazy_advancements.nms.api.IAdvancementProgress;
import eu.endercentral.crazy_advancements.nms.api.ICriterionProgress;
import net.minecraft.advancements.AdvancementRequirements;

import java.util.List;

class AdvancementProgress implements IAdvancementProgress {
    private final net.minecraft.advancements.AdvancementProgress nmsProgress = new net.minecraft.advancements.AdvancementProgress();

    @Override
    public void update(List<List<String>> requirementsList) {
        nmsProgress.update(new AdvancementRequirements(requirementsList));
    }

    @Override
    public boolean isDone() {
        return nmsProgress.isDone();
    }

    @Override
    public Iterable<String> getRemainingCriteria() {
        return nmsProgress.getRemainingCriteria();
    }

    @Override
    public Iterable<String> getCompletedCriteria() {
        return nmsProgress.getCompletedCriteria();
    }

    @Override
    public ICriterionProgress getCriterion(String name) {
        return new CriterionProgress(nmsProgress.getCriterion(name));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) nmsProgress;
    }
}
