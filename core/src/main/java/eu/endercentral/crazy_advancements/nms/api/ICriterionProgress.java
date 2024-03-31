package eu.endercentral.crazy_advancements.nms.api;

public interface ICriterionProgress {
    void grant();
    void revoke();
    boolean isDone();
}
