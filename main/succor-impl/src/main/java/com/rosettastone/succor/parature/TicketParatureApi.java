package com.rosettastone.succor.parature;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.springframework.beans.factory.annotation.Required;

import com.rosettastone.succor.backgroundtask.model.ParatureAction;
import com.rosettastone.succor.exception.ApplicationException;
import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.model.parature.Action;
import com.rosettastone.succor.model.parature.ActionCsr;
import com.rosettastone.succor.model.parature.OutreachTicket;
import com.rosettastone.succor.model.parature.SuperTicket;
import com.rosettastone.succor.model.parature.Ticket;
import com.rosettastone.succor.model.parature.TicketLanguageType;
import com.rosettastone.succor.model.parature.TicketType;
import com.rosettastone.succor.timertask.dao.ReportDao;
import com.rosettastone.succor.timertask.model.ReportEntityType;
import com.rosettastone.succor.utils.ActionSerializer;
import com.rosettastone.succor.utils.ParatureHttpDelegator;
import com.rosettastone.succor.utils.TicketJSONParser;
import com.rosettastone.succor.utils.TicketObjectBuilder;
import com.rosettastone.succor.utils.TicketSerializer;
import com.rosettastone.succor.utils.TicketXMLParser;
import com.rosettastone.succor.utils.mapping.ActionConverter;
import com.rosettastone.succor.utils.mapping.LanguageConverter;
import com.rosettastone.succor.web.dao.RuleDao;
import com.rosettastone.succor.web.dao.TemplateDao;
import com.rosettastone.succor.web.model.Template;

/**
 * The {@code TicketParatureApi} allow to manages tickets on parature.
 */

public class TicketParatureApi {

    public static final String SUCCESS_OUTREACH = "Success Outreach";
    public static final String PHONE_OUTREACH = "Phone Outreach";
    public static final String SUCCESS_SUPER = "Super Ticket";
    public static final String INSTITUTIONAL = "INST";

    private static final Log LOG = LogFactory.getLog(TicketParatureApi.class);

    private static final String TICKET_URL = "/Ticket/";
    private static final String CREATE_TICKET_URL = "/Ticket/?_enforceRequiredFields_=false";
    private static final String TICKET_JSON_URL = "/Ticket?_output_=json&";

    private ParatureHttpClient paratureHttpClient;

    private TicketSerializer ticketSerializer;

    private ActionSerializer actionSerializer;

    private LanguageConverter languageConverter;

    private ActionConverter actionConverter;

    private ReportDao reportDao;

    private RuleDao ruleDao;

    private ParatureProperties paratureProperties;

    private TicketObjectBuilder ticketBuilder;

    private TemplateDao templateDao;

    private CsrParatureApi csrParatureApi;

    private ParatureHttpDelegator paratureHttpDelegator;

    /**
     * Creates a ticket.
     * 
     * @param event
     * @param ticketType
     */
    public void createTicket(Event event, TicketType ticketType) {
        LOG.debug("Create new parature ticket");
        if (event.getInternalSuccorData().getTestWorkLocally()) {
            LOG.debug("Testing locally");
            return;
        }
        createOutreachTicket(event, ticketType);
    }

    /**
     * Creates superticket if needed, updates superticket.
     * 
     * @param event
     * @param ticketType
     */
    public void logEvent(Event event, TicketType ticketType) {
        LOG.debug("log event " + ticketType);
        if (event.getInternalSuccorData().getTestWorkLocally()) {
            LOG.debug("Testing locally");
            return;
        }
        String ticketId = event.getInternalSuccorData().getSuperTicketId();
        if (ticketId != null) {
            updateSingleSuperTicket(ticketId, event, ticketType);
        } else {
            LOG.debug("There is no super ticket Id in event.");
        }
    }

    public JSONObject findOrCreateSuperTicket(Event event) {
        if (event.getInternalSuccorData().getTestWorkLocally()) {
            LOG.debug("Testing locally");
            return null;
        }

        Customer customer = event.getCustomer();
        String paratureId = customer.getParatureId().toString();
        String productLanguage = event.getMessage().getIsoLanguageCode();
        JSONObject ticket = null;
        if (productLanguage != null) {
            // find ticket by lang
            ticket = findTicketByCustomerIdLanguage(paratureId, productLanguage);
        }
        if (ticket == null) {
            // maybe customer has no lang ticket
            ticket = findTicketByCustomerId(paratureId);
        }
        if (ticket == null) {
            String ticketId = createSuperTicket(event);
            try {
                ticket = new JSONObject();
                ticket.put("@id", ticketId);
            } catch (JSONException e) {
                throw new ApplicationException("Can not create super ticket");
            }
        }
        return ticket;
    }

