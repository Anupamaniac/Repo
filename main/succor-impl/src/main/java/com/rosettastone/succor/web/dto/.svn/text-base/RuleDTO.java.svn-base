package com.rosettastone.succor.web.dto;

import com.rosettastone.succor.web.model.Template;

import java.util.Set;

import javax.persistence.Column;

/**
 * The {@code RuleDTO} represents Rule object for flex.
 */
public class RuleDTO {

    private Long id;

    private String name;

    private String supportLang;

    private String productName;

    private long eventId;

    private boolean active;

    private boolean remove;

    private boolean updateSuperTicket;

    private boolean ignoreDoNotContact;

    private Integer days;

    private Set<String> actions;

    private Set<TicketDTO> tickets;

    private String selectedTicketType;

    private Set<Template> templates;
    
    private Integer version;// versioning
    
    private boolean addNew;// versioning
    
    private String hoursPriorToSession;
	
	private boolean solo;
    
    private boolean grandfathered;
	
    public String getHoursPriorToSession() {
		return hoursPriorToSession;
	}

	public void setHoursPriorToSession(String hoursPriorToSession) {
		this.hoursPriorToSession = hoursPriorToSession;
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

    public String getSupportLang() {
        return supportLang;
    }

    public void setSupportLang(String supportLang) {
        this.supportLang = supportLang;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setGrandfathered(boolean grandfathered) {
		this.grandfathered = grandfathered;
	}

	public boolean isGrandfathered() {
		return grandfathered;
	}
    public boolean isUpdateSuperTicket() {
        return updateSuperTicket;
    }

    public void setUpdateSuperTicket(boolean updateSuperTicket) {
        this.updateSuperTicket = updateSuperTicket;
    }

    public boolean isIgnoreDoNotContact() {
        return ignoreDoNotContact;
    }

    public void setIgnoreDoNotContact(boolean ignoreDoNotContact) {
        this.ignoreDoNotContact = ignoreDoNotContact;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Set<String> getActions() {
        return actions;
    }

    public void setActions(Set<String> actions) {
        this.actions = actions;
    }

    public Set<TicketDTO> getTickets() {
        return tickets;
    }

    public void setTickets(Set<TicketDTO> tickets) {
        this.tickets = tickets;
    }

    public String getSelectedTicketType() {
        return selectedTicketType;
    }

    public void setSelectedTicketType(String selectedTicketType) {
        this.selectedTicketType = selectedTicketType;
    }

    public Set<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(Set<Template> templates) {
        this.templates = templates;
    }

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getVersion() {
		return version;
	}

	public void setAddNew(boolean addNew) {
		this.addNew = addNew;
	}

	public boolean isAddNew() {
		return addNew;
	}

	public void setSolo(boolean solo) {
		this.solo = solo;
	}

	public boolean isSolo() {
		return solo;
	}

	

	
}
