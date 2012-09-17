package com.rosettastone.succor.web.servlets;

import com.rosettastone.succor.web.TemplateHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Special controller for email template preview functionality.
 * Flex can't show complex html pages.
 * We send html page into the server and save in session.
 * After that we create iframe with link to this controller.
 * Controller gets html content from session and put to request.
 */
@Controller
public class PreviewController {

    private static final Log log = LogFactory.getLog(PreviewController.class);

    /**
     * Session bean for storing html content
     */
    private TemplateHolder templateHolder;


    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public void preview(HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        log.debug("Get preview content from session");
        String text = "";
        if (templateHolder.getHtml() != null) {
            text = templateHolder.getHtml();
        }
        response.getWriter().write(text);
        response.getWriter().flush();
    }

    @Required
    public void setTemplateHolder(TemplateHolder templateHolder) {
        this.templateHolder = templateHolder;
    }
}
