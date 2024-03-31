package eu.endercentral.crazy_advancements.nms.api;

import java.util.List;

public interface IAdvancementProgress {
    void update(List<List<String>> requirementsList);
    boolean isDone();
    Iterable<String> getRemainingCriteria();
    Iterable<String> getCompletedCriteria();
    ICriterionProgress getCriterion(String name);
    <T> T get();
}
