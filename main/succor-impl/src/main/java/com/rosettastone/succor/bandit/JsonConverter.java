package com.rosettastone.succor.bandit;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

/**
 * Base interface for all converters from json to model objects
 * @param <C> model object class
 */
public interface JsonConverter<C> {
    /**
     * Converts json object to instance of specified model object class.
     * @param json
     * @return
     * @throws JSONException
     */
    C convert(JSONObject json) throws JSONException;

}
