package com.rosettastone.succor.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.ApplicationListener;
import org.springframework.util.Assert;

import com.rosettastone.succor.exception.ApplicationException;
import com.rosettastone.succor.model.bandit.Event;

/**
 * This class implements custom spring scope.
 * Custom scope allow as to save event corresponding current processed event in special bean.
 * Scope is created for
 * @see ThreadLocal
 */
public class EventScope implements Scope, ApplicationListener<BanditEventScopeEvent> {

    private static final Log LOG = LogFactory.getLog(EventScope.class);

    private ThreadLocal<Event> currentEventStorage;
    private ThreadLocal<ScopeObjects> localScope = new ThreadLocal<ScopeObjects>();

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        LOG.debug("Requested bean: " + name);
        ScopeObjects scopeObjects = localScope.get();
        if (scopeObjects == null) {
            scopeObjects = new ScopeObjects();
            localScope.set(scopeObjects);
        }
        Object bean = scopeObjects.getBean(name);
        if (bean == null) {
            LOG.debug("Creating bean: " + name);
            bean = objectFactory.getObject();
            scopeObjects.setBean(name, bean);
        }
        return bean;
    }

    /**
     * Remove bean with specified name from the scope
     * @param name - bean  name
     * @return removed bean object
     */
    @Override
    public Object remove(String name) {
        LOG.debug("remove bean: " + name);
        return localScope.get().removeBean(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        LOG.debug("registerDestructionCallback name: " + name);
        localScope.get().registerDestructionCallback(name, callback);
    }

    @Override
    public Object resolveContextualObject(String key) {
        return localScope.get();
    }

    @Override
    public String getConversationId() {
        return null;
    }

    /**
     * This method is working as event listener.
     * This method is invoked when someone calls getApplicationContext().publishEvent(..)
     */
    @Override
    public void onApplicationEvent(BanditEventScopeEvent event) {
        switch(event.getEventType()) {
            case PROCESSING_STARTED :
                init((Event) event.getSource());
                break;
            case PROCESSING_FINISHED :
                destroy();
                break;
            default :
                throw new ApplicationException("Unknown event type : " + event.getEventType());
        }
    }

    /**
     * Initialize scope for specified event object
     */
    private void init(Event event) {
        LOG.debug("Initialize scope for event " + event.getMessage().getGuid());
        Assert.isNull(currentEventStorage.get(), "Customer scope was not correctly cleared!!!");
        currentEventStorage.set(event);
    }

    /**
     * Destroy current local scope.
     */
    private void destroy() {
        String guid = null;
        Event event = currentEventStorage.get();
        if (event != null) {
            guid = event.getMessage().getGuid();
        }
        LOG.debug("Destroy scope for customer " + guid);
        ScopeObjects scopedObjects = localScope.get();
        if (scopedObjects != null) {
            scopedObjects.destroy();
        }
        currentEventStorage.remove();
        Assert.isNull(currentEventStorage.get());
    }

    @Required
    public void setCurrentEventStorage(ThreadLocal<Event> currentEventStorage) {
        this.currentEventStorage = currentEventStorage;
    }
}
