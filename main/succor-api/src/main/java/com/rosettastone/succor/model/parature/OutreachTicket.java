package com.rosettastone.succor.model.parature;

/**
 * The {@code OutreachTicket} is a ticket for Phone and Postal outreach types.
 * 
 */

public class OutreachTicket extends Ticket {

    private ContactReasonType contactReason;

    private String internalDetails;

    private String summary;

    private final TicketOriginType originType;

    public OutreachTicket(TicketType type) {
        super(type);

        if (type == null) {
            originType = null;
            return;
        }

        if (type.equals(TicketType.PHONE)) {
            originType = TicketOriginType.PHONE;
        } else {
            // institutional
            originType = TicketOriginType.SUPER;
        }
    }

    public ContactReasonType getContactReason() {
        return contactReason;
    }

    public void setContactReason(ContactReasonType contactReason) {
        this.contactReason = contactReason;
    }

    public String getInternalDetails() {
        return internalDetails;
    }

    public void setInternalDetails(String internalDetails) {
        this.internalDetails = internalDetails;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public TicketOriginType getOriginType() {
        return originType;
    }
}
