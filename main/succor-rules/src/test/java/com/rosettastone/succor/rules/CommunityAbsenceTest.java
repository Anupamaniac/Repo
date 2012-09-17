package com.rosettastone.succor.rules;

import org.easymock.EasyMock;
import org.testng.annotations.Test;

import com.rosettastone.succor.model.bandit.CommunityAbsenceMessage;
import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.service.EventService;

@Test
public class CommunityAbsenceTest extends BaseCustomerRulesTest {
    private static final int DAYS = 30;

    public void testNotLogged30daysFirstPhoneEmail() {
        for (String supportLang : LANGUAGES_ALL) {
            Event event = createTestEvent(false, true, supportLang);

            EventService eventService = EasyMock.createMock(EventService.class);
            eventService.sendEmail(event);
            eventService.createPhoneTicket(event);

            EasyMock.replay(eventService);

            executeRules(eventService, event);

            EasyMock.verify(eventService);
        }
    }

    public void testNotLogged30daysInstNoJapan() {
        for (String supportLang : LANGUAGES_NO_JAPAN) {
            Event event = createTestEvent(true, true, supportLang);

            testInstitutional(event, true);
        }
    }

    public void testNotLogged30daysInstJapan() {
        for (String supportLang : LANGUAGES_JAPAN_ONLY) {
            Event event = createTestEvent(true, true, supportLang);

            testInstitutional(event, false);
        }
    }

    public void testNotLogged30daysPostal() {
        for (String supportLang : LANGUAGES_ALL) {
            Event event = createTestEvent(false, false, supportLang);

            EventService eventService = EasyMock.createMock(EventService.class);
            eventService.sendPostalMail(event);

            EasyMock.replay(eventService);

            executeRules(eventService, event);

            EasyMock.verify(eventService);
        }
    }

    // mock test

    private void testInstitutional(Event event, boolean ruleFire) {
        EventService eventService = EasyMock.createMock(EventService.class);
        if (ruleFire) {
            eventService.createInstitutionalTicket(event);
        }

        EasyMock.replay(eventService);

        executeRules(eventService, event);

        EasyMock.verify(eventService);
    }

    // create events

    private Event createTestEvent(boolean institutional, boolean firstMessage, String language) {
        Event event = new Event();

        Customer customer = new Customer();
        customer.setInstitutional(institutional);
        event.setCustomer(customer);

        CommunityAbsenceMessage message = new CommunityAbsenceMessage();
        message.setDays(DAYS);
        message.setFirstMessageToThisUser(firstMessage);
        event.setMessage(message);
        event.getInternalSuccorData().setPhoneValid(true);

        createSupportLocale(language, event);

        return event;
    }
}
