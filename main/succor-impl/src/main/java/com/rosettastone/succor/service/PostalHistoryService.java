package com.rosettastone.succor.service;

import com.rosettastone.succor.timertask.model.PostalHistory;
import org.springframework.beans.factory.annotation.Required;

import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.model.parature.ContactReasonType;
import com.rosettastone.succor.model.parature.TicketLanguageType;
import com.rosettastone.succor.timertask.dao.PostalHistoryDao;
import com.rosettastone.succor.timertask.model.PostalHistoryStatus;
import com.rosettastone.succor.utils.format.FormatUtils;
import com.rosettastone.succor.utils.mapping.ContactReasonConverter;
import com.rosettastone.succor.utils.mapping.LanguageConverter;

/**
 * Service for logging history about postal actions.
 */
public class PostalHistoryService {

    private LanguageConverter languageConverter;
    private ContactReasonConverter contactReasonConverter;

    private PostalHistoryDao postalHistoryDao;

    /**
     * Create history entity about postal action in DB
     * @param event - source object for generating postal history
     */
    public void createPostalEntry(Event event) {
        PostalHistory postalHistory = createPostalHistory(event);
        postalHistoryDao.create(postalHistory);
    }

    /**
     * Create PostalHistory object by Event object
     * @param event
     * @return
     */
    private PostalHistory createPostalHistory(Event event) {
        PostalHistory history = new PostalHistory();
        history.setCreatedAt(event.getHeader().getCreatedAt());
        history.setStatus(PostalHistoryStatus.UNPROCESSED);

        Customer customer = event.getCustomer();
        history.setCustomerName(String.format("%s %s", customer.getFirstName(), customer.getLastName()));

        TicketLanguageType language = languageConverter.convert(event.getMessage().getIsoLanguageCode());
        history.setLanguage(language);

        ContactReasonType contactReason = contactReasonConverter.convert(event);
        history.setContactReason(contactReason);

        history.setProductLevel(customer.getProductNames().get(0));

        history.setShippingAddress(FormatUtils.csvformatCustomerAddress(customer));

        history.setEmail(customer.getEmail());

        history.setGuid(customer.getGuid());

        history.setSupportLocale(event.getInternalSuccorData().getSupportLocale().getLanguage());
        return history;
    }

    @Required
    public void setLanguageConverter(LanguageConverter languageConverter) {
        this.languageConverter = languageConverter;
    }

    @Required
    public void setContactReasonConverter(ContactReasonConverter contactReasonConverter) {
        this.contactReasonConverter = contactReasonConverter;
    }

    @Required
    public void setPostalHistoryDao(PostalHistoryDao postalHistoryDao) {
        this.postalHistoryDao = postalHistoryDao;
    }
}
