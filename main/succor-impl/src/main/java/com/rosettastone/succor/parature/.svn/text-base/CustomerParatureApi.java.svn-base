package com.rosettastone.succor.parature;

import java.util.Locale;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.StringUtils;

import com.rosettastone.succor.exception.ApplicationException;
import com.rosettastone.succor.exception.ParatureErrorCode;
import com.rosettastone.succor.exception.ParatureException;
import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.utils.CustomerJSONParser;
import com.rosettastone.succor.utils.format.FormatUtils;

/**
 * The {@code CustomerParatureApi} provides the functionality for customer's
 * synchronization with parature.
 * 
 * @author Konstantin Zhirov
 * 
 */
public class CustomerParatureApi {

    private static final String START_URL = "/Customer?_output_=json&";

    private static final String CSR_URL = "/Csr?_output_=json&Email=";

    private ParatureProperties paratureProperties;
    private ParatureHttpClient paratureHttpClient;
    private CustomerJSONParser customerJsonParser;

    /**
     * Synchronizes customer with parature.
     * 
     * @param customer
     *            it will be synchronized
     * @param supportLocale
     */
    public void synchronizeWithParature(Customer customer, Locale supportLocale) {
        if (StringUtils.hasLength(customer.getGuid()) || customer.getEmail() != null) {
            findAndParseCustomer(customer, supportLocale);
        } else {
            throw new ApplicationException("Customer has no GUID and Email");
        }
    }

    /**
     * Look up user in Parature and parse the result.
     * 
     * @param customer
     * @param supportLocale
     */
    private void findAndParseCustomer(Customer customer, Locale supportLocale) {
        String url = buildURL(customer, supportLocale);
        String response = paratureHttpClient.sendGetRequest(url);
        parseResponseAndAssignValues(response, url, customer, supportLocale);
    }

    /**
     * Parse response and assign values to customer.
     * 
     * @param response
     * @param url
     * @param customer
     */
    private void parseResponseAndAssignValues(String response, String url, Customer customer, Locale supportLocale) {
        String guid = customer.getGuid();
        String errorMessage = getCustomerNotFoundErrorMessage(url, guid, response);

        try {
            JSONObject customerJSON = customerJsonParser.parseCustomerJSON(response);

            Long paratureId = customerJSON.getLong("@id");
            if (paratureId == 0) {
                throw new ParatureException(errorMessage, ParatureErrorCode.NOT_FOUND_404_CUSTOMER, guid);
            }

            customer.setParatureId(paratureId);

            JSONArray customFields = customerJSON.getJSONArray("Custom_Field");

            customer.setGuid(customerJsonParser.getGuid(customFields));

            String agent = customerJsonParser.getDedicatedAgent(customFields);
            customer.setDedicatedAgent(agent);

            boolean contact = customerJsonParser.parseIsContactCustomer(customFields);
            customer.setContact(contact);

            String phoneNumber = customerJsonParser.getMobileNumber(customFields);
            if (phoneNumber == null || phoneNumber.trim().length() == 0 || !FormatUtils.isPhoneValid(phoneNumber)) {
                phoneNumber = customerJsonParser.getPhoneNumber(customFields);
            }
            customer.setContactPhoneNumber(phoneNumber);

            if (Locale.JAPANESE.equals(supportLocale)) {
                customer.setContactPhoneEmail(customerJsonParser.getPhoneEmail(customFields));
            }

            String firstName = customerJsonParser.getFirstName(customerJSON);
            customer.setFirstName(firstName);

            String lastName = customerJsonParser.getLastName(customerJSON);
            customer.setLastName(lastName);

            String communityFirstName = customerJsonParser.getCommunityFirstName(customFields);
            if (communityFirstName == null || communityFirstName.trim().length() == 0) {
                communityFirstName = firstName;
            }
            customer.setCommunityFirstName(communityFirstName);

            String communityLastName = customerJsonParser.getCommunityLastName(customFields);
            if (communityLastName == null || communityLastName.trim().length() == 0) {
                communityLastName = lastName;
            }
            customer.setCommunityLastName(communityLastName);

            if (customer.getAddressLine1() == null || !StringUtils.hasLength(customer.getAddressLine1())) {
                populateAddress(customer, customFields);
            }

            if (customer.isParent()) {
                customer.setEmail(customerJsonParser.getEmail(customerJSON));
            }

            if (customer.isKid()) {
                synchronizeKid(customer, supportLocale);
            }

        } catch (JSONException e) {
            errorMessage = ParatureErrorMessageBuilder.buildException("Error parsing JSON", url, response);
            throw new ApplicationException(errorMessage, e);
        } catch (ParatureException e) {
            throw e;
        } catch (NullPointerException e) {
            throw new ParatureException("Customer with guid '" + guid + "' not found",
                    ParatureErrorCode.NOT_FOUND_404_CUSTOMER, guid);
        }
    }

