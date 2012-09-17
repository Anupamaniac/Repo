package com.rosettastone.succor.model.bandit;

public class CommunityAbsenceMessage extends Message {

    private String maxLogin;
    private Boolean firstMessageToThisUser;
    private Integer days;
    
    @JsonField("max_login")
    public String getMaxLogin() {
        return maxLogin;
    }
    public void setMaxLogin(String maxLogin) {
        this.maxLogin = maxLogin;
    }
    @JsonField("first_message_to_this_user")
    public Boolean getFirstMessageToThisUser() {
        return firstMessageToThisUser;
    }
    public void setFirstMessageToThisUser(Boolean firstMessageToThisUser) {
        this.firstMessageToThisUser = firstMessageToThisUser;
    }
    @JsonField
    public Integer getDays() {
        return days;
    }
    public void setDays(Integer days) {
        this.days = days;
    }
}
