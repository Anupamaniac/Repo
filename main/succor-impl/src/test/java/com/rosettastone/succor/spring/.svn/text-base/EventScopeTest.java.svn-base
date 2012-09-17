package com.rosettastone.succor.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.model.bandit.Message;
import com.rosettastone.succor.spring.BanditEventScopeEvent.EventType;

@Test
@ContextConfiguration({"classpath:/properties-context.xml",
    "classpath:/parature-context.xml",
    "classpath:/test-CustomerScopeTest-context.xml" })
public class EventScopeTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ScopeTestInterface scopeTestInterface;

    public void testImplementation() {
        ScopeTestInterface impl = new ScopeTestBean();
        Assert.assertEquals(impl.getCounter(), 0);
        Assert.assertEquals(impl.getCounter(), 1);
    }

    public void testScopeOneExecution() {
        Event event = createEvent();
        applicationContext.publishEvent(new BanditEventScopeEvent(event, EventType.PROCESSING_STARTED));
        Assert.assertEquals(scopeTestInterface.getCounter(), 0);
        Assert.assertEquals(scopeTestInterface.getCounter(), 1);
        applicationContext.publishEvent(new BanditEventScopeEvent(event, EventType.PROCESSING_FINISHED));
    }

    public void testScopeTwoExecution() {
        Event event = createEvent();
        applicationContext.publishEvent(new BanditEventScopeEvent(event, EventType.PROCESSING_STARTED));
        Assert.assertEquals(scopeTestInterface.getCounter(), 0);
        Assert.assertEquals(scopeTestInterface.getCounter(), 1);
        applicationContext.publishEvent(new BanditEventScopeEvent(event, EventType.PROCESSING_FINISHED));
        applicationContext.publishEvent(new BanditEventScopeEvent(event, EventType.PROCESSING_STARTED));
        Assert.assertEquals(scopeTestInterface.getCounter(), 0);
        applicationContext.publishEvent(new BanditEventScopeEvent(event, EventType.PROCESSING_FINISHED));
    }

    public void testScopeNotCleaned() {
        Event event = createEvent();
        applicationContext.publishEvent(new BanditEventScopeEvent(event, EventType.PROCESSING_STARTED));
        Assert.assertEquals(scopeTestInterface.getCounter(), 0);
        try {
            applicationContext.publishEvent(new BanditEventScopeEvent(event, EventType.PROCESSING_STARTED));
            Assert.fail("Scope should throws exception");
        } catch (IllegalArgumentException e) {
            applicationContext.publishEvent(new BanditEventScopeEvent(event, EventType.PROCESSING_FINISHED));
        }
    }

    private Event createEvent() {
        Event event = new Event();
        event.setMessage(new Message());
        event.getMessage().setGuid("e1");
        return event;
    }
}
