package com.rosettastone.succor.utils;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import com.rosettastone.succor.exception.ApplicationException;

public class JSONUtils {

    public static final String OPTION = "Option";
    public static final String SELECTED = "@selected";
    public static final String VALUE = "Value";
    public static final String TRUE = "true";
    public static final String TEXT = "#text";
    public static final String ENTITIES_NODE_JSON = "Entities";
    public static final String TOTAL_ATTR_JSON = "@total";

    /**
     * Get string value from JSON object.
     * 
     * @param object
     * @param key
     * @return
     */
    public static String getString(JSONObject object, String key) {
        String value = null;
        try {
            value = object.getString(key);
        } catch (JSONException e) {
            throw new ApplicationException("JSON parse exception");
        }
        return value;
    }

    /**
     * Get selected option from JSON list field.
     * 
     * @param field
     * @return
     * @throws JSONException
     */
    public static String getSelectedOption(JSONObject field) throws JSONException {
        if (!field.has(OPTION)) {
            return "";
        }

        JSONArray options = field.getJSONArray(OPTION);
        for (int i = 0; i < options.length(); i++) {
            JSONObject option = options.getJSONObject(i);
            if (option.has(SELECTED) && option.getString(SELECTED).equals(TRUE)) {
                return option.getString(VALUE);
            }
        }
        return "";
    }

    /**
     * Get text representation of the JSON field.
     * 
     * @param field
     * @return
     * @throws JSONException
     */
    public static String getTextValue(JSONObject field) throws JSONException {
        if (field == null || !field.has(TEXT)) {
            return "";
        }
        return field.getString(TEXT);
    }

    /**
     * Get custom field JSON object from the list of Customer's Custom Fields.
     * 
     * @param customFields
     * @param customFieldId
     * @return
     * @throws JSONException
     */
    public static JSONObject getCustomFieldById(JSONArray customFields, String customFieldId) throws JSONException {
        for (int i = 0; i < customFields.length(); i++) {
            JSONObject customField = customFields.getJSONObject(i);
            if (customField.getString("@id").equals(customFieldId)) {
                return customField;
            }
        }
        return null;
    }

    /**
     * Parse entities from response, check they not null.
     * 
     * @param response
     * @return jsonObject
     * @throws JSONException
     */
    public static JSONObject checkAndParseEntitesFromResponse(String response) throws JSONException {
        JSONObject json = new JSONObject(response);
        JSONObject entities = json.getJSONObject(ENTITIES_NODE_JSON);

        if (entities.getInt(TOTAL_ATTR_JSON) == 0) {
            return null;
        }
        return entities;
    }

    /**
     * Get custom field JSON object from list of Customer's Custom Fields by
     * Display Name.
     * 
     * @param customFields
     * @param customFieldDisplayName
     * @return
     * @throws JSONException
     */
    public static JSONObject getCustomFieldByDisplayName(JSONArray customFields, String customFieldDisplayName)
            throws JSONException {
        for (int i = 0; i < customFields.length(); i++) {
            JSONObject customField = customFields.getJSONObject(i);
            if (customField.getString("@display-name").trim().equals(customFieldDisplayName)) {
                return customField;
            }
        }
        return null;
    }
}
