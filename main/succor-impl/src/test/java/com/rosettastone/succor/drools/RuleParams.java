package com.rosettastone.succor.drools;

public class RuleParams {

    private String ruleCondition;
    private String ruleAction;
    private String ruleName;

    public RuleParams() {

    }

    public RuleParams(String name, String condition, String action) {
        ruleName = name;
        ruleCondition = condition;
        ruleAction = action;
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
}
