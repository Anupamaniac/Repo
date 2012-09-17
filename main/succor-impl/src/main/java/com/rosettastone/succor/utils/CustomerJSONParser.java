package com.rosettastone.succor.utils;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.springframework.beans.factory.annotation.Required;

import com.rosettastone.succor.parature.ParatureProperties;

/**
 * Parsser for Customer object It parses Parature server responce JSON
 */
public class CustomerJSONParser {

    private ParatureProperties paratureProperties;

    /**
     * Parse customer JSON from response.
     * 
     * @param response
     * @return
     * @throws JSONException
     */
    public JSONObject parseCustomerJSON(String response) throws JSONException {
        JSONObject json = new JSONObject(response);
        JSONObject entities = json.getJSONObject("Entities");
        if (entities.getInt("@total") == 0) {
            throw new NullPointerException();
        }
        JSONObject customerJSON = entities.getJSONArray("Customer").getJSONObject(0);
        return customerJSON;
    }

    /**
     * Return if the user wants to be contacted.
     * 
     * @param customFields
     * @return false if Method of Contact is set to Do Not Contact, true
     *         otherwise
     * @throws JSONException
     */
    public boolean parseIsContactCustomer(JSONArray customFields) throws JSONException {
        JSONObject methodOfContact = JSONUtils
                .getCustomFieldById(customFields, paratureProperties.getMethodOfContact());

        if (methodOfContact == null || !methodOfContact.has("Option")) {
            return true;
        }
        // TODO use JSONUtils.getSelectedOptions
        JSONArray options = methodOfContact.getJSONArray("Option");
        for (int i = 0; i < options.length(); i++) {
            JSONObject option = options.getJSONObject(i);
            if (option.has("@selected") && option.getString("@selected").equals("true")
                    && option.getString("@id").equals(paratureProperties.getDoNotContact())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Parse master GUID for parent.
     * 
     * @param customFields
     * @return
     * @throws JSONException
     */
    public String getMasterGuid(JSONArray customFields) throws JSONException {
        JSONObject masterGuidField = JSONUtils.getCustomFieldById(customFields, paratureProperties.getMasterGuid());

        if (masterGuidField == null) {
            return "";
        }

        return JSONUtils.getTextValue(masterGuidField);
    }

    /**
     * Get customer phone from Custom Field.
     * 
     * @param customFields
     * @return
     * @throws JSONException
     */
    public String getMobileNumber(JSONArray customFields) throws JSONException {
        JSONObject phoneCustomField = JSONUtils.getCustomFieldById(customFields, paratureProperties.getMobile());
        return JSONUtils.getTextValue(phoneCustomField);
    }

    public String getDedicatedAgent(JSONArray customFields) throws JSONException {
        JSONObject agentCustomField = JSONUtils.getCustomFieldById(customFields,
                paratureProperties.getCustomerDedicatedAgent());
        return JSONUtils.getTextValue(agentCustomField);
    }

    public String getPhoneNumber(JSONArray customFields) throws JSONException {
        JSONObject phoneCustomField = JSONUtils.getCustomFieldById(customFields, paratureProperties.getPhone());
        return JSONUtils.getTextValue(phoneCustomField);
    }

    public String getPhoneEmail(JSONArray customFields) throws JSONException {
        JSONObject phoneEmailCustomField = JSONUtils.getCustomFieldById(customFields,
                paratureProperties.getPhoneEmail());
        return JSONUtils.getTextValue(phoneEmailCustomField);
    }

    public String getGuid(JSONArray customFields) throws JSONException {
        JSONObject guidField = JSONUtils.getCustomFieldById(customFields, paratureProperties.getCustomerId());
        return JSONUtils.getTextValue(guidField);
    }

    public String getCommunityFirstName(JSONArray customFields) throws JSONException {
        JSONObject guidField = JSONUtils.getCustomFieldById(customFields, paratureProperties.getCommunityFirstName());
        return JSONUtils.getTextValue(guidField);
    }

    public String getCommunityLastName(JSONArray customFields) throws JSONException {
        JSONObject guidField = JSONUtils.getCustomFieldById(customFields, paratureProperties.getCommunityLastName());
        return JSONUtils.getTextValue(guidField);
    }

    public String getFirstName(JSONObject customer) throws JSONException {
        JSONObject field = customer.getJSONObject("First_Name");
        return JSONUtils.getTextValue(field);
    }

    public String getEmail(JSONObject customer) throws JSONException {
        JSONObject field = customer.getJSONObject("Email");
        return JSONUtils.getTextValue(field);
    }

    public String getLastName(JSONObject customer) throws JSONException {
        JSONObject field = customer.getJSONObject("Last_Name");
        return JSONUtils.getTextValue(field);
    }

    public String getAddressLine1(JSONArray fields) throws JSONException {
        JSONObject field = JSONUtils.getCustomFieldById(fields, paratureProperties.getAddressLine1());
        return JSONUtils.getTextValue(field);
    }

    public String getAddressLine2(JSONArray fields) throws JSONException {
        JSONObject field = JSONUtils.getCustomFieldById(fields, paratureProperties.getAddressLine2());
        return JSONUtils.getTextValue(field);
    }

    public String getCity(JSONArray fields) throws JSONException {
        JSONObject field = JSONUtils.getCustomFieldById(fields, paratureProperties.getCity());
        return JSONUtils.getTextValue(field);
    }

    public String getProvince(JSONArray fields) throws JSONException {
        JSONObject field = JSONUtils.getCustomFieldById(fields, paratureProperties.getProvince());
        return JSONUtils.getTextValue(field);
    }

    public String getPostalCode(JSONArray fields) throws JSONException {
        JSONObject field = JSONUtils.getCustomFieldById(fields, paratureProperties.getPostalCode());
        return JSONUtils.getTextValue(field);
    }

    public String getState(JSONArray fields) throws JSONException {
        JSONObject field = JSONUtils.getCustomFieldById(fields, paratureProperties.getState());
        return JSONUtils.getSelectedOption(field);
    }

    public String getCountry(JSONArray fields) throws JSONException {
        JSONObject field = JSONUtils.getCustomFieldById(fields, paratureProperties.getCountry());
        return JSONUtils.getSelectedOption(field);
    }

    @Required
    public void setParatureProperties(ParatureProperties paratureProperties) {
        this.paratureProperties = paratureProperties;
    }

}
