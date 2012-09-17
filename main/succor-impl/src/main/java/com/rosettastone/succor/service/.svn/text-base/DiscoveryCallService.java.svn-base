package com.rosettastone.succor.service;

import com.rosettastone.succor.backgroundtask.model.Message;
import com.rosettastone.succor.dao.DiscoveryCallEntity;
import com.rosettastone.succor.dao.DiscoveryCallPK;
import com.rosettastone.succor.model.bandit.*;
import com.rosettastone.succor.rules.RulesInvoker;
import com.rosettastone.succor.spring.BanditEventScopeEvent;
import com.rosettastone.succor.utils.DateUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * This service is created for processing DiscoveryCall tasks
 */
public class DiscoveryCallService extends ApplicationObjectSupport {


    private CustomerSynchronizationService synchronizationService;

    private RulesInvoker rulesInvoker;

    /**
     * This method called by TaskExecutor
     * @param entity
     * @param date
     * @throws Exception
     */
    public void processEntity(DiscoveryCallEntity entity, Date date) throws Exception {
        Event event = convertToEvent(entity, date);
        if (event == null) {
            return;
        }
        try {
            getApplicationContext().publishEvent(
                    new BanditEventScopeEvent(event, BanditEventScopeEvent.EventType.PROCESSING_STARTED));
            Message message = new Message();
            message.setMessage(event.getCustomer().getEmail());
           // synchronizationService.syncronizeWithParature(event); /*Parature dereferencing*/
           // event.getMessage().setGuid(event.getCustomer().getGuid());
            rulesInvoker.invokeForEvent(event);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            getApplicationContext().publishEvent(
                    new BanditEventScopeEvent(event, BanditEventScopeEvent.EventType.PROCESSING_FINISHED));
        }
    }

    /**
     * Build Event object from DiscoveryCallEntity
     * @param entity - DiscoveryCallEntity
     * @param date - current date
     * @return
     */
    private Event convertToEvent(DiscoveryCallEntity entity, Date date) {
        DiscoveryCallPK id = entity.getId();
        if (!StringUtils.hasText(id.getEmail()) || !StringUtils.hasText(id.getLanguage())
                || !StringUtils.hasText(id.getLanguageLevel())) {
            logger.info("Inconsistent entity " + entity);
            return null;
        }

        Event event = new Event();
        // create customer object
        Customer customer = new Customer();
        customer.setEmail(id.getEmail());
        customer.setLastName(entity.getName());
        customer.setAddressLine1(entity.getStreetAddress1());
        customer.setAddressLine2(entity.getStreetAddress2());
        customer.setCity(entity.getCity());
        customer.setPostalCode(entity.getPostalCode());

        customer.setSupportLanguageIso(getLocale(entity.getId().getLanguageCode()));

        List<String> productName = new ArrayList<String>();
        productName.add(id.getLanguageLevel());
        customer.setProductNames(productName);

        event.setCustomer(customer);
        // create message object
        DiscoveryCallMessage message = new DiscoveryCallMessage();
        int days = DateUtils.getDaysIntervalLength(date, entity.getId().getOrderedOn());
        message.setDays(days);
        message.setRsLanguageCode("ENG");
        event.setMessage(message);
        // create header object
        Header header = new Header();
        header.setCreatedAt(date);
        header.setMessageType(MessageType.discovery_call_message);
        event.setHeader(header);

        // calculate customer support locale
        Locale locale = getLanguageLocale(entity.getId().getLanguageCode());
        // create internal succor data and initialize some fields
        event.getInternalSuccorData().setSupportLocale(locale);
        event.getInternalSuccorData().setActionAsKey(MessageType.discovery_call_message.name());
        return event;
    }

    /**
     * Return locale by language
     * @param code - locale code in en-US format.
     * @return locale
     */
    private Locale getLanguageLocale(String code) {
        if (code == null) {
            return null;
        }
        String language = code.substring(0, 2);
        return new Locale(language);
    }

    /**
     * Return locale by language and country
     * @param code - locale code in en-US format.
     * @return locale
     */
    private Locale getLocale(String code) {
        if (code == null) {
            return null;
        }
        String language = code.substring(0, 2);
        String country = code.substring(3, 5);
        return new Locale(language, country);
    }

    @Required
    public void setSynchronizationService(CustomerSynchronizationService synchronizationService) {
        this.synchronizationService = synchronizationService;
    }

    @Required
    public void setRulesInvoker(RulesInvoker rulesInvoker) {
        this.rulesInvoker = rulesInvoker;
    }
}
