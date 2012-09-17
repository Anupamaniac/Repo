package com.rosettastone.succor.model.bandit;

/**
 * User: mixim
 * Date: 6/7/11
 */

public class StudioSatisfactionMessage extends MessageLevelUnitStartTime {
    private Boolean satisfied;

    @JsonField
    public Boolean getSatisfied() {
        return satisfied;
    }

    public void setSatisfied(Boolean satisfied) {
        this.satisfied = satisfied;
    }
}
