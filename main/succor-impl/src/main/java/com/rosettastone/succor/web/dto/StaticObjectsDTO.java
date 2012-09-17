package com.rosettastone.succor.web.dto;

import com.rosettastone.succor.web.model.Product;
import com.rosettastone.succor.web.model.TemplateVariable;

import java.util.List;

/**
 * The {@code StaticObjectsDTO} represents data for flex.
 */
public class StaticObjectsDTO {

    private List<EnumDTO> actionTypes;

    private List<Product> productTypes;

    private List<EventDTO> eventTypes;

    private List<EnumDTO> ticketTypes;

    private List<EnumDTO> languages;

    private List<TemplateVariable> templateVariables;

    public List<EnumDTO> getActionTypes() {
        return actionTypes;
    }

    public void setActionTypes(List<EnumDTO> actionTypes) {
        this.actionTypes = actionTypes;
    }

    public List<Product> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<Product> productTypes) {
        this.productTypes = productTypes;
    }

    public List<EventDTO> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(List<EventDTO> eventTypes) {
        this.eventTypes = eventTypes;
    }

    public List<EnumDTO> getTicketTypes() {
        return ticketTypes;
    }

    public void setTicketTypes(List<EnumDTO> ticketTypes) {
        this.ticketTypes = ticketTypes;
    }

    public List<EnumDTO> getLanguages() {
        return languages;
    }

    public void setLanguages(List<EnumDTO> languages) {
        this.languages = languages;
    }

    public List<TemplateVariable> getTemplateVariables() {
        return templateVariables;
    }

    public void setTemplateVariables(List<TemplateVariable> templateVariables) {
        this.templateVariables = templateVariables;
    }
}
