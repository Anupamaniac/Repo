package com.rosettastone.succor.utils;

import org.apache.http.client.HttpResponseException;

/**
 * The {@code UnicodeHandlerHttpResponseException} helps to to parse exception rom parature.
 *
 */
public class UnicodeHandlerHttpResponseException extends HttpResponseException {

    private String response;

    /**
     * Constructs an unicodeHandlerHttpResponseException, initialize the {@code response}.
     *
     * @param statusCode
     * @param message
     * @param response
     */
    public UnicodeHandlerHttpResponseException(int statusCode, String message, String response) {
        super(statusCode, message);
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

}
