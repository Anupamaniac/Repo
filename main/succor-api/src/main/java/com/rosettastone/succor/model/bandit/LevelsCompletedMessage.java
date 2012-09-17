package com.rosettastone.succor.model.bandit;

public class LevelsCompletedMessage extends Message {
    
    private String levels;

    @JsonField
    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

}
