package com.rosettastone.succor.backgroundtask.impl;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/*
 * This test starts task executor threads, so it disabled to avoid side effects to other tests.
 * Run it manually after changes in context
 */
@Test (enabled = false)
@ContextConfiguration({"classpath:/backgroundtask-context.xml", "classpath:/backgrountaskexecutors-context.xml",
    "classpath:/test-context.xml" })
public class ContextIntegrationTest extends AbstractTestNGSpringContextTests {

    private static final long SLEEP_TIME = 1000;

    public void testContext() throws InterruptedException {
        Thread.sleep(SLEEP_TIME);
    }
}
