package com.rosettastone.succor.rules;

import org.easymock.EasyMock;
import org.testng.annotations.Test;

import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.model.bandit.LevelCompletionMessage;
import com.rosettastone.succor.service.EventService;

@Test
public class CustomerRulesLevelCompletedTest extends BaseCustomerRulesTest {

    private static final int LEVEL_1 = 1;
    private static final int LEVEL_2 = 2;
    private static final int LEVEL_3 = 3;
    private static final int LEVEL_4 = 4;
    private static final int LEVEL_5 = 5;

    public void testCompletionOfLevel1Phone() {
        doTestLevelCompletedPhone(LEVEL_1, new String[] {PRODUCT_NAME_L1 });
    }

    public void testCompletionOfLevel1Email() {
        String[] productNames = new String[] {PRODUCT_NAME_S3, PRODUCT_NAME_S5, PRODUCT_NAME_TOT };
        doTestLevelCompletedEmail(LEVEL_1, productNames);
    }

    public void testCompletionOfLevel1NoJapan() {
        doTestLevelCompletedInst(LEVEL_1, LANGUAGES_NO_JAPAN, true);
    }

    public void testCompletionOfLevel1Japan() {
        doTestLevelCompletedInst(LEVEL_1, LANGUAGES_JAPAN_ONLY, false);
    }

    public void testCompletionOfLevel2Phone() {
        doTestLevelCompletedPhone(LEVEL_2, new String[] {PRODUCT_NAME_L2, PRODUCT_NAME_S2 });
    }

    public void testCompletionOfLevel2Email() {
        String[] productNames = new String[] {PRODUCT_NAME_S3, PRODUCT_NAME_S5, PRODUCT_NAME_TOT };
        doTestLevelCompletedEmail(LEVEL_2, productNames);
    }

    public void testCompletionOfLevel2NoJapan() {
        doTestLevelCompletedInst(LEVEL_2, LANGUAGES_NO_JAPAN, true);
    }

    public void testCompletionOfLevel2Japan() {
        doTestLevelCompletedInst(LEVEL_2, new String[] {SUPPORT_LANG_JA_JP }, false);
    }

    public void testCompletionOfLevel3Phone() {
        doTestLevelCompletedPhone(LEVEL_3, new String[] {PRODUCT_NAME_L3, PRODUCT_NAME_S3 });
    }

    public void testCompletionOfLevel3Email() {
        String[] productNames = new String[] {PRODUCT_NAME_S5, PRODUCT_NAME_TOT };
        doTestLevelCompletedEmail(LEVEL_3, productNames);
    }

    public void testCompletionOfLevel3NoJapan() {
        doTestLevelCompletedInst(LEVEL_3, LANGUAGES_NO_JAPAN, true);
    }

    public void testCompletionOfLevel3Japan() {
        doTestLevelCompletedInst(LEVEL_3, new String[] {SUPPORT_LANG_JA_JP }, false);
    }

    public void testCompletionOfLevel4Phone() {
        doTestLevelCompletedPhone(LEVEL_4, new String[] {PRODUCT_NAME_L4 });
    }

    public void testCompletionOfLevel4Email() {
        String[] productNames = new String[] {PRODUCT_NAME_S5, PRODUCT_NAME_TOT };
        doTestLevelCompletedEmail(LEVEL_4, productNames);
    }

    public void testCompletionOfLevel4NoJapan() {
        doTestLevelCompletedInst(LEVEL_4, LANGUAGES_NO_JAPAN, true);
    }

    public void testCompletionOfLevel4Japan() {
        doTestLevelCompletedInst(LEVEL_4, new String[] {SUPPORT_LANG_JA_JP }, false);
    }

    public void testCompletionOfLevel5Phone() {
        doTestLevelCompletedPhone(LEVEL_5, new String[] {PRODUCT_NAME_L5, PRODUCT_NAME_S5, PRODUCT_NAME_TOT });
    }

    public void testCompletionOfLevel5NoJapan() {
        doTestLevelCompletedInst(LEVEL_5, LANGUAGES_NO_JAPAN, true);
    }

    public void testCompletionOfLevel5Japan() {
        doTestLevelCompletedInst(LEVEL_5, new String[] {SUPPORT_LANG_JA_JP }, false);
    }

    private Event createTestEvent(int level, String productName, String supportLang) {
        Event event = new Event();
        LevelCompletionMessage levelCompletionMessage = new LevelCompletionMessage();
        levelCompletionMessage.setLevel(level);
        event.setMessage(levelCompletionMessage);

        Customer customer = new Customer();
        customer.getProductNames().add(productName);
        event.setCustomer(customer);

        createSupportLocale(supportLang, event);

        return event;
    }

    private Event createTestEventInst(int level, String supportLang) {
        Event event = new Event();
        LevelCompletionMessage levelCompletionMessage = new LevelCompletionMessage();
        levelCompletionMessage.setLevel(level);
        Customer customer = new Customer();
        customer.setInstitutional(true);
        event.setCustomer(customer);
        event.setMessage(levelCompletionMessage);

        createSupportLocale(supportLang, event);

        return event;
    }

    private void doTestLevelCompletedPhone(int level, String[] products) {
        for (String supportLang : LANGUAGES_ALL) {
            for (String productName : products) {
                Event event = createTestEvent(level, productName, supportLang);
                EventService eventService = EasyMock.createMock(EventService.class);
                eventService.createPhoneTicketOrSendEmail(event);

                EasyMock.replay(eventService);

                executeRules(eventService, event);

                EasyMock.verify(eventService);
            }
        }
    }

    private void doTestLevelCompletedEmail(int level, String[] products) {
        for (String supportLang : LANGUAGES_ALL) {
            for (String productName : products) {
                Event event = createTestEvent(level, productName, supportLang);

                EventService eventService = EasyMock.createMock(EventService.class);
                eventService.sendEmail(event);

                EasyMock.replay(eventService);

                executeRules(eventService, event);

                EasyMock.verify(eventService);
            }
        }
    }

    private void doTestLevelCompletedInst(int level, String[] languages, boolean ruleFired) {
        for (String supportLang : languages) {
            Event event = createTestEventInst(level, supportLang);

            EventService eventService = EasyMock.createMock(EventService.class);
            if (ruleFired) {
                eventService.createInstitutionalTicket(event);
            }

            EasyMock.replay(eventService);

            executeRules(eventService, event);

            EasyMock.verify(eventService);
        }
    }

}
