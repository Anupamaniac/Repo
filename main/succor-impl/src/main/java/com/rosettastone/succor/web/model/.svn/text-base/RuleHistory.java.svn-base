package com.rosettastone.succor.web.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The {@code RuleHistory} represents ruleHistory entity from db.
 */
@Entity
@Table(name = "rule_history")
public class RuleHistory implements Serializable {

	public static final long serialVersionUID = 1225404783754381249L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "rule_name")
	private String ruleName;

	@Column(name = "rule_id")
	private Long ruleId;

	@Column(name = "created")
	private Date created;

	@Column(name = "action")
    @Enumerated(value = EnumType.STRING)
	private HistoryAction action;

    @Column(name = "lang")
    @Enumerated(value = EnumType.STRING)
    private Language language;

	public RuleHistory() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public HistoryAction getAction() {
		return action;
	}

	public void setAction(HistoryAction action) {
		this.action = action;
	}

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
