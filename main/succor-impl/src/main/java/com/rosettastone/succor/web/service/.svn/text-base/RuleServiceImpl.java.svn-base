package com.rosettastone.succor.web.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.drools.template.ObjectDataCompiler;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rosettastone.succor.parature.TicketParatureApi;
import com.rosettastone.succor.web.dao.RuleDao;
import com.rosettastone.succor.web.model.Action;
import com.rosettastone.succor.web.model.Event;
import com.rosettastone.succor.web.model.Language;
import com.rosettastone.succor.web.model.Product;
import com.rosettastone.succor.web.model.Rule;
import com.rosettastone.succor.web.model.Value;
import com.rosettastone.succor.web.service.drools.RuleParams;

/**
 * Implementation of RuleService interface
 * @see RuleService
 */
public class RuleServiceImpl implements RuleService {

    private RuleDao ruleDao;

    /**
     * Load list of rules from DB and generate DRL file.
     * @return DRL
     */

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public String createRulesByTemplate() {
        return createRulesByTemplate(ruleDao.loadActive());
    }

    /**
     * Generate DRL file by list of rules.
     * @param rules
     * @return DRL
     */
    @Override
    public String createRulesByTemplate(List<Rule> rules) {
        List<RuleParams> templateParams = new ArrayList<RuleParams>();
        for (Rule rule : rules) {
            RuleParams params = new RuleParams();
            params.setRuleName(rule.getName() + " " + rule.getSupportLang().toString());
            params.setRuleAction(createAction(rule));
            params.setEventClass(rule.getEvent().getMessageType().getClassName());
            params.setRuleCondition(createCondition(rule));
            templateParams.add(params);
        }

        InputStream templateInput = RuleServiceImpl.class.getResourceAsStream("/drools/rule-template.drl");

        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drlRules = converter.compile(templateParams, templateInput);
        return drlRules;
    }

    /**
     * Create condition for specified rule
     * @param rule
     * @return condition section
     */
    private String createCondition(Rule rule) {
        Event event = rule.getEvent();
        Product product = rule.getProduct();
        Language supportLang = rule.getSupportLang();

        StringBuilder str = new StringBuilder();
        Set<Value> values = event.getValues();

        Iterator<Value> iterator = values.iterator();
        while (iterator.hasNext()) {
            Value v = iterator.next();
            String name = v.getVariable().getName();
            String value = v.getValue();
            if (name.equals("reason")) {
                str.append(" (message.").append(name).append(" == ").append(value).append(") && ");
            } else {
                str.append(" (message.").append(name).append(" == \"").append(value).append("\") && ");
            }
        }

        if (product.getName().equals(TicketParatureApi.INSTITUTIONAL)) {
            str.append("(customer.institutional == \"true\")");
        } else {
            str.append("(customer.productNames contains \"").append(product.getName()).append("\")");
        }
        if (supportLang != null) {
            str.append(" && (internalSuccorData.supportLocale.language == \"").append(supportLang.name().toLowerCase())
                    .append("\")");
        }
//        if ("DiscoveryCallMessage".equals(event.getClassName())
//                || "ExpiringSubscriptionMessage".equals(event.getClassName())) {
//            str.append(" && (message.days == \"").append(rule.getDays()).append("\")");
//        }
        if ("DiscoveryCallMessage".equals(event.getMessageType().getClassName())
                || "ExpiringSubscriptionMessage".equals(event.getMessageType().getClassName())) {
            str.append(" && (message.days == \"").append(rule.getDays()).append("\")");
        }
        if ("StudioSessionCancellationMessage".equals(event.getMessageType().getClassName())) {
            str.append(" && (message.solo == \"").append(rule.isSolo()).append("\")");
            str.append(" && (license.grandfathered == \"").append(rule.isGrandfathered()).append("\")");
        }
        if ("StudioReminderMessage".equals(event.getMessageType().getClassName())) {
            str.append(" && (message.solo == \"").append(rule.isSolo()).append("\")");
            str.append(" && (license.grandfathered == \"").append(rule.isGrandfathered()).append("\")");
            str.append(" && (message.hours == \"").append(rule.getHoursPriorToSession()).append("\")");
        }
        return str.toString();
    }

    /**
     * Create actions for specified rule
     * @param rule
     * @return actions section
     */
    private String createAction(Rule rule) {
        Set<Action> actions = rule.getActions();
        StringBuilder str = new StringBuilder();
        str.append("\t\t$event.internalSuccorData.updateSuperTicket = " + rule.isUpdateSuperTicket().toString() + ";\n");
        str.append("\t\teventService.findSuperTicketId($event);\n");
        str.append("\t\t$event.internalSuccorData.ruleId = ").append(rule.getId()).append("l;\n");
        str.append("\t\t$event.internalSuccorData.ignoreDoNotContact = ").append(rule.getIgnoreDoNotContact())
                .append(";\n");
        if (actions == null) {
            return str.toString();
        }
        for (Action action : actions) {
            switch (action) {
            case EMAIL:
                str.append("\t\teventService.sendEmail($event);\n");
                break;
           /*case PHONE:
                str.append("\t\teventService.createPhoneTicket($event);\n");
                break;
            case PHONE_OR_EMAIL:
                str.append("\t\teventService.createPhoneTicketOrSendEmail($event);\n");
                break;*/
            case POSTAL:
                str.append("\t\teventService.sendPostalMail($event);\n");
                break;
          /*case INSTANT_ACTION_TICKET:
                str.append("\t\teventService.createInstitutionalTicket($event);\n");
                break;*/
            case SMS:
                str.append("\t\teventService.sendSMS($event);\n");
            default:
            }
        }

        return str.toString();
    }

    public void setRuleDao(RuleDao ruleDao) {
        this.ruleDao = ruleDao;
    }
}
