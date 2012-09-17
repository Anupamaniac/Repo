package com.rosettastone.succor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rosettastone.succor.keepalive.KeepaliveController;
import com.rosettastone.succor.service.EventService;

@Test
@ContextConfiguration(locations = {
        "classpath:/properties-context.xml",
        "classpath:/rules-context.xml",
        "classpath:/hibernate-context.xml",
        "classpath:/service-context.xml",
        "classpath:/parature-context.xml",
        "classpath:/rabbitmq-context.xml",
        "classpath:/backgroundtask-context.xml",
        "classpath:/backgrountaskexecutors-context.xml",
        "classpath:/timer-context.xml",
        "classpath:/scheduler-context.xml",
        "classpath:/spring-servlet.xml"})
public class IntegrationTest extends AbstractTestNGSpringContextTests {

    private static final Log LOG = LogFactory.getLog(IntegrationTest.class);

    private static final long SLEEP_TIME = 1000;

    @Autowired
    private EventService eventService;

    @Autowired
    private KeepaliveController keepaliveController;

    public void smokeTest() throws InterruptedException {
        Assert.assertNotNull(eventService);
        Thread.sleep(SLEEP_TIME);
        LOG.debug("It works");
    }

    public void keepaliveTest() {
        Assert.assertEquals(keepaliveController.checkstatus(), "OK");
    }
}
