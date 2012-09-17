package com.rosettastone.succor.model.bandit;

import java.util.Date;

/**
 * The {@code MessageLevelUnitStartTime} extends {@link Message},
 * added fields {@link #level}, {@link #unit}, {@link #sessionStartTime}.
 * {@code MessageLevelUnitStartTime} is a base class for other classes.
 * User: mixim
 * Date: 6/7/11
 *
 @see Message
 @see FollowUpWithLearnerMessage
 @see StudioReminderMessage
 @see StudioSatisfactionMessage
 @see StudioSessionCancellationMessage
 @see StudioSessionConfirmationMessage
 */

public class MessageLevelUnitStartTime extends Message{
    private Integer level;
    private Integer unit;
    private Date sessionStartTime;

    @JsonField
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
    @JsonField
    public Integer getUnit() {
        return unit;
    }
    public void setUnit(Integer unit) {
        this.unit = unit;
    }
    @JsonField("session_start_time")
    public Date getSessionStartTime() {
        return sessionStartTime;
    }
    public void setSessionStartTime(Date sessionStartTime) {
        this.sessionStartTime = sessionStartTime;
    }
}
