package com.rosettastone.succor.model.bandit;

import java.util.Date;

/**
 * The {@code Header} represents a header block from JSON.
 *
 @see Event
 */

public class Header {

    private Integer schemaVersion;
    private Date createdAt;
    private String hostname;
    private MessageType messageType;

    @JsonField("schema_version")
    public Integer getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(Integer schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    @JsonField("created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @JsonField
    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @JsonField("message_type")
    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

}
