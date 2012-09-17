package com.rosettastone.succor.web.dto;

/**
 * The {@code TicketDTO} represents Ticket object for flex.
 */
public class TicketDTO {

    private String type;
    private String summary;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
