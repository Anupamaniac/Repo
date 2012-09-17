package com.rosettastone.succor.rules;

import org.easymock.EasyMock;
import org.testng.annotations.Test;

import com.rosettastone.succor.model.bandit.ClaimedExtensionsMessage;
import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.service.EventService;

@Test
public class ClaimedExtensionsMessageTest extends BaseCustomerRulesTest {

    public void testEmailMessage() {
        boolean institutional = false;
        Event event = createEvent(institutional, SUPPORT_LANG_EN_GB);
        EventService eventService = EasyMock.createMock(EventService.class);
        eventService.sendEmail(event);
        EasyMock.replay(eventService);
        executeRules(eventService, event);
        EasyMock.verify(eventService);
    }

    public void testEmailJaMessage() {
        boolean institutional = false;
        Event event = createEvent(institutional, SUPPORT_LANG_JA_JP);
        EventService eventService = EasyMock.createMock(EventService.class);
        eventService.sendEmail(event);
        EasyMock.replay(eventService);
        executeRules(eventService, event);
        EasyMock.verify(eventService);
    }

    public void testInstMessage() {
        boolean institutional = true;
        Event event = createEvent(institutional, SUPPORT_LANG_EN_GB);
        EventService eventService = EasyMock.createMock(EventService.class);
        eventService.createInstitutionalTicket(event);
        EasyMock.replay(eventService);
        executeRules(eventService, event);
        EasyMock.verify(eventService);
    }

    public void testInstJaMessage() {
        boolean institutional = true;
        Event event = createEvent(institutional, SUPPORT_LANG_JA_JP);
        EventService eventService = EasyMock.createMock(EventService.class);
        EasyMock.replay(eventService);
        executeRules(eventService, event);
        EasyMock.verify(eventService);
    }

    private Event createEvent(boolean institutional, String language) {
        Event event = new Event();

        Customer customer = new Customer();
        customer.setInstitutional(institutional);
        if (language != null) {
            createSupportLocale(language, event);
        }
        event.setCustomer(customer);
        event.setMessage(new ClaimedExtensionsMessage());

        return event;
    }

}
