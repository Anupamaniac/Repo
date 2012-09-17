package com.rosettastone.succor.service;

import java.util.Locale;

import com.rosettastone.succor.exception.ApplicationException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.commons.json.JSONObject;
import org.springframework.beans.factory.annotation.Required;

import com.rosettastone.succor.backgroundtask.TaskManager;
import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.model.parature.TicketType;
import com.rosettastone.succor.parature.TicketParatureApi;
import com.rosettastone.succor.utils.TaskPriorityService;
import com.rosettastone.succor.utils.TicketJSONParser;

/**
 * The {@code EventServiceImpl} implements actions for event.
 */

public class EventServiceImpl implements EventService {

    private static final Log LOG = LogFactory.getLog(EventServiceImpl.class);

    private TaskManager taskManager;
    private TicketParatureApi ticketParatureApi;
    private PostalHistoryService postalHistoryService;
    private CustomerSynchronizationService customerSynchronizationService;

    private ThreadLocal<Exception> currentRuleExceptionStorage;
    private TaskPriorityService priorityService;

    /**
     * Creates "sendEmail" task.
     * 
     * @param event
     * 
     @see {@code logEvent}
     */
    @Override
    public void sendEmail(Event event) {
        if (!isContact(event)) {
            return;
        }

        Task emailTask = new Task();
        emailTask.setBeanName("emailService");
        emailTask.setMethodName("sendEmail");
        emailTask.setRawArguments(new Object[] { event });
        emailTask.setPriority(priorityService.getPriorityForMessageType(event.getHeader().getMessageType().toString()));
        taskManager.createEmailTask(emailTask);

        if (event.getInternalSuccorData().getUpdateSuperTicket()) {
           // logEvent(event, TicketType.EMAIL);
        }
    }

    /**
     * Creates "log to Parature" task.
     * 
     * @param event
     * @param ticketType
     */
    private void logEvent(Event event, TicketType ticketType) {
        Task paratureTask = new Task();
        paratureTask.setBeanName("ticketParatureApi");
        paratureTask.setMethodName("logEvent");
        paratureTask.setRawArguments(new Object[] { event, ticketType });
        paratureTask.setPriority(priorityService.getPriorityForMessageType(event.getHeader().getMessageType()
                .toString()));
        taskManager.createParatureTask(paratureTask);
    }

    /**
     * Creates "phone" ticket.
     * 
     * @param event
     * @see {@code createTicket}
     */
    @Override
    public void createPhoneTicket(Event event) {
        if (!isContact(event)) {
            return;
        }
        if (event.getInternalSuccorData().getPhoneValid()) {
            createTicket(event, TicketType.PHONE);
        }
    }

    /**
     * Creates "phone" ticket if {@code isContact} returns True.
     * 
     * @param event
     * @see {@code createTicket}
     */
    @Override
    public void createPhoneTicketDontCheckPhone(Event event) {
        if (!isContact(event)) {
            return;
        }
        createTicket(event, TicketType.PHONE);
    }

    /**
     * Creates "phone" ticket or "sendEmail" task.
     * 
     * @param event
     * @see {@code createTicket} method
     * @see {@code sendEmail} method
     */
    @Override
    public void createPhoneTicketOrSendEmail(Event event) {
        if (!isContact(event)) {
            return;
        }
        if (event.getInternalSuccorData().getPhoneValid()) {
            createTicket(event, TicketType.PHONE);
        } else {
            sendEmail(event);
        }
    }

    /**
     * Creates "ticketParatureApi" task.
     * 
     * @param event
     * @param ticketType
     * @see com.rosettastone.succor.parature.TicketParatureApi
     */
    private void createTicket(Event event, TicketType ticketType) {
        LOG.debug("create ticket " + ticketType);
        Task paratureTask = new Task();
        paratureTask.setBeanName("ticketParatureApi");
        paratureTask.setMethodName("createTicket");
        paratureTask.setRawArguments(new Object[] { event, ticketType });
        paratureTask.setPriority(priorityService.getPriorityForMessageType(event.getHeader().getMessageType()
                .toString()));
        taskManager.createParatureTask(paratureTask);

        // Log in Super ticket
        // In some rules we create ticket, but we do not need to log it in Super
        // Ticket
        LOG.debug("Need to update super ticket " + event.getInternalSuccorData().getUpdateSuperTicket());
        if (event.getInternalSuccorData().getUpdateSuperTicket()) {
          //  logEvent(event, ticketType);
        }

    }

