package com.rosettastone.succor.rules;

import org.easymock.EasyMock;
import org.testng.annotations.Test;

import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.model.bandit.FirstCompletedStudioSessionMessage;
import com.rosettastone.succor.model.bandit.StudioReadinessMessage;
import com.rosettastone.succor.model.bandit.StudioReminderMessage;
import com.rosettastone.succor.service.EventService;

@Test
public class CustomerRulesSessionTest extends BaseCustomerRulesTest {

    // Ready for first session removed for now 
//    public void testReadyForFirstSession() {
//        boolean firstSession = true;
//        boolean institutional = false;
//        Event event = createTestEventStudioReadiness(firstSession, institutional, null);
//
//        EventService eventService = EasyMock.createMock(EventService.class);
//        eventService.createPhoneTicketDontCheckPhone(event);
//
//        EasyMock.replay(eventService);
//
//        executeRules(eventService, event);
//
//        EasyMock.verify(eventService);
//    }
//
//    public void testReadyForFirstSessionInst() {
//        testInstActionStudioReadiness(LANGUAGES_NO_JAPAN, true);
//    }
//
//    public void testReadyForFirstSessionInstJp() {
//        testInstActionStudioReadiness(LANGUAGES_JAPAN_ONLY, false);
//    }

    private void testInstActionStudioReadiness(String[] supportLanguages, boolean fireRule) {
        boolean firstSession = true;
        boolean institutional = true;
        for (String supportLang : supportLanguages) {

            Event event = createTestEventStudioReadiness(firstSession, institutional, supportLang);

            testInstitutionalAction(event, fireRule);
        }
    }

    // //////////////////////////////////////////////////////////////////////////////

    //
    public void testCompletedFirstSession() {
        boolean institutional = false;

        for (String supportLang : LANGUAGES_ALL) {
            Event event = createFirstSessionCompletedEvent(institutional, supportLang);
            testEmailAction(event);
        }
    }

    public void testCompletedFirstSessionInst() {
        boolean institutional = true;

        for (String supportLang : LANGUAGES_NO_JAPAN) {
            Event event = createFirstSessionCompletedEvent(institutional, supportLang);
            testInstitutionalAction(event, true);
        }
    }

    public void testCompletedFirstSessionInstJp() {
        boolean institutional = true;

        for (String supportLang : LANGUAGES_JAPAN_ONLY) {
            Event event = createFirstSessionCompletedEvent(institutional, supportLang);
            testInstitutionalAction(event, false);
        }
    }

    public void testStudioReminder() {
        Event event = createStudioReminderEvent();
        testEmailAction(event);
    }

    // create events

    private Event createStudioReminderEvent() {
        Event event = createEvent(false, null);

        StudioReminderMessage message = new StudioReminderMessage();
        event.setMessage(message);

        return event;
    }

    private Event createFirstSessionCompletedEvent(boolean institutional, String language) {
        Event event = createEvent(institutional, language);

        FirstCompletedStudioSessionMessage message = new FirstCompletedStudioSessionMessage();
        event.setMessage(message);

        return event;
    }

    private Event createTestEventStudioReadiness(boolean first, boolean institutional, String language) {
        Event event = createEvent(institutional, language);

        StudioReadinessMessage message = new StudioReadinessMessage();
        message.setFirstMessageToThisUser(true);
        event.setMessage(message);

        return event;
    }

    private Event createEvent(boolean institutional, String language) {
        Event event = new Event();

        Customer customer = new Customer();
        customer.setInstitutional(institutional);
        if (language != null) {
            createSupportLocale(language, event);
        }
        event.setCustomer(customer);

        return event;
    }

    // / test actions

    private void testEmailAction(Event event) {
        EventService eventService = EasyMock.createMock(EventService.class);
        eventService.sendEmail(event);

        EasyMock.replay(eventService);

        executeRules(eventService, event);

        EasyMock.verify(eventService);
    }

    private void testInstitutionalAction(Event event, boolean fireRule) {
        EventService eventService = EasyMock.createMock(EventService.class);

        if (fireRule) {
            eventService.createInstitutionalTicket(event);
        }

        EasyMock.replay(eventService);

        executeRules(eventService, event);

        EasyMock.verify(eventService);
    }

}
