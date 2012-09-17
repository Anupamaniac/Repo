package com.rosettastone.succor.dao;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * THe {@code CustomerPK} represents object for generate criteria loading of customers entities from db.
 *
 @see CustomerDAO
 */

@Embeddable
public class CustomerPK implements Serializable {
	private static final long serialVersionUID = 6915928897891551252L;

	private String license_guid;
	private String lang;

	public CustomerPK() {

	}

	public String getLicense_guid() {
		return license_guid;
	}

	public void setLicense_guid(String license_guid) {
		this.license_guid = license_guid;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public int hashCode() {
		return license_guid.hashCode() + lang.hashCode();
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof CustomerPK)) {
			return false;
		}
		CustomerPK pk = (CustomerPK) obj;
		return pk.license_guid.equals(license_guid) && pk.lang.equals(lang);
	}
}
