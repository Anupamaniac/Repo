package com.rosettastone.succor.web.service.drools;

/**
 * Model object that used as helper for generating DRL file by drools template
 * @see com.rosettastone.succor.web.service.RuleServiceImpl
 */
public class RuleParams {

    private String ruleCondition;
    private String ruleAction;
    private String ruleName;
    private String eventClass;

    public RuleParams() {

    }

    public RuleParams(String name, String eventClass, String condition, String action) {
        this.ruleName = name;
        this.eventClass = eventClass;
        this.ruleCondition = condition;
        this.ruleAction = action;
    }


    public String getRuleCondition() {
        return ruleCondition;
    }

    public void setRuleCondition(String ruleCondition) {
        this.ruleCondition = ruleCondition;
    }

    public String getRuleAction() {
        return ruleAction;
    }

    public void setRuleAction(String ruleAction) {
        this.ruleAction = ruleAction;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getEventClass() {
        return eventClass;
    }

    public void setEventClass(String eventClass) {
        this.eventClass = eventClass;
    }
}