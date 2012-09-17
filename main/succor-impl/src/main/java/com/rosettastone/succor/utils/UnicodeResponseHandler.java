package com.rosettastone.succor.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

/**
 * The {@code UnicodeResponseHandler} allows to handle UnicodeHandlerHttpResponseException.
 */
public class UnicodeResponseHandler implements ResponseHandler<String> {

    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * Handles response.
     *
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    public String handleResponse(HttpResponse response) throws IOException {
        StatusLine statusLine = response.getStatusLine();

        HttpEntity entity = response.getEntity();
        String content;
        if (entity != null) {
            content = EntityUtils.toString(entity, DEFAULT_CHARSET);
            content = fixBomIssue(content);
        } else {
            content = "";
        }

        if (statusLine.getStatusCode() >= HttpStatus.SC_MULTIPLE_CHOICES) {
            throw new UnicodeHandlerHttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase(),
                    content);
        }

        return content;
    }

    private String fixBomIssue(String content) {
        //Ugly, but it works.
        return content.substring(1);
    }
}
