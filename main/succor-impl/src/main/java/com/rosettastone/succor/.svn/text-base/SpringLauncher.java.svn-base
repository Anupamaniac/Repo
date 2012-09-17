package com.rosettastone.succor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class SpringLauncher {

    private static final Log LOG = LogFactory.getLog(SpringLauncher.class);

    private SpringLauncher() {
    }

    public static void main(String[] args) {
        LOG.info("Loading application context");
        new ClassPathXmlApplicationContext(new String[] {
                "properties-context.xml",
                "rabbitmq-context.xml",
                "parature-context.xml",
                "rules-context.xml",
                "service-context.xml",
                "sms-context.xml",
                "backgroundtask-context.xml",
                "backgrountaskexecutors-context.xml",
                "hibernate-context.xml",
                "timer-context.xml"
                });
        LOG.info("Loading application context - Done");
        LOG.info("Application started");
    }
}
