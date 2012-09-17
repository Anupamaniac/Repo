package com.rosettastone.succor.rules;

import org.easymock.EasyMock;
import org.testng.annotations.Test;

import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.model.bandit.Message;
import com.rosettastone.succor.model.bandit.SessionCancelReason;
import com.rosettastone.succor.model.bandit.StudioSessionCancellationMessage;
import com.rosettastone.succor.service.EventService;

@Test
public class SessionCancelledTest extends BaseCustomerRulesTest {

    public void testSessionCancelledNoCoachYesLearner() {
        Event event = createNoCoachYesLearnerEvent();
        testEmailAndPhone(event);
    }

    public void testSessionCancelledYesCoachNoLearner() {
        Event event = createYesCoachNoLearnerEvent();
        testInst(event);
    }

    public void testSessionCancelledBeforeSession() {
        Event event = createCancelledBeforeEvent();
        testEmailAndPhone(event);
    }

    public void testSessionCancelledDuringSession() {
        Event event = createCancelledDuringEvent();
        testEmailAndPhone(event);
    }

    // test mock
    private void testEmailAndPhone(Event event) {
        EventService eventService = EasyMock.createMock(EventService.class);
        eventService.createPhoneTicket(event);
        eventService.sendEmail(event);

        EasyMock.replay(eventService);

        executeRules(eventService, event);

        EasyMock.verify(eventService);
    }

    private void testInst(Event event) {
        EventService eventService = EasyMock.createMock(EventService.class);
        eventService.createInstitutionalTicket(event);

        EasyMock.replay(eventService);

        executeRules(eventService, event);

        EasyMock.verify(eventService);
    }

    // create event

    private Event createEvent(boolean inst) {
        Event event = new Event();

        Customer customer = new Customer();
        customer.setInstitutional(inst);
        event.setCustomer(customer);

        createSupportLocale(SUPPORT_LANG_EN_GB, event);

        return event;
    }

    private Event createNoCoachYesLearnerEvent() {
        Event event = createEvent(false);

        event.setMessage(createMessage(SessionCancelReason.no_coach_yes_learner));

        return event;
    }

    private Event createYesCoachNoLearnerEvent() {
        Event event = createEvent(true);

        event.setMessage(createMessage(SessionCancelReason.cancelled_session_no_learner));

        return event;
    }

    private Event createCancelledBeforeEvent() {
        Event event = createEvent(false);

        event.setMessage(createMessage(SessionCancelReason.cancelled_before_session));

        return event;
    }

    private Event createCancelledDuringEvent() {
        Event event = createEvent(false);

        event.setMessage(createMessage(SessionCancelReason.cancelled_session_yes_learner));

        return event;
    }

    // create message

    private Message createMessage(SessionCancelReason reason) {
        StudioSessionCancellationMessage message = new StudioSessionCancellationMessage();
        message.setReason(reason);
        return message;
    }
}
