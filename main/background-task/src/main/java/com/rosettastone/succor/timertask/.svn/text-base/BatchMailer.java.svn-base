package com.rosettastone.succor.timertask;

import java.util.Date;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class BatchMailer {

    private static final Log LOG = LogFactory.getLog(SendPostalBatchTimer.class);

    private MessageSource messageSource;
    private String mailTo;
    private JavaMailSender mailSender;
    private String mailFrom;

    public void sendEmail(byte[] csvFile, Locale locale) throws MessagingException {
        LOG.debug("Send postal batch to " + locale);
        MimeMessage message = mailSender.createMimeMessage();

        boolean isMultipart = true;
        MimeMessageHelper helper = new MimeMessageHelper(message, isMultipart);
        helper.setTo(mailTo);
        helper.setFrom(mailFrom);
        helper.setSubject(messageSource.getMessage("postal_history.subject", new Object[]{}, locale));
        
        Date mailDate = new Date();
        
        Object[] bodyParams = new Object[]{DateFormatUtils.format(mailDate, "MM/dd/yyyy", Locale.ENGLISH)};
        String body = messageSource.getMessage("postal_history.body", bodyParams, locale);
        boolean isHtml = false;
        helper.setText(body, isHtml);
        
        ByteArrayResource resource = new ByteArrayResource(csvFile);        
        String filename = "postal-" + DateFormatUtils.format(mailDate, "MMddyyyy", Locale.ENGLISH) + ".csv";
        helper.addAttachment(filename, resource);
        
        mailSender.send(message);
    }

    @Required
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Required
    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    @Required
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Required
    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }
}
