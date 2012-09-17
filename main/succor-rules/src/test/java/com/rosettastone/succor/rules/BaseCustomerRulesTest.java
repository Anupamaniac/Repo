package com.rosettastone.succor.rules;

import java.util.List;
import java.util.Locale;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.service.EventService;
import com.rosettastone.succor.util.ResourceBundleLocalizedMessageSource;
import com.rosettastone.succor.utils.LocalizedMessageSource;

public class BaseCustomerRulesTest {

    public static final String PRODUCT_NAME_L1 = "L1";
    public static final String PRODUCT_NAME_L2 = "L2";
    public static final String PRODUCT_NAME_L3 = "L3";
    public static final String PRODUCT_NAME_L4 = "L4";
    public static final String PRODUCT_NAME_L5 = "L5";
    public static final String PRODUCT_NAME_S2 = "S2";
    public static final String PRODUCT_NAME_S3 = "S3";
    public static final String PRODUCT_NAME_S5 = "S5";
    public static final String PRODUCT_NAME_TOT = "TOT";

    public static final String SUPPORT_LANG_EN_GB = "en-GB";
    public static final String SUPPORT_LANG_EN_US = "en-US";
    public static final String SUPPORT_LANG_JA_JP = "ja-JP";
    public static final String SUPPORT_LANG_ES_419 = "es-419";
    public static final String SUPPORT_LANG_ES_ES = "es-ES";

    public static final String[] LANGUAGES_ALL = new String[] {SUPPORT_LANG_EN_GB, SUPPORT_LANG_EN_US,
            SUPPORT_LANG_ES_419, SUPPORT_LANG_ES_ES, SUPPORT_LANG_JA_JP };

    public static final String[] LANGUAGES_NO_JAPAN = new String[] {SUPPORT_LANG_EN_GB, SUPPORT_LANG_EN_US,
            SUPPORT_LANG_ES_419, SUPPORT_LANG_ES_ES };
    public static final String[] LANGUAGES_JAPAN_ONLY = new String[] {SUPPORT_LANG_JA_JP };

    private KnowledgeBuilder kbuilder;
    private KnowledgeBase kbase;
    private LocalizedMessageSource messageSource = new ResourceBundleLocalizedMessageSource();

    @BeforeClass
    public void initTests() {
        kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("CustomersChangeset.xml", getClass()),
                ResourceType.CHANGE_SET);

        Assert.assertFalse(kbuilder.hasErrors(), kbuilder.getErrors().toString());

        kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
    }

    protected KnowledgeBuilder getKbuilder() {
        return kbuilder;
    }

    protected KnowledgeBase getKbase() {
        return kbase;
    }

    protected LocalizedMessageSource getMessageSource() {
        return messageSource;
    }

    protected void executeRules(EventService eventService, List<Customer> customers) {
        StatelessKnowledgeSession ksession = kbase.newStatelessKnowledgeSession();
        ksession.setGlobal("eventService", eventService);
        ksession.setGlobal("messageSource", messageSource);

        ksession.execute(customers);
    }

    protected void executeRules(EventService eventService, Event event) {
        StatelessKnowledgeSession ksession = kbase.newStatelessKnowledgeSession();
        ksession.setGlobal("eventService", eventService);

        ksession.execute(event);
    }

    protected void createSupportLocale(String language, Event event) {
        event.getInternalSuccorData().setSupportLocale(new Locale(language.split("-")[0]));
    }
}
