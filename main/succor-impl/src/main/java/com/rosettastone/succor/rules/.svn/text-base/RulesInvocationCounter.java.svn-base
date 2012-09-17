package com.rosettastone.succor.rules;

import org.drools.event.rule.AfterActivationFiredEvent;
import org.drools.event.rule.DefaultAgendaEventListener;

/**
 * The {@code RulesInvocationCounter} allows get counter info.
 */

public class RulesInvocationCounter extends DefaultAgendaEventListener {

    private int counter = 0;

    /**
     * Increase {@code counter}.
     *
     * @param event
     */
    @Override
    public void afterActivationFired(AfterActivationFiredEvent event) {
        counter++;
    }

    public int getCounter() {
        return counter;
    }
}
