package com.rosettastone.succor.web.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rosettastone.succor.web.model.TicketType;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.rosettastone.succor.web.model.Language;
import com.rosettastone.succor.web.model.Rule;
import com.rosettastone.succor.web.model.RuleHistory;

/**
 * The {@code RuleDao} provides methods for working with {@code Rule}.
 */
public class RuleDao extends HibernateDaoSupport {

    public static final String SUPPORT_LANG = "supportLang";
    public static final String ACTIVE = "active";
    public static final String TICKET_TYPE = "ticketType";
    public static final String PRODUCT_NAME = "product.name";
    public static final String NAME = "name";
    public static final String EVENT_ID="event.id";

    /**
     * Load all rules from DB
     *
     * @return list of rules
     */

    public List<Rule> loadAll() {
        return getHibernateTemplate().loadAll(Rule.class);
    }

    /**
     * Load all active Rules
     *
     * @return list
     */
    @SuppressWarnings("unchecked")
    public List<Rule> loadActive() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Rule.class);
        criteria.add(Restrictions.eq(ACTIVE, true));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Load Rule by id
     *
     * @param id - rule id
     * @return rule
     */
    public Rule load(long id) {
        return getHibernateTemplate().load(Rule.class, new Long(id));
    }

    /**
     * Search rules by Lang 2 letter code
     * 
     * @param lang - language code(en, ja, ko)
     * @return list
     */
  /*  @SuppressWarnings("unchecked")
    public List<Rule> loadByLang(String lang) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Rule.class);
        criteria.add(Restrictions.eq(SUPPORT_LANG, Language.valueOf(lang.toUpperCase())));
        return getHibernateTemplate().findByCriteria(criteria);
    }*/

    /**
     * Save rule object
     *
     * @param rule
     */
    public void create(Rule rule) {
        getHibernateTemplate().save(rule);
    }

    /**
     * Add history entity about adding/updating/removing some rule
     *
     * @param ruleHistory - history object
     */
    public void log(RuleHistory ruleHistory) {
        getHibernateTemplate().save(ruleHistory);
    }

    /**
     * Search rules by Language by search criteria letter code
     * 
     * @param lang - language code(en, ja, ko)
     * @return list
     */
    @SuppressWarnings("unchecked")
    public List<Rule> loadByLangBySearchKey(String lang,String searchKey, String selectedEvent, String selectedProduct, String selectedTicket, String actions) throws Exception {

        DetachedCriteria criteria = DetachedCriteria.forClass(Rule.class);
       	try{
        criteria.add(Restrictions.eq(SUPPORT_LANG, Language.valueOf(lang.toUpperCase())));
	        criteria.add( Restrictions.ilike(NAME, "%"+searchKey.trim()+"%") );
        
        if(selectedProduct != null && !selectedProduct.equals("All Products")){
        	criteria.add( Restrictions.eq(PRODUCT_NAME, selectedProduct));
        }
        
        if(selectedEvent != null && !selectedEvent.equals("All Events")){
        	criteria.add( Restrictions.eq(EVENT_ID, Long.valueOf(selectedEvent)));
        }
        
	        if(selectedTicket != null && !selectedTicket.equals("All Ticket Types")){
	        	criteria.add(Restrictions.eq(TICKET_TYPE, TicketType.valueOf(selectedTicket)));
	        }
	        if(actions != null && !actions.isEmpty() && !actions.equals("No Action")){
	        	List rule = addActionCriteria(actions, criteria);
	        	
	        	
	        	 if(rule != null && !rule.isEmpty()){
	             	criteria.add(Restrictions.in("id", rule));
	             }else {
	             	return new ArrayList<Rule>();
	             	}
	        }
			        
    	}catch(Exception e){
    		e.printStackTrace();
    		throw new Exception();
    	}
        return getHibernateTemplate().findByCriteria(criteria);
    }
    
     public List addActionCriteria(String actions, DetachedCriteria criteria) {
   
        /*Long length = Long.valueOf(actions.split("#").length)-1;
        String[] str = actions.split("#");
        String selectedActions ="";
        if(length>=1){
        	selectedActions =str[1];
	        for(int i=2;i<=length;i++){
	        	selectedActions = selectedActions +"','"+str[i];
	        }
        }*/
        		
 //       Query query = getSessionFactory().openSession().createQuery("select ruleId from RuleAction  where  ruleId not in (select ruleId from RuleAction where action not in('"+selectedActions+"')) and  action IN('"+selectedActions+"') group by ruleId having (count(distinct action))>="+length);
        Query query = getSessionFactory().openSession().createQuery("select ruleId from RuleAction  where action IN ('"+ /*selectedActions*/ actions+"') group by ruleId having (count(distinct action))>= 1"/*+length*/);
        return query.list();
       
    }
    
    /**
     * Load list of history objects about rules for specified language code
     *
     * @param language - language code (en, ko)
     * @return list of history objects
     */
    public List<RuleHistory> loadHistory(final Language language) {
        return getHibernateTemplate().executeFind(new HibernateCallback<List<RuleHistory>>() {
            @Override
            public List<RuleHistory> doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(RuleHistory.class)
                        .add(Restrictions.eq("language", language)).addOrder(Order.desc("created"));
                return criteria.list();
            }
        });
    }

    /**
     * Update rule
     *
     * @param rule
     */
    public void update(Rule rule) {
        getHibernateTemplate().update(rule);
    }

    /**
     * Remove rule from DB by id
     *
     * @param id
     */
    public void remove(long id) {
        Rule rule = getHibernateTemplate().load(Rule.class, new Long(id));
        if (rule != null) {
            getHibernateTemplate().delete(rule);
        }
    }

    /**
     * Loads ticket summary field for specified rule and ticket type
     *
     * @param rule_id - rule identifier
     * @param type - ticket type
     * @return summary field
     */
    public String loadTicketSummary(long rule_id, TicketType type) {
        List<String> result = getHibernateTemplate().find("select summary from Ticket where rule_id=? and type=?",
                rule_id, type);
        if (result.size() > 0) {
            return result.get(0);
        }
        return "";
    }

}
