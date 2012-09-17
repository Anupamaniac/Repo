package com.rosettastone.succor.parature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * The {@code ParatureErrorMessageBuilder} allows to build error message while working with parature.
 */

public final class ParatureErrorMessageBuilder {

    private static final Log LOG = LogFactory.getLog(ParatureErrorMessageBuilder.class);

    private ParatureErrorMessageBuilder() { }

    /**
     * {@code buildException}
     *
     * @param message
     * @param url
     * @param response
     * @return string
     */
    public static String buildException(String message, String url, String response) {
        return buildException(message, url, null, response);
    }

    /**
     * {@code buildException}
     *
     * @param message
     * @param url
     * @param bodyStream
     * @param response
     * @return string
     */
    public static String buildException(String message, String url, InputStream bodyStream, String response) {
        StringBuilder builder = new StringBuilder("Parature exception occured: " + message + "\n");
        builder.append("API URL: " + url + "\n");
        if (bodyStream != null) {
            try {
                bodyStream.reset();
                builder.append("Data Sent:\n" + convertStreamToString(bodyStream) + "\n");
            } catch (IOException ex) {
                LOG.warn("Can not convert request body", ex);
                builder.append("Data Sent is unavailable\n");
            }
        } else {
            builder.append("No Data Sent\n");
        }
        if (response != null) {
            builder.append("Data Received:\n" + response + "\n");
        } else {
            builder.append("No Data Received\n");
        }
        builder.append("-------------------------------------------------------------------");
        return builder.toString();
   }

    private static String convertStreamToString(InputStream is) throws IOException {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine() method. We iterate until the
         * BufferedReader return null which means there's no more data to read. Each line will appended to a
         * StringBuilder and returned as String.
         */
        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                is.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }

}
