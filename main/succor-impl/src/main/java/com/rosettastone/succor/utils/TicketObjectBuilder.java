package com.rosettastone.succor.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.MessageSource;

import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.model.bandit.Message;
import com.rosettastone.succor.model.bandit.StudioReadinessMessage;
import com.rosettastone.succor.model.parature.Action;
import com.rosettastone.succor.model.parature.ActionCsr;
import com.rosettastone.succor.model.parature.ContactReasonType;
import com.rosettastone.succor.model.parature.OutreachTicket;
import com.rosettastone.succor.model.parature.SuperTicket;
import com.rosettastone.succor.model.parature.TicketLanguageType;
import com.rosettastone.succor.model.parature.TicketType;
import com.rosettastone.succor.parature.TicketParatureApi;
import com.rosettastone.succor.service.EmailService;
import com.rosettastone.succor.utils.format.FormatUtils;
import com.rosettastone.succor.utils.mapping.ContactReasonConverter;
import com.rosettastone.succor.utils.mapping.LanguageConverter;

/**
 * THe {@code TicketObjectBuilder} allows to create tickets.
 */
public class TicketObjectBuilder {

    private ContactReasonConverter contactReasonConverter;

    private LanguageConverter languageConverter;

    private MessageSource messageSource;

    /**
     * Create OutreachTicket object.
     * 
     * @param event
     * @param ticketType
     * @param language
     * @return
     */
    public OutreachTicket prepareOutreachTicket(Event event, TicketType ticketType, String language) {
        OutreachTicket outreachTicket = new OutreachTicket(ticketType);

        ContactReasonType contactReason = contactReasonConverter.convert(event);
        String internalDetails = getInternalDetails(event, language);

        outreachTicket.setCustomer(event.getCustomer());
        outreachTicket.setContactReason(contactReason);
        outreachTicket.setInternalDetails(internalDetails);
        return outreachTicket;
    }

    /**
     * Prepare Super Ticket object.
     * 
     * @param event
     * @return superTicket
     */
    public SuperTicket prepareSuperTicket(Event event) {
        SuperTicket superTicket = new SuperTicket();

        superTicket.setCustomer(event.getCustomer());
        setLangToSuperTicket(superTicket, event);
        superTicket.setSummary(TicketParatureApi.SUCCESS_SUPER);

        return superTicket;
    }

    /**
     * Create and return action object.
     * 
     * @param ticketId
     * @param type
     * @param actionCreatedAt
     * @param email
     * @return action
     */
    public Action getActionObject(long ticketId, String type, Date actionCreatedAt, String email) {
        Action action = new Action();
        action.setTicketId(ticketId);
        action.setType(type);
        action.setComment(FormatUtils.createComment(actionCreatedAt, email));
        return action;
    }

    public ActionCsr getActionCsrObject(long csrId) {
        ActionCsr action = new ActionCsr();
        action.setAssignToCsr(csrId);
        action.setComment("Assigning the ticket to you.");
        action.setShowToCust(false);
        action.setTimeSpentMinutes(15);
        return action;
    }

    /**
     * Create super ticket object.
     * 
     * @param event
     * @return
     */
    public SuperTicket createSuperTicketObjectWithCustomerAndLanguage(Event event) {
        SuperTicket ticket = new SuperTicket();
        ticket.setCustomer(event.getCustomer());
        setLangToSuperTicket(ticket, event);
        return ticket;
    }

    /**
     * Convert and set language to ticket.
     * 
     * @param ticket
     * @param event
     */
    private void setLangToSuperTicket(SuperTicket ticket, Event event) {
        if (event.getMessage().getIsoLanguageCode() != null) {
            TicketLanguageType language = languageConverter.convert(event.getMessage().getIsoLanguageCode());
            ticket.setLanguage(language);
        }
    }

    /**
     * 
     * @param event
     * @param language
     * @return string
     */
    private String getInternalDetails(Event event, String language) {
        Customer customer = event.getCustomer();
        String productName;
        if (customer.getInstitutional()) {
            productName = "";
        } else {
            productName = customer.getProductNames().get(0);
        }

        Message message = event.getMessage();
        if (message instanceof StudioReadinessMessage) {
            StudioReadinessMessage studioReadinessMessage = (StudioReadinessMessage) message;
            if (studioReadinessMessage.getFirstMessageToThisUser()) {
                productName += " "
                        + FormatUtils.formatLevelUnit(studioReadinessMessage.getLevel(),
                                studioReadinessMessage.getUnit());
            }
        }

        Date eventDate = event.getHeader().getCreatedAt();
        return FormatUtils.productLanguageLevelTime(productName, language, eventDate);
    }

    /**
     * Update ticket's summary according to format: CONTACT_REASON TICKET_TYPE.
     * 
     * @param outreachTicket
     * @param event
     */
    public void updateTicketSummary(OutreachTicket outreachTicket, Event event) {
        LocalizedMessageSource localizedMessageSource = new LocalizedMessageSourceImpl(messageSource, event
                .getInternalSuccorData().getSupportLocale());
        String summary = EmailService.getSubject(event, localizedMessageSource);
        outreachTicket.setSummary(summary);
    }

    @Required
    public void setContactReasonConverter(ContactReasonConverter contactReasonConverter) {
        this.contactReasonConverter = contactReasonConverter;
    }

    @Required
    public void setLanguageConverter(LanguageConverter languageConverter) {
        this.languageConverter = languageConverter;
    }

    @Required
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}
