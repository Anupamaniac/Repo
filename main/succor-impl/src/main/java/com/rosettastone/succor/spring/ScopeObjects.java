package com.rosettastone.succor.spring;

import java.util.HashMap;
import java.util.Map;


/**
 * Store for event scope objects
 * @see EventScope
 */
public class ScopeObjects {

    /**
     * Name to bean object map
     */
    private Map<String, Object> beans = new HashMap<String, Object>();

    /**
     * Name to callback object map
     */
    private Map<String, Runnable> destructionCallbacks = new HashMap<String, Runnable>();

    /**
     * Destroy scope object:
     * - Run all destruction callbacks
     * - Remove all beans
     * - Remove all callbacks
     */
    public void destroy() {
        for (Runnable callback : destructionCallbacks.values()) {
            callback.run();
        }
        destructionCallbacks.clear();
        beans.clear();
    }

    /**
     * Put bean with specified name to the scope
     * @param name - bean name
     *
     */
    public void setBean(String name, Object bean) {
        beans.put(name, bean);
    }

    /**
     * Return bean with specified name
     * @param name - bean name
     * @return spring bean
     */
    public Object getBean(String name) {
        return beans.get(name);
    }

    /**
     * Remove bean with specified name from the scope
     * @param name - bean name
     * @return removed spring bean
     */
    public Object removeBean(String name) {
        destructionCallbacks.remove(name);
        return beans.remove(name);
    }

    /**
     * Add destruction callback to the scope
     * @param name
     * @param callback
     */
    public void registerDestructionCallback(String name, Runnable callback) {
        destructionCallbacks.put(name, callback);
    }
}
