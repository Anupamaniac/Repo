package com.rosettastone.succor.service.sms;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * User: Nikolay Sazonov
 * Date: 5/10/11
 * Model object for SMS API.
 */
public class SMSRequestVO implements Serializable {

    @NotNull
    private String locale;

    @NotNull
    private String phone;

    @NotNull
    private String text;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