    /**
     * Creates postal entity.
     * 
     * @param event
     * @see {@code logEvent}
     */
    @Override
    public void sendPostalMail(Event event) {
        if (!isContact(event)) {
            return;
        }

        if (event.getCustomer().getAddressLine1() == null || StringUtils.isEmpty(event.getCustomer().getAddressLine1())) {
            LOG.debug("Customer " + event.getCustomer().getGuid() + " has no address");
            return;
        }

        postalHistoryService.createPostalEntry(event);
        // TODO check if we need to update?
        //logEvent(event, TicketType.POSTAL);
    }

    /**
     * Creates "sendSMS" task.
     * 
     * @param event
     * @see SMSService
     */
    @Override
    public void sendSMS(Event event) {
        if (!isContact(event)) {
            return;
        }

        if (StringUtils.isBlank(event.getCustomer().getContactPhoneNumber())
                && !event.getInternalSuccorData().getSupportLocale().equals(Locale.JAPANESE)) {
            LOG.debug("sending sms: fail, number '" + event.getCustomer().getContactPhoneNumber() + "' is not valid.");
            return;
        }
        Task emailTask = new Task();
        emailTask.setBeanName("smsService");
        emailTask.setMethodName("sendSMS");
        emailTask.setRawArguments(new Object[] { event });
        emailTask.setPriority(priorityService.getPriorityForMessageType(event.getHeader().getMessageType().toString()));
        taskManager.createSMSTask(emailTask);

        if (event.getInternalSuccorData().getUpdateSuperTicket()) {
          //  logEvent(event, TicketType.SMS);
        }

    }

    /**
     * Create "institutional" task.
     * 
     * @param event
     */
    @Override
    public void createInstitutionalTicket(Event event) {
        if (!isContact(event)) {
            return;
        }
        createTicket(event, TicketType.INST);
    }

    @Required
    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Required
    public void setPostalHistoryService(PostalHistoryService postalHistoryService) {
        this.postalHistoryService = postalHistoryService;
    }

    @Required
    public void setCustomerSynchronizationService(CustomerSynchronizationService customerSynchronizationService) {
        this.customerSynchronizationService = customerSynchronizationService;
    }

    /**
     * Check if we need to contact Customer, base on his Contact preferences and
     * trigger setup.
     * 
     * @param event
     * @return
     */
    private boolean isContact(Event event) {
        if (event.getInternalSuccorData().isIgnoreDoNotContact()) {
            return true;
        }
        return event.getCustomer().getContact();
    }

    public void findSuperTicketId(Event event) {
        if (event.getInternalSuccorData().getUpdateSuperTicket()) {
            try {
                JSONObject superTicket = ticketParatureApi.findOrCreateSuperTicket(event);
                String superTicketId = TicketJSONParser.getTicketId(superTicket);
                String superTicketLang = TicketJSONParser.getTicketLang(superTicket);
                event.getInternalSuccorData().setSuperTicketId(superTicketId);
                event.getInternalSuccorData().setSuperTicketLang(superTicketLang);
            } catch (ApplicationException e) {
                LOG.error("Catch exception and rethrow", e);
                currentRuleExceptionStorage.set(e);
                throw e;
            } catch (Exception e) {
                LOG.error("Catch exception and rethrow", e);
                currentRuleExceptionStorage.set(e);
                throw new ApplicationException("Unexpected error", e);
            }
        }
    }

    @Required
    public void setTicketParatureApi(TicketParatureApi ticketParatureApi) {
        this.ticketParatureApi = ticketParatureApi;
    }

    @Required
    public void setCurrentRuleExceptionStorage(ThreadLocal<Exception> currentRuleExceptionStorage) {
        this.currentRuleExceptionStorage = currentRuleExceptionStorage;
    }

    public TaskPriorityService getPriorityService() {
        return priorityService;
    }

    public void setPriorityService(TaskPriorityService priorityService) {
        this.priorityService = priorityService;
    }
}
