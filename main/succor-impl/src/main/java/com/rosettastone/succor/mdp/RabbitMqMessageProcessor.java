package com.rosettastone.succor.mdp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.support.ApplicationObjectSupport;

import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.rules.RulesInvoker;

/**
 * The {@code RabbitMqMessageProcessor} processes the JSON message.
 */
public class RabbitMqMessageProcessor extends ApplicationObjectSupport {

    private Log log = LogFactory.getLog(RabbitMqMessageProcessor.class);

    private RulesInvoker rulesInvoker;
    private ThreadLocal<Exception> currentRuleExceptionStorage;

    /**
     * Main method for message processing.
     * 
     * @param event object for processing
     */
    public void processMessage(Event event) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Received: " + event.toString() + "\n");
        }
        try {
            rulesInvoker.invokeForEvent(event);
        } catch (Exception e) {
            // get original exception
            Exception originalExc = currentRuleExceptionStorage.get();
            currentRuleExceptionStorage.set(null);
            if (originalExc != null) {
                throw originalExc;
            } else {
                throw e;
            }
        }
    }

    @Required
    public void setRulesInvoker(RulesInvoker rulesInvoker) {
        this.rulesInvoker = rulesInvoker;
    }

    @Required
    public void setCurrentRuleExceptionStorage(ThreadLocal currentRuleExceptionStorage) {
        this.currentRuleExceptionStorage = currentRuleExceptionStorage;
    }
}
