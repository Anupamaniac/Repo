package com.rosettastone.succor.web;

import java.io.Serializable;

/**
 * Session bean for storing html content
 * @see com.rosettastone.succor.web.servlets.PreviewController
 */

public class TemplateHolder implements Serializable {

    private String html;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
