package com.rosettastone.succor.model.bandit;

import java.util.Date;

/**
 * User: mixim
 * Date: 6/10/11
 */

public class ExpiringSubscriptionMessage extends Message{
    private String family;
    private Date expirationTime;
    private Integer days;

    @JsonField
    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    @JsonField
    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }
    @JsonField("expiration_time")
    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }
}
