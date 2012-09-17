package com.rosettastone.succor.utils;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

/**
 * The {@code TicketJSONParser} represents functionality for getting ticket from
 * JSON.
 */

public final class TicketJSONParser {

    public static final String TICKET_NODE_JSON = "Ticket";
    public static final String ID_ATTR_JSON = "@id";
    public static final String LANGUAGE_JSON = "Language";

    /**
     * Extract first single ticket from JSON response.
     * 
     * @param response
     * @return jsonObject
     * @throws JSONException
     */
    public static JSONObject parseTicketJsonFromEntitiesResponse(String response) throws JSONException {
        JSONObject entities = JSONUtils.checkAndParseEntitesFromResponse(response);
        if (entities == null) {
            return null;
        }
        return entities.getJSONArray(TICKET_NODE_JSON).getJSONObject(0);
    }

    /**
     * Extrace id of the first ticket in JSON response.
     * 
     * @param response
     * @return string
     * @throws JSONException
     */
    public static String findTicketIdFromEntitiesResponse(String response) throws JSONException {
        JSONObject ticket = parseTicketJsonFromEntitiesResponse(response);
        if (ticket == null) {
            return null;
        }
        return ticket.getString("@id");
    }

    /**
     * 
     * @param response
     * @return list
     * @throws JSONException
     */
    public static JSONArray getTicketsFromEntitiesResponse(String response) throws JSONException {
        JSONObject entities = JSONUtils.checkAndParseEntitesFromResponse(response);
        if (entities == null) {
            return null;
        }
        JSONArray tickets = entities.getJSONArray(TICKET_NODE_JSON);
        return tickets;
    }

    /**
     * Get ID from JSON Ticket object.
     * 
     * @param ticket
     * @return
     */
    public static String getTicketId(JSONObject ticket) {
        return JSONUtils.getString(ticket, "@id");
    }

    /**
     * Get Language text representation from Super Ticket.
     * 
     * @param ticket
     * @return
     */
    public static String getTicketLang(JSONObject ticket) {
        String language;
        try {
            JSONArray customFields = ticket.getJSONArray("Custom_Field");
            JSONObject languageField = JSONUtils.getCustomFieldByDisplayName(customFields, LANGUAGE_JSON);
            language = JSONUtils.getSelectedOption(languageField);
        } catch (JSONException e) {
            language = "";
        }
        return language;
    }
}
