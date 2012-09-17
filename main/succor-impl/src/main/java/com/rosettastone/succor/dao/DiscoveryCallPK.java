package com.rosettastone.succor.dao;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Th {@code DiscoveryCallPK} represents object for generate criteria for loading of discoveryCall entities.
 */

@Embeddable
public class DiscoveryCallPK implements Serializable {

    private static final long serialVersionUID = -2351338878464249606L;

    @Column(name = "email")
    private String email;

    @Column(name = "lang")
    private String language;

    @Column(name = "lang_level")
    private String languageLevel;

    @Column(name = "ordered_on_dt_wid")
    private Long orderedOn;

    /**
     * Language code is support language in format en-US, en-GB
     */
    @Column(name = "language_code")
    private String languageCode;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageLevel() {
        return languageLevel;
    }

    public void setLanguageLevel(String languageLevel) {
        this.languageLevel = languageLevel;
    }

    public Long getOrderedOn() {
        return orderedOn;
    }

    public void setOrderedOn(Long orderedOn) {
        this.orderedOn = orderedOn;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiscoveryCallPK that = (DiscoveryCallPK) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        if (languageCode != null ? !languageCode.equals(that.languageCode) : that.languageCode != null) return false;
        if (languageLevel != null ? !languageLevel.equals(that.languageLevel) : that.languageLevel != null)
            return false;
        if (orderedOn != null ? !orderedOn.equals(that.orderedOn) : that.orderedOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (languageLevel != null ? languageLevel.hashCode() : 0);
        result = 31 * result + (orderedOn != null ? orderedOn.hashCode() : 0);
        result = 31 * result + (languageCode != null ? languageCode.hashCode() : 0);
        return result;
    }
}