    /**
     * Create phone or institutional ticket. and reassign to csr.
     * 
     * @param event
     * @param ticketType
     */
    private void createOutreachTicket(Event event, TicketType ticketType) {
        // OutreachTicket outreachTicket = prepareOutreachTicket(customer,
        // ticketType);
        // String language = getCustomerLanguageString(event);
        String language = event.getInternalSuccorData().getSuperTicketLang();

        OutreachTicket outreachTicket = ticketBuilder.prepareOutreachTicket(event, ticketType, language);
        String summary = null;
        if (ticketType == TicketType.INST) {
            summary = ruleDao.loadTicketSummary(event.getInternalSuccorData().getRuleId(),
                    com.rosettastone.succor.web.model.TicketType.INSTITUTIONAL);
        } else if (ticketType == TicketType.PHONE) {
            summary = ruleDao.loadTicketSummary(event.getInternalSuccorData().getRuleId(),
                    com.rosettastone.succor.web.model.TicketType.CST_OUTREACH);
        }
        outreachTicket.setSummary(summary);

        String ticketResponse = createParatureTicket(outreachTicket, ParatureAction.PARATURETICKETCREATION);
        String ticketId = TicketXMLParser.getTicketIdFromXML(ticketResponse);
        LOG.debug("====>>>>>"+ticketId +"<<<+++"+ticketResponse);
        outreachTicket.setId(ticketId);

        // updateTicket(outreachTicket, ticketId);

        try {
            csrParatureApi.setCsrToCustomer(event.getCustomer());
        } catch (JSONException e) {
            LOG.debug("ERROR while setting CSR.");
            e.printStackTrace();
        }
        if (event.getCustomer().getCsrId() != null) {
            reassignCsr(ticketBuilder.getActionCsrObject(event.getCustomer().getCsrId()), Long.valueOf(ticketId));
        } else {
            LOG.debug("Can not assign ticket to null CSR.");
        }
        reportTicket(ticketType);
    }

    /**
     * Log ticket create/update in reportDao.
     * 
     * @param ticketType
     */
    private void reportTicket(TicketType ticketType) {
        switch (ticketType) {
        case INST:
            reportDao.createNewEntity(ReportEntityType.INSTITUTIONAL_TICKET);
            break;
        case PHONE:
            reportDao.createNewEntity(ReportEntityType.PHONE_TICKET);
            break;
        case SUPER:
            reportDao.createNewEntity(ReportEntityType.SUPER_TIKET);
            break;
        default:
            throw new ApplicationException("Unsupported tiket type " + ticketType);
        }
    }

    // /**
    // * Get customer language string in human readable format.
    // *
    // * @param event
    // * @return string
    // */
    // private String getCustomerLanguageString(Event event) {
    // if (event.getMessage().getIsoLanguageCode() == null) {
    // return "";
    // }
    // Customer customer = event.getCustomer();
    // String productLanguage = event.getMessage().getIsoLanguageCode();
    // String response = getTicketResponseXML(event, productLanguage);
    // String language = TicketXMLParser.getOptionTextValue(response,
    // getLangId(productLanguage));
    // return language;
    // }

    /**
     * Create and update super ticket with action.
     * 
     * @param event
     * @param ticketType
     */
    private void createAndUpdateSuperTicket(Event event, TicketType ticketType) {
        String ticketId = createSuperTicket(event);
        if (ticketId != null) {
            updateTicketAction(ticketId, event, ticketType);
        }
    }

    /**
     * Check if user has one or multiple super tickets and update required.
     * 
     * @param event
     * @param ticketType
     * @param ticketIds
     */
    private void checkAndUpdateSuperTicket(Event event, TicketType ticketType, List<String> ticketIds) {
        if (ticketIds.size() == 1) {
            updateSingleSuperTicket(ticketIds.get(0), event, ticketType);
        } else {
            findAndUpdateSuperTicket(event, ticketType);
        }
    }

    /**
     * Update single ticket with language and action.
     * 
     * @param ticketId
     * @param event
     * @param ticketType
     */
    private void updateSingleSuperTicket(String ticketId, Event event, TicketType ticketType) {
        SuperTicket ticket = ticketBuilder.createSuperTicketObjectWithCustomerAndLanguage(event);
        if (event.getMessage().getIsoLanguageCode() != null) {
            updateSuperTicketSummary(event, ticket, ticketId);
        }
        updateTicketAction(ticketId, event, ticketType);
    }

