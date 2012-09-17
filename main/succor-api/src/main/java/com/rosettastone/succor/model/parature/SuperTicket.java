package com.rosettastone.succor.model.parature;

/**
 * The {@code SuperTicket} extends {@code Ticket}: added fields
 * {@code language}, {@code summary}, {@code details}, {@code originType}.
 *
 @see Ticket
 */

public class SuperTicket extends Ticket {

    private TicketLanguageType language;

    private String summary;

    private String details;

    private final TicketOriginType originType;

    public SuperTicket() {
        super(TicketType.SUPER);
        originType = TicketOriginType.SUPER;
    }

    public TicketLanguageType getLanguage() {
        return language;
    }

    public void setLanguage(TicketLanguageType language) {
        this.language = language;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public TicketOriginType getOriginType() {
        return originType;
    }
}
