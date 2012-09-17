package com.rosettastone.succor.model.parature;

import com.rosettastone.succor.model.bandit.Customer;

/**
 * The {@code Ticket} represents an entity for interaction with paratute.
 */

public abstract class Ticket {

    private Customer customer;
    private String id;
    private TicketType ticketType;
    private final boolean emailNotification;
    private final boolean hideFromCustomer;

    public Ticket(TicketType ticketType) {
        this.ticketType = ticketType;

        if (!ticketType.equals(TicketType.PHONE)) {
            this.emailNotification = false;
            this.hideFromCustomer = true;
        } else {
            this.emailNotification = true;
            this.hideFromCustomer = false;
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public boolean isEmailNotification() {
        return emailNotification;
    }

    public boolean isHideFromCustomer() {
        return hideFromCustomer;
    }

}