    /**
     * Find ticket by language and update it.
     * 
     * @param event
     * @param ticketType
     */
    private void findAndUpdateSuperTicket(Event event, TicketType ticketType) {
        Customer customer = event.getCustomer();
        String paratureId = customer.getParatureId().toString();
        String productLanguage = event.getMessage().getIsoLanguageCode();

        // String ticketId = findTicketIdByCustomerIdLanguage(paratureId,
        // productLanguage);
        JSONObject ticket = findTicketByCustomerIdLanguage(paratureId, productLanguage);
        String ticketId = null;
        try {
            ticketId = ticket.getString("@id");
        } catch (JSONException e) {
            ticketId = null;
        }
        if (ticketId != null) {
            updateTicketAction(ticketId, event, ticketType);
        }
    }

    private String createSuperTicket(Event event) {
        SuperTicket superTicket = ticketBuilder.prepareSuperTicket(event);
        String response = createParatureTicket(superTicket, ParatureAction.SUPERTICKETCREATION);
        String id = TicketXMLParser.getTicketIdFromXML(response);
        solveSuperTicket(id);
        return id;
    }

    private void solveSuperTicket(String ticketId) {
        String actionType = paratureProperties.get("parature.action.type.solve");
        Date actionDate = new Date();
        createAction(ticketBuilder.getActionObject(Long.valueOf(ticketId), actionType, actionDate, null), ticketId,
                ParatureAction.SUPERTICKETACTIONUPDATION);
    }

    private void updateSuperTicketSummary(Event event, SuperTicket ticket, String ticketId) {
        String summary = getSuperTicketSummary(event, ticketId);
        ticket.setSummary(summary);
        updateTicket(ticket, ticketId, ParatureAction.SUPERTICKETSUMMARYUPDATION);
    }

    /**
     * Return string in format "LANGUAGE + Super Ticket" for events where
     * language is defined, "Super Ticket" otherwise.
     * 
     * @param event
     * @param ticketId
     * @return
     */
    private String getSuperTicketSummary(Event event, String ticketId) {
        if (event.getMessage().getIsoLanguageCode() == null) {
            return TicketParatureApi.SUCCESS_SUPER;
        }

        String productLanguage = event.getMessage().getIsoLanguageCode();
        String response = paratureHttpClient.sendGetRequest(TICKET_URL + ticketId);
        String language = TicketXMLParser.getOptionTextValue(response, getLangId(productLanguage));
        return language + " " + TicketParatureApi.SUCCESS_SUPER;
    }

    private void updateTicketAction(String ticketId, Event event, TicketType ticketType) {
        String actionType = actionConverter.convert(event, ticketType);
        Date actionDate = event.getHeader().getCreatedAt();
        String emailSubject = "";
        if (TicketType.EMAIL.equals(ticketType)) {
            Template template = templateDao.load(event.getInternalSuccorData().getRuleId(), Template.Type.EMAIL, event
                    .getCustomer().isKid());
            if (template == null) {
                return;
            }
            emailSubject = template.getSubject();
        }
        createAction(ticketBuilder.getActionObject(Long.valueOf(ticketId), actionType, actionDate, emailSubject),
                ticketId, ParatureAction.SUPERTICKETACTIONUPDATION);
        reportTicket(TicketType.SUPER);
    }

    private String createParatureTicket(Ticket ticket, ParatureAction paratureAction) {
        InputStream is = ticketSerializer.serialize(ticket);
        String response = sendCreate(CREATE_TICKET_URL, is, ticket, paratureAction);
        return response;
    }

    private String updateTicket(Ticket ticket, String ticketId, ParatureAction paratureAction) {
        InputStream is = ticketSerializer.serialize(ticket);
        String response = sendUpdate(TICKET_URL + ticketId + "?_enforceRequiredFields_=false", is, ticketId, ticket,
                paratureAction);
        return response;
    }

    /**
     * Reassign ticket to csr.
     * 
     * @param action
     * @param ticketId
     */
    public void reassignCsr(ActionCsr action, long ticketId) {
        if (action.getAssignToCsr() == null) {
            return;
        }
        String actionId = paratureProperties.getReAssignTicket();
        action.setId(actionId);
        InputStream is = actionSerializer.serializeCsr(action);
        LOG.debug("Reassign ticket '" + ticketId + "', action " + action);
        sendUpdate(TICKET_URL + ticketId, is, Long.toString(ticketId), action, ParatureAction.SUPERTICKETACTIONUPDATION);
    }

    public String createAction(Action action, String ticketId, ParatureAction paratureAction) {
        InputStream is = actionSerializer.serialize(action);
        String response = sendUpdate(TICKET_URL + action.getTicketId(), is, ticketId, action, paratureAction);
        return response;
    }

    /**
     * Send update ticket request.
     * 
     * @param url
     * @param is
     * @return
     */
    private String sendUpdate(String url, InputStream is, String ticketId, Object ticketOrAction,
            ParatureAction paratureAction) {
        return paratureHttpDelegator.sendPutRequest(url, is, ticketId, ticketOrAction, paratureAction);
    }