    /**
     * Populate customer address from Parature
     * 
     * @param customer
     * @param customFields
     * @throws JSONException
     */
    private void populateAddress(Customer customer, JSONArray customFields) throws JSONException {
        customer.setAddressLine1(customerJsonParser.getAddressLine1(customFields));
        customer.setAddressLine2(customerJsonParser.getAddressLine2(customFields));
        customer.setCity(customerJsonParser.getCity(customFields));
        // either Province or State will be populated, depending on country
        customer.setStateProvince(customerJsonParser.getProvince(customFields)
                + customerJsonParser.getState(customFields));
        customer.setPostalCode(customerJsonParser.getPostalCode(customFields));
        // actually country name, not ISO
        customer.setCountryIso(customerJsonParser.getCountry(customFields));
    }

    /**
     * Synchronize parent's name from Parature.
     * 
     * @param kid
     * @param supportLocale
     */
    private void synchronizeKid(Customer kid, Locale supportLocale) {
        if (!StringUtils.hasLength(kid.getParentGUID())) {
            return;
        }

        Customer parent = new Customer();
        parent.setGuid(kid.getParentGUID());
        parent.setParent(true);
        synchronizeWithParature(parent, supportLocale);

        kid.setContactPhoneNumber(parent.getContactPhoneNumber());
        kid.setEmail(parent.getEmail());
        kid.setParentFirstName(parent.getFirstName());
        kid.setParentLastName(parent.getLastName());

        populateKidAddress(kid, parent);
    }

    /**
     * Populate kids address from Parents address.
     * 
     * @param kid
     * @param parent
     */
    private void populateKidAddress(Customer kid, Customer parent) {
        kid.setAddressLine1(parent.getAddressLine1());
        kid.setAddressLine2(parent.getAddressLine2());
        kid.setCity(parent.getCity());
        kid.setStateProvince(parent.getStateProvince());
        kid.setPostalCode(parent.getPostalCode());
        kid.setCountryIso(parent.getCountryIso());
    }

    /**
     * Build and return customer error message.
     * 
     * @param url
     * @param guid
     * @param response
     * @return
     */
    private String getCustomerNotFoundErrorMessage(String url, String guid, String response) {
        String errorMessage = ParatureErrorMessageBuilder.buildException("No customer with licenseID " + guid,
                paratureHttpClient.generateUrl(url), response);
        return errorMessage;
    }

    /**
     * Build request url based on customer's guid.
     * 
     * 
     * @param supportLocale
     * @return
     */
    private String buildURL(Customer customer, Locale supportLocale) {
        String customFields = paratureProperties.getMethodOfContact() + "," + paratureProperties.getPhone() + ","
                + paratureProperties.getCustomerId() + "," + paratureProperties.getMobile() + ","
                + paratureProperties.getCommunityFirstName() + "," + paratureProperties.getCommunityLastName() + ","
                + paratureProperties.getCustomerDedicatedAgent() + "," + paratureProperties.getAddressLine1() + ","
                + paratureProperties.getAddressLine2() + "," + paratureProperties.getCity() + ","
                + paratureProperties.getState() + "," + paratureProperties.getProvince() + ","
                + paratureProperties.getCountry();

        if (supportLocale != null && supportLocale.toString().equals("ja")) {
            customFields += "," + paratureProperties.getPhoneEmail();
        }
        String url = null;
        if (StringUtils.hasLength(customer.getGuid())) {
            // for normal events find customer by GUID
            url = String.format("%s%s=%s&_fields_=%s", START_URL, paratureProperties.getCustomerIDField(),
                    customer.getGuid(), customFields);
        } else {
            // for DiscoveryCall find customer by email
            url = String.format("%s%s=%s&_fields_=%s", START_URL, "Email", customer.getEmail(), customFields);
        }
        if (customer.isKid()) {
            url += "," + paratureProperties.getMasterGuid();
        }
        return url;
    }

    @Required
    public void setParatureProperties(ParatureProperties paratureProperties) {
        this.paratureProperties = paratureProperties;
    }

    @Required
    public void setCustomerJsonParser(CustomerJSONParser customerJsonParser) {
        this.customerJsonParser = customerJsonParser;
    }

    @Required
    public void setParatureHttpClient(ParatureHttpClient paratureHttpClient) {
        this.paratureHttpClient = paratureHttpClient;
    }
}
