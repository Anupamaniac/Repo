package com.rosettastone.succor.dao;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The {@code CustomerEntity} represents a customer object.
 */

@Entity
@Table(name = "OLAPDW.WC_LEARNER_CONTACT_INFO_MV")
public class CustomerEntity implements Serializable {
	public static final String LANG_FIELDNAME = "lang";
	public static final String LICENSE_GUID_FIELDNAME = "license_guid";
	private static final long serialVersionUID = -7830731668253643947L;
	// private String license_guid;
	private String learner_name;
	private String phone_num;
	private String address_title;
	private String st_address1;
	private String st_address2;
	private String city;
	private String state_code;
	private String country_name;
	private String postal_code;
	private String customer_email;
	// private String lang;
	private String lang_level;
	private String support_lang;

	private CustomerPK primaryKey;

	// @Id
	// public String getLicense_guid() {
	// return license_guid;
	// }
	//
	// public void setLicense_guid(String licenseGuid) {
	// license_guid = licenseGuid;
	// }

	@EmbeddedId
	public CustomerPK getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(CustomerPK primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getLearner_name() {
		return learner_name;
	}

	public void setLearner_name(String learnerName) {
		learner_name = learnerName;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phoneNum) {
		phone_num = phoneNum;
	}

	public String getAddress_title() {
		return address_title;
	}

	public void setAddress_title(String addressTitle) {
		address_title = addressTitle;
	}

	public String getSt_address1() {
		return st_address1;
	}

	public void setSt_address1(String stAddress1) {
		st_address1 = stAddress1;
	}

	public String getSt_address2() {
		return st_address2;
	}

	public void setSt_address2(String stAddress2) {
		st_address2 = stAddress2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState_code() {
		return state_code;
	}

	public void setState_code(String stateCode) {
		state_code = stateCode;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String countryName) {
		country_name = countryName;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postalCode) {
		postal_code = postalCode;
	}

	public String getCustomer_email() {
		return customer_email;
	}

	public void setCustomer_email(String customerEmail) {
		customer_email = customerEmail;
	}

	// public String getLang() {
	// return lang;
	// }
	//
	// public void setLang(String lang) {
	// this.lang = lang;
	// }

	public String getLang_level() {
		return lang_level;
	}

	public void setLang_level(String langLevel) {
		lang_level = langLevel;
	}

	public String getSupport_lang() {
		return support_lang;
	}

	public void setSupport_lang(String supportLang) {
		support_lang = supportLang;
	}

}
