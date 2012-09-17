package com.rosettastone.succor.service;

import java.util.Locale;

import com.rosettastone.succor.exception.SMSException;
import com.rosettastone.succor.timertask.dao.ReportDao;
import com.rosettastone.succor.timertask.model.ReportEntityType;
import com.rosettastone.succor.utils.TaskPriorityService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;

import com.rosettastone.succor.backgroundtask.TaskManager;
import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.service.sms.SMSSender;
import com.rosettastone.succor.utils.EmailBodyGenerator;
import com.rosettastone.succor.web.dao.TemplateDao;
import com.rosettastone.succor.web.model.Template;

/**
 * User: Nikolay Sazonov Date: 5/18/11 Service for sending SMS messages.
 */
public class SMSService {

    private static final Log LOG = LogFactory.getLog(SMSService.class);

    private TemplateDao templateDao;

    private EmailBodyGenerator emailBodyGenerator;

    private SMSSender smsSender;

    private TaskManager taskManager;

    private ReportDao reportDao;

    private TaskPriorityService priorityService;
    /**
     * Send email to Japanese user or SMS to other
     * 
     * @param event
     * @throws SMSException
     */
    public void sendSMS(Event event) throws SMSException {
        Customer customer = event.getCustomer();
        if (event.getInternalSuccorData().getSupportLocale().equals(Locale.JAPANESE)) {
        	throw new NullPointerException("Phone Email Address doesn't exist"); /*Parature dereferencing*/
            /*LOG.debug("sending sms email");
            // create task for sending email
            Task emailTask = new Task();
            emailTask.setBeanName("smsEmailService");
            emailTask.setMethodName("sendEmail");
            emailTask.setRawArguments(new Object[] { event });
            emailTask.setPriority(priorityService.getPriorityForMessageType(
                event.getHeader().getMessageType().toString()));
            taskManager.createEmailTask(emailTask);
            LOG.debug("sending sms email done");*/
        } else {
            LOG.debug("sending sms");
            Template template = templateDao.load(event.getInternalSuccorData().getRuleId(), Template.Type.SMS, false);

            if (template == null || template.getBody() == null || template.getSender() == null) {
                LOG.info("Template does not exist");
                throw new NullPointerException("SMS template doesn't exist");
            }
            String body = emailBodyGenerator.generateEmailBody(event, template.getBody());
            // send SMS message
            smsSender.sendMessage(event.getCustomer().getContactPhoneNumber(), body, event.getInternalSuccorData().getSupportLocale());
            reportDao.createNewEntity(ReportEntityType.SMS_CUSTOMER);
            LOG.debug("sending sms done");
        }
    }

    @Required
    public void setTemplateDao(TemplateDao templateDao) {
        this.templateDao = templateDao;
    }

    @Required
    public void setEmailBodyGenerator(EmailBodyGenerator emailBodyGenerator) {
        this.emailBodyGenerator = emailBodyGenerator;
    }

    @Required
    public void setSmsSender(SMSSender smsSender) {
        this.smsSender = smsSender;
    }

    @Required
    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Required
    public void setReportDao(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    @Required
    public void setPriorityService(TaskPriorityService priorityService) {
        this.priorityService = priorityService;
    }
}
