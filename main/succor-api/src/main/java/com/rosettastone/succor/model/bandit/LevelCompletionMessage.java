package com.rosettastone.succor.model.bandit;

public class LevelCompletionMessage extends Message {

    private String course;
    private Integer level;

    @JsonField
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @JsonField
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