    /**
     * Send create ticket request.
     * 
     * @param url
     * @param is
     * @return
     */
    private String sendCreate(String url, InputStream is, Object ticketOrAction, ParatureAction paratureAction) {
        return paratureHttpDelegator.sendPostRequest(url, is, ticketOrAction, paratureAction);

    }

    /**
     * Find SuperTicket id for Customer with specified CustomerId.
     * 
     * @param customerId
     * @param language
     * @return
     */
    private JSONObject findTicketByCustomerIdLanguage(String customerId, String language) {
        String response = findTicketResponseJSON(customerId, language);
        try {
            JSONObject ticket = TicketJSONParser.parseTicketJsonFromEntitiesResponse(response);
            return ticket;
        } catch (JSONException e) {
            throw new ApplicationException("Can not find ticket by customerId=" + customerId);
        }
    }

    private String getTicketResponseXML(Event event, String language) {
        String ticketId = event.getInternalSuccorData().getSuperTicketId();
        String response = paratureHttpClient.sendGetRequest(TICKET_URL + ticketId);
        return response;
    }

    private String findTicketResponseJSON(String customerId, String language) {
        String languageId = getLangId(language);
        String requestPath = buildFindTicketURL(customerId, languageId);
        String response = paratureHttpClient.sendGetRequest(requestPath);
        return response;
    }

    private String getLangId(String language) {
        try {
            TicketLanguageType langType = languageConverter.convert(language);
            return paratureProperties.get(langType.getKey());
        } catch (NullPointerException ex) {
            throw new ApplicationException("No language found in the mapping : " + language);
        }
    }

    /**
     * Find all customers Super Ticket and take the first one.
     * 
     * @param customerId
     * @return
     */
    private JSONObject findTicketByCustomerId(String customerId) {
        JSONArray ticketIds;

        String requestUrl = buildTicketsByCustomerURL(customerId);
        String response = paratureHttpClient.sendGetRequest(requestUrl);

        try {
            ticketIds = TicketJSONParser.getTicketsFromEntitiesResponse(response);
            if (ticketIds != null && ticketIds.length() > 0) {
                return ticketIds.getJSONObject(0);
            }
        } catch (JSONException e) {
            throw new ApplicationException("Can not find ticket by customerId=" + customerId);
        }
        return null;
    }

    /**
     * Build url for API request to find tickets by customer.
     * 
     * @param customerId
     * @return
     */
    private String buildTicketsByCustomerURL(String customerId) {
        return TICKET_JSON_URL + "Ticket_Customer_id_=" + customerId + "&" + paratureProperties.getTicketTypeField()
                + "=" + paratureProperties.getSuccorType();
    }

    /**
     * Build url for API request to find ticket by customer, language.
     * 
     * @param customerId
     * @param languageId
     * @return
     */
    private String buildFindTicketURL(String customerId, String languageId) {
        return TICKET_JSON_URL + "Ticket_Customer_id_=" + customerId + "&" + paratureProperties.getTicketTypeField()
                + "=" + paratureProperties.getSuccorType() + "&" + paratureProperties.getLanguageField() + "="
                + languageId + "&_fields_=" + paratureProperties.getLanguage();
    }

    @Required
    public void setTicketSerializer(TicketSerializer ticketSerializer) {
        this.ticketSerializer = ticketSerializer;
    }

    @Required
    public void setActionSerializer(ActionSerializer actionSerializer) {
        this.actionSerializer = actionSerializer;
    }

    public void setLanguageConverter(LanguageConverter languageConverter) {
        this.languageConverter = languageConverter;
    }

    public void setActionConverter(ActionConverter actionConverter) {
        this.actionConverter = actionConverter;
    }

    @Required
    public void setReportDao(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    @Required
    public void setParatureProperties(ParatureProperties paratureProperties) {
        this.paratureProperties = paratureProperties;
    }

    public void setTicketBuilder(TicketObjectBuilder ticketBuilder) {
        this.ticketBuilder = ticketBuilder;
    }

    @Required
    public void setParatureHttpClient(ParatureHttpClient paratureHttpClient) {
        this.paratureHttpClient = paratureHttpClient;
    }

    @Required
    public void setRuleDao(RuleDao ruleDao) {
        this.ruleDao = ruleDao;
    }

    @Required
    public void setTemplateDao(TemplateDao templateDao) {
        this.templateDao = templateDao;
    }

    @Required
    public void setCsrParatureApi(CsrParatureApi csrParatureApi) {
        this.csrParatureApi = csrParatureApi;
    }

    public ParatureHttpDelegator getParatureHttpDelegator() {
        return paratureHttpDelegator;
    }

    public void setParatureHttpDelegator(ParatureHttpDelegator paratureHttpDelegator) {
        this.paratureHttpDelegator = paratureHttpDelegator;
    }
}
