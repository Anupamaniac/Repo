package com.rosettastone.succor.parature;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rosettastone.succor.exception.ApplicationException;
import com.rosettastone.succor.model.bandit.Event;

@Test
@ContextConfiguration({"classpath:/properties-context.xml", "classpath:/test-ParatureProperties-context.xml" })
public class ParaturePropertiesTest extends AbstractTestNGSpringContextTests {

    private static final String TEST_KEY = "language.name";

    @Autowired
    private ParatureProperties paratureProperties;

    @Autowired
    private ThreadLocal<Event> currentEventStorage;

    public void testConfiguration() {
        Assert.assertNotNull(applicationContext);
    }

    public void testGetEnValue() {
        initCustomer("en-GB");
        Assert.assertEquals(paratureProperties.get(TEST_KEY), "English");
    }

    private void initCustomer(String lang) {
        Event event = new Event();
        String[] localeStr = lang.split("-");
        Locale locale = new Locale(localeStr[0]);
        event.getInternalSuccorData().setSupportLocale(locale);
        currentEventStorage.set(event);
    }

    public void testGetEsValue() {
        initCustomer("es-ES");
        Assert.assertEquals(paratureProperties.get(TEST_KEY), "Spanish");
    }

    public void testGetJaValue() {
        initCustomer("ja-JP");
        Assert.assertEquals(paratureProperties.get(TEST_KEY), "Japanese");
    }

    public void testGetDefaultValue() {
        initCustomer("ko-KR");
        Assert.assertEquals(paratureProperties.get(TEST_KEY), "English");
    }

    @Test(expectedExceptions = ApplicationException.class)
    public void testNotInitialized() {
        currentEventStorage.remove();
        paratureProperties.get(TEST_KEY);
    }
}
