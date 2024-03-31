package eu.endercentral.crazy_advancements.nms.v1_20_R3;

import eu.endercentral.crazy_advancements.nms.api.ICriterionProgress;

class CriterionProgress implements ICriterionProgress {
    private final net.minecraft.advancements.CriterionProgress nms;
    CriterionProgress(net.minecraft.advancements.CriterionProgress nms) {
        this.nms = nms;
    }

    @Override
    public void grant() {
        nms.grant();
    }

    @Override
    public void revoke() {
        nms.revoke();
    }

    @Override
    public boolean isDone() {
        return nms.isDone();
    }
}
