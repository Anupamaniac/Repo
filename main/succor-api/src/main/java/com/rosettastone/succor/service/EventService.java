package com.rosettastone.succor.service;

import com.rosettastone.succor.model.bandit.Event;

/**
 * The {@code EventService} represents the actions for events.
 */

public interface EventService {

    void sendEmail(Event event);

    void createPhoneTicket(Event event);
    
    void createPhoneTicketDontCheckPhone(Event event);

    /**
     * This is special method for handle case when the customer has no valid phone number and we should send email
     * instead create phone ticket.
     */
    void createPhoneTicketOrSendEmail(Event event);

    void sendPostalMail(Event event);

    void sendSMS(Event event);

    void createInstitutionalTicket(Event event);
    
    void findSuperTicketId(Event event);

}
