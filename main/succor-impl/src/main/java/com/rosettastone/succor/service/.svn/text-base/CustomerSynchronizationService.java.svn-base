package com.rosettastone.succor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;

import com.rosettastone.succor.dao.CustomerDAO;
import com.rosettastone.succor.dao.CustomerEntity;
import com.rosettastone.succor.exception.ObjectNotFoundException;
import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.parature.CustomerParatureApi;
import com.rosettastone.succor.utils.format.FormatUtils;

/**
 * The {@code CustomerSynchronizationService} allows to synchronize customer.
 */
public class CustomerSynchronizationService {

    private static final Log LOG = LogFactory.getLog(CustomerSynchronizationService.class);

    private CustomerDAO customerDao;
    private CustomerParatureApi customerParatureApi;

    /**
     * Common method for synchronization. Includes two synchronization: BAW
     * synchronization {@code synchronizeWithBAW} and PARATURE synchronization
     * {@code synchronizeWithParature}.
     * 
     * @param event
     */
    public void synchronize(Event event) {
        LOG.debug("synchronize customer with license id = " + event.getCustomer().getGuid() + " and email "
                + event.getCustomer().getEmail());
        synchronizeWithBAW(event);
        syncronizeWithParature(event);
        LOG.debug("synchronization was finished");
    }

    /**
     * Allows to synchronize customer with BAW.
     * 
     * @param event
     *            contains customer
     */
    public void synchronizeWithBAW(Event event) {
        Customer customer = event.getCustomer();

        if (!customer.getInstitutional()) {
            List<CustomerEntity> entities = customerDao.getByIdLang(customer.getGuid(), event.getMessage()
                    .getRsLanguageCode());

            if (entities == null) {
                throw new ObjectNotFoundException("Customer entity for license id = " + customer.getGuid()
                        + " does not exist.");
            }

            customer.setProductNames(entitiesToStrings(entities));
        }

        event.getInternalSuccorData().setSynchronizedWithBAW(true);
    }

    /**
     * Convert list of CustomerEntities to list of Strings.
     * 
     * @param entities
     * @return
     */
    private List<String> entitiesToStrings(List<CustomerEntity> entities) {
        ArrayList<String> strings = new ArrayList<String>();
        for (CustomerEntity ce : entities) {
            if (ce.getLang_level() != null) {
                strings.add(ce.getLang_level());
            }
        }
        return strings;
    }

    /**
     * Allows to synchronize customer with parature.
     * 
     * @param event
     *            contains info needed for synchronization
     */
    public void syncronizeWithParature(Event event) {
        // TODO if customer isTestUser and doesn't exist in Parature what to
        // do?
        // get call to parature
        Customer customer = event.getCustomer();

        boolean isKid = isKid(customer.getEmail());
        customer.setKid(isKid);

        customerParatureApi.synchronizeWithParature(customer, event.getInternalSuccorData().getSupportLocale());
        event.getInternalSuccorData().setPhoneValid(FormatUtils.isPhoneValid(customer.getContactPhoneNumber()));
    }

    /**
     * Check if the email is for Korean kid in format user@server.rskid1
     * 
     * @param email
     * @return
     */
    private static boolean isKid(String email) {
        email = email.trim();
        Pattern p = Pattern.compile(EmailService.rsKidPattern);
        Matcher m = p.matcher(email);
        if (m.find()) {
            return true;
        }
        return false;
    }

    @Required
    public void setCustomerDao(CustomerDAO customerDao) {
        this.customerDao = customerDao;
    }

    @Required
    public void setCustomerParatureApi(CustomerParatureApi customerParatureApi) {
        this.customerParatureApi = customerParatureApi;
    }
}
