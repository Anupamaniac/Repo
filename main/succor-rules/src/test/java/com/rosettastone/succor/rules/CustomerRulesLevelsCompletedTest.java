package com.rosettastone.succor.rules;

import org.easymock.EasyMock;
import org.testng.annotations.Test;

import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.model.bandit.LevelsCompletedMessage;
import com.rosettastone.succor.service.EventService;

@Test
public class CustomerRulesLevelsCompletedTest extends BaseCustomerRulesTest {

    private static final String LEVELS_123 = "1-3";
    private static final String LEVELS_12345 = "1-5";

    public void testCompletionOfLevels123Postal() {
        testCompletionOfLevelsPostal(LEVELS_123, new String[] {PRODUCT_NAME_L3, PRODUCT_NAME_S3, PRODUCT_NAME_S5,
                PRODUCT_NAME_TOT });
    }

    public void testCompletionOfLevels12345Postal() {
        testCompletionOfLevelsPostal(LEVELS_12345, new String[] {PRODUCT_NAME_L5, PRODUCT_NAME_S5, PRODUCT_NAME_TOT });
    }

    private void testCompletionOfLevelsPostal(String levels, String[] products) {
        for (String supportLang : LANGUAGES_ALL) {
            for (String product : products) {
                Event event = createTestEvent(levels, product, supportLang);

                EventService eventService = EasyMock.createMock(EventService.class);
                eventService.sendPostalMail(event);

                EasyMock.replay(eventService);

                executeRules(eventService, event);

                EasyMock.verify(eventService);
            }
        }
    }

    public void testCompletionOfLevels123Inst() {
        testCompletionOfLevelsInst(LEVELS_123, LANGUAGES_NO_JAPAN, true);
    }

    public void testCompletionOfLevels12345Inst() {
        testCompletionOfLevelsInst(LEVELS_12345, LANGUAGES_JAPAN_ONLY, false);
    }

    private void testCompletionOfLevelsInst(String levels, String[] languages, boolean ruleFired) {
        for (String supportLang : languages) {
            Event event = createTestEventInst(levels, supportLang);

            EventService eventService = EasyMock.createMock(EventService.class);
            if (ruleFired) {
                eventService.createInstitutionalTicket(event);
            }

            EasyMock.replay(eventService);

            executeRules(eventService, event);

            EasyMock.verify(eventService);
        }
    }

    private Event createTestEvent(String levels, String productName, String supportLang) {
        Event event = new Event();
        LevelsCompletedMessage message = new LevelsCompletedMessage();
        message.setLevels(levels);
        event.setMessage(message);

        com.rosettastone.succor.model.bandit.Customer customer = new com.rosettastone.succor.model.bandit.Customer();
        customer.getProductNames().add(productName);
        event.setCustomer(customer);

        createSupportLocale(supportLang, event);

        return event;
    }

    private Event createTestEventInst(String levels, String supportLang) {
        Event event = new Event();
        LevelsCompletedMessage message = new LevelsCompletedMessage();
        message.setLevels(levels);
        event.setMessage(message);

        Customer customer = new Customer();
        customer.setInstitutional(true);
        event.setCustomer(customer);

        createSupportLocale(supportLang, event);

        return event;
    }
}
