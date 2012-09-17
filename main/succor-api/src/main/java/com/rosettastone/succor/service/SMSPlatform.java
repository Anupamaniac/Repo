package com.rosettastone.succor.service;

import java.util.Locale;

import com.rosettastone.succor.exception.SMSException;

/**
 * User: Nikolay Sazonov
 * Date: 5/3/11
 *
 * Base API for sending SMS
 */

public interface SMSPlatform {

    /**
     * Send SMS message
     *
     * @param phoneNumber - mobile phone number
     * @param message - text
     * @return returns unique message id
     */
    String sendMessage(String phoneNumber, String message, Locale locale) throws SMSException;

}
