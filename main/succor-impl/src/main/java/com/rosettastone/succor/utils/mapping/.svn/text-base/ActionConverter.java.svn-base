package com.rosettastone.succor.utils.mapping;

import org.springframework.beans.factory.annotation.Required;

import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.model.bandit.MessageType;
import com.rosettastone.succor.model.parature.TicketType;
import com.rosettastone.succor.parature.ParatureProperties;

public class ActionConverter {

    private static final String PARATURE_PREFIX = "parature.action.type.";
    private static final String DELIMITER = "_";
    private static final String EMAIL = "email";

    private ParatureProperties paratureProperties;

    public String convert(Event event, TicketType ticketType) {
        MessageType messageType = event.getHeader().getMessageType();
        if (messageType == null || MessageType.UNKNOWN.equals(messageType)) {
            return null;
        }

        String action = event.getInternalSuccorData().getActionAsKey();

        // for email
        if (ticketType.equals(TicketType.SUPER)) {
            action += DELIMITER + EMAIL;
        } else {
            action += DELIMITER + ticketType.toString().toLowerCase();
        }
        String value = paratureProperties.get(PARATURE_PREFIX + action);
        if (value == null) {
            throw new IllegalArgumentException("Can't find action code for " + PARATURE_PREFIX + action);
        }
        return value;
    }

    @Required
    public void setParatureProperties(ParatureProperties paratureProperties) {
        this.paratureProperties = paratureProperties;
    }
}
