package com.rosettastone.succor.spring;

import org.springframework.context.ApplicationEvent;

import com.rosettastone.succor.model.bandit.Event;

public class BanditEventScopeEvent extends ApplicationEvent {

    public enum EventType { PROCESSING_STARTED, PROCESSING_FINISHED }
    
    private EventType eventType;
    
    public BanditEventScopeEvent(Event event, EventType eventType) {
        super(event);
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return eventType;
    }
}
