package com.rosettastone.succor.web.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rosettastone.succor.exception.InvalidUpdateException;
import com.rosettastone.succor.rules.RulesInvoker;
import com.rosettastone.succor.web.TemplateHolder;
import com.rosettastone.succor.web.dao.EventDao;
import com.rosettastone.succor.web.dao.ProductDao;
import com.rosettastone.succor.web.dao.RuleDao;
import com.rosettastone.succor.web.dao.TemplateDao;
import com.rosettastone.succor.web.dto.EnumDTO;
import com.rosettastone.succor.web.dto.EventDTO;
import com.rosettastone.succor.web.dto.RuleDTO;
import com.rosettastone.succor.web.dto.StaticObjectsDTO;
import com.rosettastone.succor.web.dto.TicketDTO;
import com.rosettastone.succor.web.model.Action;
import com.rosettastone.succor.web.model.Event;
import com.rosettastone.succor.web.model.HistoryAction;
import com.rosettastone.succor.web.model.Language;
import com.rosettastone.succor.web.model.Product;
import com.rosettastone.succor.web.model.Rule;
import com.rosettastone.succor.web.model.RuleHistory;
import com.rosettastone.succor.web.model.Template;
import com.rosettastone.succor.web.model.Ticket;
import com.rosettastone.succor.web.model.TicketType;
import com.rosettastone.succor.web.sso.SuccUserDetails;

/**
 * Special service that used by flex UI
 */
public class FlexService {

	private static final Log LOG = LogFactory.getLog(FlexService.class);
	
    private EventDao eventDao;

    private ProductDao productDao;

    private RuleDao ruleDao;

    private TemplateDao templateDao;

    private RulesInvoker rulesInvoker;

    private TemplateHolder templateHolder;

    /**
     * Create list of available actions as data transfer objects.
     * @return
     */
    public List<EnumDTO> getActions() {
        List<EnumDTO> actions = new ArrayList<EnumDTO>();
//        actions.add(new EnumDTO(Action.PHONE.name(), "Phone"));
        actions.add(new EnumDTO(Action.EMAIL.name(), "Email"));
        actions.add(new EnumDTO(Action.POSTAL.name(), "Postal"));
       /* actions.add(new EnumDTO(Action.INSTANT_ACTION_TICKET.name(), "Institutional Action Ticket"));
        actions.add(new EnumDTO(Action.PHONE_OR_EMAIL.name(), "Phone or email"));*/
        actions.add(new EnumDTO(Action.SMS.name(), "SMS"));
        return actions;
    }

    /**
     * Create list of ticket types as data transfer objects.
     * @return
     */
    public List<EnumDTO> getTickets() {
        List<EnumDTO> tickets = new ArrayList<EnumDTO>();
        tickets.add(new EnumDTO(TicketType.CST_OUTREACH.name(), "CST Outreach Ticket"));
        tickets.add(new EnumDTO(TicketType.INSTITUTIONAL.name(), "Institutional Ticket"));
        return tickets;
    }

    /**
     * Returns all static objects to flex side
     * (enums, languages, events and etc)
     * @return
     */
    public StaticObjectsDTO getStaticObjects(String langCode) {
        StaticObjectsDTO so = new StaticObjectsDTO();
        so.setActionTypes(getActions());
        so.setProductTypes(getProducts(langCode));
        so.setEventTypes(getEvents());
        so.setTicketTypes(getTickets());
        so.setLanguages(getLanguages());
        so.setTemplateVariables(templateDao.loadVariables());
        return so;
    }

