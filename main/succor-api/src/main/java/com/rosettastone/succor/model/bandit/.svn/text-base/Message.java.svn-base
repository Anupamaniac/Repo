package com.rosettastone.succor.model.bandit;

/**
 * The {@code Message} is a base class for message-classes.
 * Contains common fields {@link #guid}, {@link #isoLanguageCode}, {@link #rsLanguageCode}.
 *
 @see ClaimedExtensionsMessage
 @see CommunityAbsenceMessage
 @see DiscoveryCallMessage
 @see ExpiringSubscriptionMessage
 @see FirstCompletedStudioSessionMessage
 @see LevelCompletionMessage
 @see LevelsCompletedMessage
 @see MessageLevelUnitStartTime
 @see UpdateMessage
 */

public class Message {

    private String guid;
    private String isoLanguageCode;
    private String rsLanguageCode;
    
    @JsonField
    public String getGuid() {
        return guid;
    }
    public void setGuid(String guid) {
        this.guid = guid;
    }

    @JsonField("iso_language_code")
    public String getIsoLanguageCode() {
        return isoLanguageCode;
    }
    public void setIsoLanguageCode(String isoLanguageCode) {
        this.isoLanguageCode = isoLanguageCode;
    }

    @JsonField("rs_language_code")
    public String getRsLanguageCode() {
        return rsLanguageCode;
    }
    public void setRsLanguageCode(String rsLanguageCode) {
        this.rsLanguageCode = rsLanguageCode;
    }
}
