package com.rosettastone.succor.parature;

import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.model.parature.ContactReasonType;
import com.rosettastone.succor.model.parature.OutreachTicket;
import com.rosettastone.succor.model.parature.SuperTicket;
import com.rosettastone.succor.model.parature.Ticket;
import com.rosettastone.succor.model.parature.TicketLanguageType;
import com.rosettastone.succor.model.parature.TicketType;

public final class TestTicketFactory {

    private TestTicketFactory() { };

    public static Ticket createTestTicket(Customer customer, TicketType type) {
        if (type.equals(TicketType.SUPER)) {
            SuperTicket superTicket = new SuperTicket();
    //      superTicket.setId(222);
            superTicket.setLanguage(TicketLanguageType.FRENCH);
            //superTicket.setCustomer(customer);
            superTicket.setDetails("My details");
            superTicket.setSummary("My summary");
            return superTicket;
        } else {
            OutreachTicket outreachTicket = new OutreachTicket(type);
    //      outreachTicket.setId(111);
           // outreachTicket.setCustomer(customer);
            outreachTicket.setContactReason(ContactReasonType.FIRST_STUDIO_COMPLETITION);
            outreachTicket.setInternalDetails("Internal details");
            return outreachTicket;
        }
    }
}
