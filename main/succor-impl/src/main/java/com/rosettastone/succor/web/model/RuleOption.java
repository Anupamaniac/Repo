package com.rosettastone.succor.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/*@Entity
@Table(name = "rule_action")*/
public class RuleOption {
	
	@Column(name="hours_prior_to_session")
	int hoursPriorToSession;
	
	@Column(name="solo")
	boolean solo;
	
	@Column(name="grandfathered")
	boolean grandfathered;
	
	
	public int getHoursPriorTosession() {
		return hoursPriorToSession;
	}

	public void setHoursPriorToSession(int hours_prior_to_session) {
		this.hoursPriorToSession = hours_prior_to_session;
	}

	public boolean isSolo() {
		return solo;
	}

	public void setSolo(boolean solo) {
		this.solo = solo;
	}
	public boolean isGrandfathered() {
		return grandfathered;
	}

	public void setGrandfathered(boolean grandfathered) {
		this.grandfathered = grandfathered;
	}

}
