package com.rosettastone.succor.model.bandit;

import java.util.Date;

public class UpdateMessage extends Message {
    private Date maxUpdatedAt;

    @JsonField("max_updated_at")
    public Date getMaxUpdatedAt() {
        return maxUpdatedAt;
    }

    public void setMaxUpdatedAt(Date maxUpdatedAt) {
        this.maxUpdatedAt = maxUpdatedAt;
    }
}
