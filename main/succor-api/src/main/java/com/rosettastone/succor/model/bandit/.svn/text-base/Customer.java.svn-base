package com.rosettastone.succor.model.bandit;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The {@code Customer} is a domain object for representation of customer.
 * 
 * @see Event
 */

public class Customer {

    private String guid;
    private String firstName;
    private String lastName;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String stateProvince;
    private String postalCode;
    private String countryIso;

    private String email;

    private String contactPhoneNumber;
    private String contactPhoneCountryCode;
    private String contactPhoneEmail;
    private Boolean institutional;
    private Locale supportLanguageIso;
    private Locale lastViewedLocale;
    private String timeZone;

    // TODO it's for DB and Parature sync
    private String addressTitle;
    private List<String> productNames = new ArrayList<String>();
    private boolean contact = Boolean.TRUE;
    private Long paratureId;

    private boolean kid;
    private boolean parent;
    private String parentGUID;
    private String parentFirstName;
    private String parentLastName;

    private String communityFirstName;
    private String communityLastName;

    private Long csrId;
    private String dedicatedAgent;

    @JsonField
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @JsonField("first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonField("last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddressTitle() {
        return addressTitle;
    }

    public void setAddressTitle(String addressTitle) {
        this.addressTitle = addressTitle;
    }

    @JsonField("address_line_1")
    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @JsonField("address_line_2")
    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    @JsonField()
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @JsonField("state_province")
    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    @JsonField("postal_code")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @JsonField("country_iso")
    public String getCountryIso() {
        return countryIso;
    }

    public void setCountryIso(String countryIso) {
        this.countryIso = countryIso;
    }

    @JsonField("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonField("contact_phone_number")
    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    @JsonField("contact_phone_country_code")
    public String getContactPhoneCountryCode() {
        return contactPhoneCountryCode;
    }

    public void setContactPhoneCountryCode(String contactPhoneCountryCode) {
        this.contactPhoneCountryCode = contactPhoneCountryCode;
    }

    @JsonField
    public Boolean getInstitutional() {
        return institutional;
    }

    public void setInstitutional(Boolean institutional) {
        this.institutional = institutional;
    }

    @JsonField("time_zone")
    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }

    public boolean getContact() {
        return contact;
    }

    public void setContact(boolean contact) {
        this.contact = contact;
    }

    public Long getParatureId() {
        return paratureId;
    }

    public void setParatureId(Long paratureId) {
        this.paratureId = paratureId;
    }

    public boolean isKid() {
        return kid;
    }

    public void setKid(boolean kid) {
        this.kid = kid;
    }

    public boolean isParent() {
        return parent;
    }

    public void setParent(boolean parent) {
        this.parent = parent;
    }

    @JsonField("parent_guid")
    public String getParentGUID() {
        return parentGUID;
    }

    public void setParentGUID(String parentGUID) {
        this.parentGUID = parentGUID;
    }

    public String getParentFirstName() {
        return parentFirstName;
    }

    public void setParentFirstName(String parentFirstName) {
        this.parentFirstName = parentFirstName;
    }

    public String getParentLastName() {
        return parentLastName;
    }

    public void setParentLastName(String parentLastName) {
        this.parentLastName = parentLastName;
    }

    @JsonField("contact_phone_email")
    public String getContactPhoneEmail() {
        return contactPhoneEmail;
    }

    public void setContactPhoneEmail(String contactPhoneEmail) {
        this.contactPhoneEmail = contactPhoneEmail;
    }

    @JsonField("support_language_iso")
    public Locale getSupportLanguageIso() {
        return supportLanguageIso;
    }

    public void setSupportLanguageIso(Locale supportLanguageIso) {
        this.supportLanguageIso = supportLanguageIso;
    }

    @JsonField("last_viewed_locale")
    public Locale getLastViewedLocale() {
        return lastViewedLocale;
    }

    public void setLastViewedLocale(Locale lastViewedLocale) {
        this.lastViewedLocale = lastViewedLocale;
    }

    public String getCommunityFirstName() {
        return communityFirstName;
    }

    public void setCommunityFirstName(String communityFirstName) {
        this.communityFirstName = communityFirstName;
    }

    public String getCommunityLastName() {
        return communityLastName;
    }

    public void setCommunityLastName(String communityLastName) {
        this.communityLastName = communityLastName;
    }

    public Long getCsrId() {
        return csrId;
    }

    public void setCsrId(Long csrId) {
        this.csrId = csrId;
    }

    public String getDedicatedAgent() {
        return dedicatedAgent;
    }

    public void setDedicatedAgent(String dedicatedAgent) {
        this.dedicatedAgent = dedicatedAgent;
    }

}
