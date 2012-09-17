package com.rosettastone.succor.service;

import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.timertask.model.ReportEntityType;
import com.rosettastone.succor.web.model.Template;

/**
 * The {@code EmailService} allows to send emails.
 */
public class EmailService extends AbstractEmailService {

    public static final String rsKidPattern = ".rskid[0-9]+$";

    @Override
    public Template.Type getTemplateType() {
        return Template.Type.EMAIL;
    }

    @Override
    public ReportEntityType getReportEntityType() {
        return ReportEntityType.EMAIL_CUSTOMER;
    }

    @Override
    public String getToEmail(Customer customer) {
        String email = customer.getEmail().trim();
        if (customer.isKid()) {
            email = getParentEmail(email);
        }
        return email;
    }

    private String getParentEmail(String email) {
        email = email.trim();
        return email.replaceAll(rsKidPattern, "");
    }

}