    /**
     * Build list of available languages
     * @return
     */
    private List<EnumDTO> getLanguages() {
        List<EnumDTO> languages = new ArrayList<EnumDTO>();
        languages.add(new EnumDTO(Language.EN.name(), "English"));
        languages.add(new EnumDTO(Language.ES.name(), "Spanish"));
        languages.add(new EnumDTO(Language.JA.name(), "Japanese"));
        languages.add(new EnumDTO(Language.KO.name(), "Korean"));
        languages.add(new EnumDTO(Language.AR.name(), "Arabic"));
        languages.add(new EnumDTO(Language.DE.name(), "German"));
        languages.add(new EnumDTO(Language.FR.name(), "French"));
        languages.add(new EnumDTO(Language.IT.name(), "Italian"));
        languages.add(new EnumDTO(Language.PT.name(), "Portuguese"));
        languages.add(new EnumDTO(Language.RU.name(), "Russian"));
        languages.add(new EnumDTO(Language.ZH.name(), "Chinese"));
        return languages;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Product> getProducts(String langCode) {
        return productDao.loadByLang(langCode);
    }

    /**
     * Load template for specified rule
     * @param ruleId
     * @param type
     * @param isKid
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Template getTemplate(long ruleId, String type, boolean isKid) {
        Template.Type templateType = Template.Type.valueOf(type);
        return templateDao.load(ruleId, templateType, isKid);
    }

    /**
     * Load rules history for specified language
     * @param language
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<RuleHistory> getHistory(String language) {
        Language lang = Language.valueOf(language);
        return ruleDao.loadHistory(lang);
    }

    /**
     * Returns list of data tranfer objects for events
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<EventDTO> getEvents() {
        List<Event> events = eventDao.loadAll();
        List<EventDTO> dtoEvents = new ArrayList<EventDTO>();
        for (Event event : events) {
            EventDTO dto = new EventDTO();
            dto.setId(event.getId());
            dto.setName(event.getName());
            dto.setClassName(event.getMessageType().getClassName());
            dtoEvents.add(dto);
        }
        return dtoEvents;
    }

    /**
     * Returns list of data tranfer objects for all rules
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<RuleDTO> getRules() {
        List<Rule> rules = ruleDao.loadAll();
        List<RuleDTO> dtoList = convertRules(rules);
        return dtoList;
    }

    /**
     * Returns list of data tranfer objects for rules with specified language
     * @return
     */
    /*@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<RuleDTO> getRulesByLang(String lang) {
        List<Rule> rules = ruleDao.loadByLang(lang);
        List<RuleDTO> dtoList = convertRules(rules);
        return dtoList;
    }*/
    
    /**
     * Returns list of data tranfer objects for rules with specified language
     * @return
     */
    /**
     * @param lang
     * @param searchKey
     * @param selectedEvent
     * @param selectedProduct
     * @param selectedTicket
     * @param actions
     * @return
     * @throws Exception 
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<RuleDTO> getRulesByLangBySearch(String lang,String searchKey, String selectedEvent, String selectedProduct, String selectedTicket, String actions) throws Exception {
       try{
    	List<Rule> rules = ruleDao.loadByLangBySearchKey(lang,searchKey, selectedEvent, selectedProduct, selectedTicket, actions);
    	 List<RuleDTO> dtoList = convertRules(rules);
         return dtoList;
       }catch(Exception e){
    	  e.printStackTrace();
       }
       return null;
      
    }

    /**
     * Convert list of rules to list of data tranfer objects
     * @return
     */
    private List<RuleDTO> convertRules(List<Rule> rules) {
        List<RuleDTO> dtoList = new ArrayList<RuleDTO>(rules.size());
        for (Rule rule : rules) {
            RuleDTO dto = new RuleDTO();
            dto.setId(rule.getId());
            dto.setVersion(rule.getVersion());//versioning
            dto.setName(rule.getName());
            dto.setActive(rule.getActive());
            
            if(rule.getHoursPriorToSession() !=null)
            dto.setHoursPriorToSession(rule.getHoursPriorToSession().toString());
            
            dto.setGrandfathered(rule.isGrandfathered());
            dto.setSolo(rule.isSolo());
            /*if(rule.isGrandfathered()){
            	dto.setSolo(false);
            }else{
            	dto.setSolo(rule.isSolo());
            }*/
            
            dto.setEventId(rule.getEvent().getId());
            dto.setUpdateSuperTicket(rule.getUpdateSuperTicket());
            dto.setDays(rule.getDays());
            dto.setIgnoreDoNotContact(rule.getIgnoreDoNotContact());
            dto.setProductName(rule.getProduct().getName());
            dto.setSupportLang(rule.getSupportLang().name());
            dto.setActions(Action.toStringSet(rule.getActions()));
            dto.setTickets(convertTickets(rule.getTickets()));
            dto.setSelectedTicketType(rule.getTicketType().name());
            Hibernate.initialize(rule.getTemplates());
            dto.setTemplates(rule.getTemplates());
            dtoList.add(dto);
        }
        Collections.sort(dtoList, new Comparator<RuleDTO>() {
            public int compare(RuleDTO o1, RuleDTO o2) {
                return o2.getId().compareTo(o1.getId());
            }
        });
        return dtoList;
    }

    /**
     * Convert set of tickets to list of data tranfer objects for ticket
     * @return
     */
    private Set<TicketDTO> convertTickets(Set<Ticket> tickets) {
        Set<TicketDTO> dtoSet = new HashSet<TicketDTO>();
        for (Ticket t : tickets) {
            TicketDTO dto = new TicketDTO();
            dto.setType(t.getType().name());
            dto.setSummary(t.getSummary());
            dtoSet.add(dto);
        }
        return dtoSet;
    }

    /**
     * Save html content to session bean for page preview.
     * @param html
     */
    public void saveForPreview(String html) {
        templateHolder.setHtml(html);
    }

    /**
     * Delete rules that marked for removing from DB
     * @param dtoRules - list of data transfer objects
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public List<RuleDTO> deleteRules(List<RuleDTO> dtoRules, String lang,String searchKey, String selectedEvent, String selectedProduct, String selectedTicket, String actions) throws Exception {
    	try{
        // check that user was authentificated
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new Exception("Access denied, user does not authentificated.");
        }
        String user = ((SuccUserDetails) auth.getPrincipal()).getUsername();
        // load current rules from database
        List<Rule> rules = ruleDao.loadByLangBySearchKey(lang,searchKey, selectedEvent, selectedProduct, selectedTicket, actions);
        // build Map ruleId-> Rule
        Map<Long, Rule> rulesMap = new HashMap<Long, Rule>();
        for (Rule rule : rules) {
            rulesMap.put(rule.getId(), rule);
        }

        List<RuleDTO> newRuleList = new ArrayList<RuleDTO>();
        if(isUpdateValid(dtoRules,rulesMap)){
	        for (RuleDTO ruleDTO : dtoRules) {
	            Rule rule = rulesMap.get(ruleDTO.getId());
	            if (ruleDTO.isRemove()) {
	                if (rule != null) {
	                    ruleDao.remove(rule.getId());
	                    rulesMap.remove(rule.getId());
	                    // put log entity to database
	                    logRule(rule, HistoryAction.DELETE, user, rule.getSupportLang());
	                }
	            } else {
	                newRuleList.add(ruleDTO);
	            }
	        }
        }else{
        	throw new InvalidUpdateException();
        }
        return newRuleList;
    	}catch(Exception e){
    		e.printStackTrace();
    		throw e;
    	}
    }

    /**
     * Convert rules from DTO objects to model and save.
     * @param dtoRules
     * @param lang - selected language
     * @return - list of DTO for rules from DB
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public List<RuleDTO> saveRules(List<RuleDTO> dtoRules, String lang,String searchKey, String selectedEvent, String selectedProduct, String selectedTicket, String actions) throws Exception {
    	try{
        // check authentification
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new Exception("Access denied, user does not authentificated.");
        }
        String user = ((SuccUserDetails) auth.getPrincipal()).getUsername();
        // load rules form db
        List<Rule> rules = ruleDao.loadByLangBySearchKey(lang, searchKey, selectedEvent, selectedProduct, selectedTicket, actions);
        // build Map ruleId-> Rule
        Map<Long, Rule> rulesMap = new HashMap<Long, Rule>();
        for (Rule rule : rules) {
            rulesMap.put(rule.getId(), rule);
        }

        List<Rule> newRuleList = new ArrayList<Rule>();
        if(isUpdateValid(dtoRules,rulesMap)){
	        for (RuleDTO ruleDTO : dtoRules) {
	            Rule rule = rulesMap.get(ruleDTO.getId());
//	  rule.setGrandfathered(rule.isSolo());
	            if (ruleDTO.isRemove()) {
	                if (rule != null) {
	                    ruleDao.remove(rule.getId());
	                    rulesMap.remove(rule.getId());
	                    logRule(rule, HistoryAction.DELETE, user, rule.getSupportLang());
	                }
	            } else if (rule != null) {
	                Rule newRule = new Rule();
	                updateRule(ruleDTO, newRule);
	                if (!rule.equals(newRule)) {
	                    updateRule(ruleDTO, rule);
	                    ruleDao.update(rule);
	                    logRule(rule, HistoryAction.UPDATE, user, rule.getSupportLang());
	                }
	                rulesMap.remove(ruleDTO.getId());
	            } else {
	                rule = new Rule();
	                updateRule(ruleDTO, rule);
	                newRuleList.add(0, rule);
	            }
	        }
	        for (Rule r : newRuleList) {
	        	LOG.debug("saving rule now");
	        	ruleDao.create(r);
	            logRule(r, HistoryAction.CREATE, user, r.getSupportLang());
	        }
	
	        // remove rules
	        for (Long key : rulesMap.keySet()) {
	            Rule rule = rulesMap.get(key);
	            ruleDao.remove(key);
	            logRule(rule, HistoryAction.DELETE, user, rule.getSupportLang());
	        }
	        rulesInvoker.updateRules();
	        List<RuleDTO> loadedRules = getRulesByLangBySearch(lang, searchKey, selectedEvent, selectedProduct, selectedTicket,actions);
	        return loadedRules;
        }else{
        	throw new InvalidUpdateException();
        }
    	}catch(Exception e){
    		e.printStackTrace();
    		throw e;
    	}
    }
    
    /**
     * Checks for a valid update. 
     * @param dtoRules rules loaded on client
     * @param rulesMap map of rules from database 
     * @return - Returns true If other user has not updated the rules in data base.  
     */

    public boolean isUpdateValid(List<RuleDTO> dtoRules,Map<Long, Rule> rulesMap){
    	List<RuleDTO> rulesDTOOldList = new ArrayList<RuleDTO>();
        for (RuleDTO ruleDTO : dtoRules) {
        	//LOG.debug(" add  new  "+ruleDTO.isAddNew());
        	if(!ruleDTO.isAddNew()){
        		rulesDTOOldList.add(ruleDTO);
        	}
        }
        //LOG.debug("  rulesDTOOldList "+rulesDTOOldList.size());
        //LOG.debug("  rulesList_fromdb "+rulesMap.size());
        // If rule list without newly added rules at client is not equal to rule list at server then update is invalid.
        if(rulesDTOOldList.size() != rulesMap.size()){
        	return false;
        }
        // If any of the rule has been modified then its version will be different as compared to version of rules in db hence update is invalid.
    	for (RuleDTO ruleDTO : rulesDTOOldList) {
            Rule rule = rulesMap.get(ruleDTO.getId());
            //LOG.debug("  rulesLisFromdb ID "+ruleDTO.getId()+"version"+rule.getVersion());
            if(rule == null){
            	return false;
            }
            else if(rule!= null && !rule.getVersion().equals(ruleDTO.getVersion())){
            	return false;
            }
    	}
    	return true;
    }
    /**
     * Add log entity to DB
     * @param rule
     * @param action
     * @param user
     * @param supportLang
     */
    private void logRule(Rule rule, HistoryAction action, String user, Language supportLang) {
        RuleHistory ruleHistory = new RuleHistory();
        ruleHistory.setCreated(new Date());
        ruleHistory.setRuleId(rule.getId());
        ruleHistory.setRuleName(rule.getName());
        ruleHistory.setLanguage(supportLang);
        ruleHistory.setUsername(user);
        ruleHistory.setAction(action);
        ruleDao.log(ruleHistory);
    }

    /**
     * Copy changes from DTO object to Rule model object
     * @param ruleDTO
     * @param rule
     */
    private void updateRule(RuleDTO ruleDTO, Rule rule) {
        rule.setName(ruleDTO.getName());
        rule.setActive(ruleDTO.isActive());
        rule.setUpdateSuperTicket(ruleDTO.isUpdateSuperTicket());
        rule.setEvent(eventDao.load(ruleDTO.getEventId()));
        rule.setIgnoreDoNotContact(ruleDTO.isIgnoreDoNotContact());
        rule.setProduct(productDao.load(ruleDTO.getProductName()));
        rule.setSupportLang(Language.valueOf(ruleDTO.getSupportLang().toUpperCase()));
        rule.setDays(ruleDTO.getDays());
        rule.setVersion(ruleDTO.getVersion()+1);//versioning
        rule.setHoursPriorToSession(Integer.parseInt(ruleDTO.getHoursPriorToSession()));
        rule.setGrandfathered(ruleDTO.isGrandfathered());
        rule.setSolo(ruleDTO.isSolo());
//        LOG.debug("rule.getVersion"+rule.getVersion());
        rule.setActions(Action.toEnumSet(ruleDTO.getActions()));
        rule.setTicketType(TicketType.valueOf(ruleDTO.getSelectedTicketType()));
        Set<Ticket> oldTickets = rule.getTickets();
        Set<Ticket> tickets = new HashSet<Ticket>();
        // update tickets
        for (TicketDTO ticketDTO : ruleDTO.getTickets()) {
            TicketType type = TicketType.valueOf(ticketDTO.getType());
            Ticket ticket = findOrCreateTicket(oldTickets, type);
            ticket.setSummary(ticketDTO.getSummary());
            tickets.add(ticket);
        }
        rule.setTickets(tickets);
        // update templates
        Set<Template> oldTemplates = rule.getTemplates();
        Set<Template> templates = new HashSet<Template>();
        for (Template template : ruleDTO.getTemplates()) {
            Template oldTemplate = findOrCreateTemplate(oldTemplates, template.getType(), template.getKid());
            oldTemplate.setSender(template.getSender());
            oldTemplate.setSubject(template.getSubject());
            oldTemplate.setBody(template.getBody());
            oldTemplate.setKid(template.getKid());
            templates.add(oldTemplate);
        }

        rule.setTemplates(templates);
    }

    /**
     * Find ticket by type
     * If ticket with specified parameters does not exist, than create it.
     * @param set
     * @param type
     * @return
     */
    private Ticket findOrCreateTicket(Set<Ticket> set, TicketType type) {
        if (set == null) {
            return new Ticket(type);
        }
        for (Ticket t : set) {
            if (t.getType() == type) {
                return t;
            }
        }
        return new Ticket(type);
    }

    /**
     * Find template by type
     * If template with specified parameters does not exist, than create it.
     * @param set
     * @param type
     * @param isKid
     * @return
     */
    private Template findOrCreateTemplate(Set<Template> set, Template.Type type, Boolean isKid) {
        if (set == null) {
            return new Template(type);
        }
        for (Template t : set) {
            if (t.getType() == type && isKid.equals(t.getKid())) {
                return t;
            }
        }
        return new Template(type);
    }

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void setRuleDao(RuleDao ruleDao) {
        this.ruleDao = ruleDao;
    }

    public void setTemplateDao(TemplateDao templateDao) {
        this.templateDao = templateDao;
    }

    public void setRulesInvoker(RulesInvoker rulesInvoker) {
        this.rulesInvoker = rulesInvoker;
    }

    public void setTemplateHolder(TemplateHolder templateHolder) {
        this.templateHolder = templateHolder;
    }
}
