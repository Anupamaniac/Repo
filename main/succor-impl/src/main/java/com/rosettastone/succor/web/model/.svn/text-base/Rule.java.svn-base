package com.rosettastone.succor.web.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rosettastone.succor.utils.ModelUtils;

/**
 * The {@code Rule} represents rule entity from db.
 */
@Entity
@Table(name = "rule")
public class Rule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rule_id")
    private Long id;
    
    @Column(name = "version")
    private Integer version;//versioning

    @Column(name = "name")
    private String name;

    @Column(name = "support_lang", length = 30)
    @Enumerated(value = EnumType.STRING)
    private Language supportLang = Language.EN;

    @ManyToOne
    private Event event;

    @ManyToOne
    private Product product;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "update_st")
    private Boolean updateSuperTicket;

    @Column(name = "ignore_dnc")
    private Boolean ignoreDoNotContact;

    @Column(name = "days")
    private Integer days;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "rule_id")
    private Set<Ticket> tickets;

    @Column(name = "ticket_type")
    @Enumerated(value = EnumType.STRING)
    private TicketType ticketType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "rule_id")
    private Set<Template> templates;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "rule_id")
    private Set<RuleAction> ruleActions;
   
    @ElementCollection(targetClass = Action.class)
    @CollectionTable(name = "rule_action", joinColumns = @JoinColumn(name = "rule_id"))
    @Enumerated(value = EnumType.STRING)
    @Column(name = "action")
    private Set<Action> actions;

    @Column(name="hours_prior_to_session",columnDefinition="int default 0")
	Integer hoursPriorToSession;
	
	@Column(name="solo")
	private Boolean solo;
	
	@Column(name="grandfathered")
	private Boolean grandfathered;
	
	
    public Rule() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getSupportLang() {
        return supportLang;
    }

    public void setSupportLang(Language supportLang) {
        this.supportLang = supportLang;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Boolean isUpdateSuperTicket() {
        return updateSuperTicket;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getUpdateSuperTicket() {
        return updateSuperTicket;
    }

    public void setUpdateSuperTicket(Boolean updateSuperTicket) {
        this.updateSuperTicket = updateSuperTicket;
    }

    public Boolean getIgnoreDoNotContact() {
        return ignoreDoNotContact;
    }

    public void setIgnoreDoNotContact(Boolean ignoreDoNotContact) {
        this.ignoreDoNotContact = ignoreDoNotContact;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public Set<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(Set<Template> templates) {
        this.templates = templates;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Rule)) {
            return false;
        }

        Rule rule = (Rule) obj;

        if (!active.equals(rule.getActive())) {
            return false;
        }
        
        if (!grandfathered.equals(rule.isGrandfathered())) {
            return false;
        }
        
        if (!solo.equals(rule.isSolo())) {
            return false;
        }

        if (!name.equals(rule.getName())) {
            return false;
        }

        if (!supportLang.equals(rule.getSupportLang())) {
            return false;
        }

        if (!event.getId().equals(rule.getEvent().getId())) {
            return false;
        }

        if (!product.getName().equals(rule.getProduct().getName())) {
            return false;
        }
        boolean eq = true;
        eq = eq && ModelUtils.equals(ticketType, rule.getTicketType());
        eq = eq && ModelUtils.equals(updateSuperTicket, rule.getUpdateSuperTicket());
        eq = eq && ModelUtils.equals(ignoreDoNotContact, rule.getIgnoreDoNotContact());
        eq = eq && ModelUtils.equals(days, rule.getDays());
        eq = eq && ModelUtils.equals(hoursPriorToSession, rule.getHoursPriorToSession());
        if (!eq) {
            return false;
        }
        if (!ModelUtils.equals(actions, rule.getActions())) {
            return false;
        }
        if (!ModelUtils.equals(tickets, rule.getTickets())) {
            return false;
        }
        if (!ModelUtils.equals(templates, rule.getTemplates())) {
            return false;
        }
        return true;
    }

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getVersion() {
		return version;
	}

	public void setRuleActions(Set<RuleAction> ruleActions) {
		this.ruleActions = ruleActions;
	}

	public Set<RuleAction> getRuleActions() {
		return ruleActions;
	}

	public Integer getHoursPriorToSession() {
		return hoursPriorToSession;
	}

	public void setHoursPriorToSession(Integer hoursPriorToSession) {
		this.hoursPriorToSession = hoursPriorToSession;
	}

	public Boolean isSolo() {
		return solo;
	}

	public void setSolo(Boolean solo) {
		this.solo = solo;
	}
	public Boolean isGrandfathered() {
		return grandfathered;
	}

	public void setGrandfathered(Boolean grandfathered) {
		this.grandfathered = grandfathered;
	}

}
