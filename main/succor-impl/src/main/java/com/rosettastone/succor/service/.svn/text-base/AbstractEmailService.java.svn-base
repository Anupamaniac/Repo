package com.rosettastone.succor.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.parature.TicketParatureApi;
import com.rosettastone.succor.timertask.dao.ReportDao;
import com.rosettastone.succor.timertask.model.ReportEntityType;
import com.rosettastone.succor.utils.EmailBodyGenerator;
import com.rosettastone.succor.utils.LocalizedMessageSource;
import com.rosettastone.succor.web.dao.TemplateDao;
import com.rosettastone.succor.web.model.Template;

/**
 * Base class for email services.
 * 
 * User: Nikolay Sazonov Date: 5/19/11
 */
public abstract class AbstractEmailService {

    public static final String SUBJECT_PREFIX = "mail.subject.";
    public static final String MAIL_FROM = "mail.from";

    private JavaMailSender mailSender;
    private ReportDao reportDao;
    private TemplateDao templateDao;
    private EmailBodyGenerator emailBodyGenerator;

    private static final Log LOG = LogFactory.getLog(TicketParatureApi.class);

    public abstract Template.Type getTemplateType();

    public abstract ReportEntityType getReportEntityType();

    public abstract String getToEmail(Customer customer);

    /**
     * Sends email populated with data from the event.
     * 
     * @param event
     *            will be used by {@code populateMessage} method.
     * @throws MessagingException
     */
    public void sendEmail(Event event) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        if (populateMessage(message, event)) {
            LOG.debug("sending email");
            mailSender.send(message);
            LOG.debug("sending email done");
            reportDao.createNewEntity(getReportEntityType());
        }
    }

    /**
     * Populate message with data from the event.
     * 
     * @param message
     *            will be populated
     * @param event
     *            will be parsed
     * @return True if population was successful.
     * @throws MessagingException
     */
    private boolean populateMessage(MimeMessage message, Event event) throws MessagingException {

        Customer customer = event.getCustomer();

        Template template = templateDao.load(event.getInternalSuccorData().getRuleId(), getTemplateType(),
                customer.isKid());

        if (template == null) {
            LOG.info("Template does not exist");
            return false;
            // throw new MessagingException("Template is null");
        }

        String body = emailBodyGenerator.generateEmailBody(event, template.getBody());
        String subject = template.getSubject();

        MimeMessageHelper helper = new MimeMessageHelper(message);

        String email = getToEmail(customer);
        helper.setTo(email);

        boolean isHtml = true;
        helper.setText(body, isHtml);
        helper.setSubject(subject);
        if (template.getSender() == null) {
            LOG.info("From field is requred in the email template");
            return false;
        }
        helper.setFrom(template.getSender());
        return true;
    }

    /**
     * Return localized subject using action key and properties files.
     * 
     * @param event
     *            - current event
     * @param localizedMessageSource
     * @return localized email subject
     */
    public static String getSubject(Event event, LocalizedMessageSource localizedMessageSource) {
        String key = SUBJECT_PREFIX + event.getInternalSuccorData().getActionAsKey();
        String subject = localizedMessageSource.getMessage(key);
        return subject;
    }

    @Required
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Required
    public void setReportDao(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    @Required
    public void setTemplateDao(TemplateDao templateDao) {
        this.templateDao = templateDao;
    }

    @Required
    public void setEmailBodyGenerator(EmailBodyGenerator emailBodyGenerator) {
        this.emailBodyGenerator = emailBodyGenerator;
    }
}
