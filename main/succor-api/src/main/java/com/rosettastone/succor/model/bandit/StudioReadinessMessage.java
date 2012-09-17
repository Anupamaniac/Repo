package com.rosettastone.succor.model.bandit;

public class StudioReadinessMessage extends UpdateMessage {

    private Integer level;
    private String course;
    private Integer unit;
    private Boolean firstMessageToThisUser;
    private Boolean attendedStudioSession;

    @JsonField
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @JsonField
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @JsonField
    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    @JsonField("first_message_to_this_user")
    public Boolean getFirstMessageToThisUser() {
        return firstMessageToThisUser;
    }

    public void setFirstMessageToThisUser(Boolean firstMessageToThisUser) {
        this.firstMessageToThisUser = firstMessageToThisUser;
    }

    @JsonField("attended_studio_session")
    public Boolean getAttendedStudioSession() {
        return attendedStudioSession;
    }

    public void setAttendedStudioSession(Boolean attendedStudioSession) {
        this.attendedStudioSession = attendedStudioSession;
    }

}
