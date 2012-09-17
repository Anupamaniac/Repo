package com.rosettastone.succor.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rule_action")
public class RuleAction {
	
	@Id
    @Column(name = "rule_id")
    private Long ruleId;
	
	@Column(name = "action")
    @Enumerated(value = EnumType.STRING)
    private Action action;

	public void setAction(Action action) {
		this.action = action;
	}

	public Action getAction() {
		return action;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public Long getRuleId() {
		return ruleId;
	}

	

/*	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
*/
	

}
