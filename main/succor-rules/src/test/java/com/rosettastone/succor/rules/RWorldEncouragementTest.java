package com.rosettastone.succor.rules;

import org.easymock.EasyMock;
import org.testng.annotations.Test;

import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.model.bandit.RWorldEncouragementMessage;
import com.rosettastone.succor.service.EventService;

@Test
public class RWorldEncouragementTest extends BaseCustomerRulesTest {

    public void testRwoldMessage() {
        boolean institutional = false;
        Event event = createEvent(institutional, SUPPORT_LANG_EN_GB);
        EventService eventService = EasyMock.createMock(EventService.class);
        eventService.sendEmail(event);
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
        event.setMessage(new RWorldEncouragementMessage());

        return event;
    }

}
