package com.rosettastone.succor.web.service;

import com.rosettastone.succor.web.model.Rule;

import java.util.List;

/**
 * Service interface for generating drools rules by Rule model objects.
 */
public interface RuleService {

    /**
     * This service methods loads all active rules from database and generates drools rules (DRL)
     * @return DRL
     */
    public String createRulesByTemplate();

    /**
     * Generate drools rules (DRL) by list of Rule objects
     * @return DRL
     */
    String createRulesByTemplate(List<Rule> rules);
}
