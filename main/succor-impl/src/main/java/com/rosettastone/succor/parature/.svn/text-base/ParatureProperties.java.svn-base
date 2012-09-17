package com.rosettastone.succor.parature;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Required;

import com.rosettastone.succor.exception.ApplicationException;
import com.rosettastone.succor.model.bandit.Event;

/**
 * The {@code ParatureProperties} allows to get parature properties.
 */
public final class ParatureProperties {

    private Locale defaultLocale;
    private Map<Locale, Properties> propertiesMap;
    private ThreadLocal<Event> currentEventStorage;

    /**
     * Return value from map corresponding to the key.
     * 
     * @param key
     *            key for search
     * @return string value
     */
    public String get(String key) {
        Event event = currentEventStorage.get();
        if (event == null) {
            throw new ApplicationException("ThreadLocal<Event> currentEventStorage is not initialized, "
                    + "event is null");
        }
        Properties properties = propertiesMap.get(event.getInternalSuccorData().getSupportLocale());
        if (properties == null) {
            properties = propertiesMap.get(defaultLocale);
        }
        return properties.getProperty(key);
    }

    public String getTypeField() {
        return get("parature.field.type");
    }

    public String getSuccorType() {
        return get("parature.field.type.super");
    }

    public String getCustomerId() {
        return get("parature.field.customerID");
    }

    public String getMethodOfContact() {
        return get("parature.field.customer.method_of_contact");
    }

    public String getDoNotContact() {
        return get("parature.field.customer.method_of_contact.doNotContact");
    }

    public String getLanguage() {
        return get("parature.field.language");
    }

    public String getMobile() {
        return get("parature.field.customer.mobile");
    }

    public String getPhone() {
        return get("parature.field.customer.phone");
    }

    public String getPhoneEmail() {
        return get("parature.field.customer.phoneEmail");
    }

    public String getMasterGuid() {
        return get("parature.field.masterGUID");
    }

    public String getTicketTypeField() {
        return "FID" + getTypeField();
    }

    public String getLanguageField() {
        return "FID" + getLanguage();
    }

    public String getCustomerIDField() {
        return "FID" + getCustomerId();
    }

    public String getAccountId() {
        return get("parature.accountId");
    }

    public String getDepartmentId() {
        return get("parature.departmentId");
    }

    public String getBaseUrl() {
        return get("parature.baseUrl");
    }

    public String getAuthToken() {
        return get("parature.authToken");
    }

    public String getCommunityFirstName() {
        return get("parature.field.customer.community_first_name");
    }

    public String getCommunityLastName() {
        return get("parature.field.customer.community_last_name");
    }

    public String getCustomerDedicatedAgent() {
        return get("parature.field.customer.dedicated_agent");
    }

    @Required
    public void setDefaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    @Required
    public void setPropertiesMap(Map<Locale, Properties> propertiesMap) {
        this.propertiesMap = propertiesMap;
    }

    @Required
    public void setCurrentEventStorage(ThreadLocal<Event> currentEventStorage) {
        this.currentEventStorage = currentEventStorage;
    }

    public String getReAssignTicket() {
        return get("parature.action.type.reassign_ticket");
    }

    public String getAddressLine1() {
        return get("parature.field.customer.address_line_1");
    }

    public String getAddressLine2() {
        return get("parature.field.customer.address_line_2");
    }

    public String getCity() {
        return get("parature.field.customer.city");
    }

    public String getState() {
        return get("parature.field.customer.state");
    }

    public String getProvince() {
        return get("parature.field.customer.province");
    }

    public String getPostalCode() {
        return get("parature.field.customer.postal_code");
    }

    public String getCountry() {
        return get("parature.field.customer.country");
    }

}
