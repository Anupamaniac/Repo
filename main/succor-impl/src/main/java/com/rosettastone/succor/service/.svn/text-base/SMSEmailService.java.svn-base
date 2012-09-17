package com.rosettastone.succor.service;

import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.timertask.model.ReportEntityType;
import com.rosettastone.succor.web.model.Template;

/**
 * User: Nikolay Sazonov
 * Date: 5/19/11
 */
public class SMSEmailService extends AbstractEmailService {

    @Override
    public Template.Type getTemplateType() {
        return Template.Type.SMS;
    }

    @Override
    public ReportEntityType getReportEntityType() {
        return null;
    }

    @Override
    public String getToEmail(Customer customer) {
        return customer.getContactPhoneEmail();
    }
}
