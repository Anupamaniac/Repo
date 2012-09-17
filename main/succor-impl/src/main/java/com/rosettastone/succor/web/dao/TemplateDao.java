package com.rosettastone.succor.web.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.rosettastone.succor.web.model.Template;
import com.rosettastone.succor.web.model.TemplateVariable;

/**
 * The {@code TemplateDao} provides methods for working with {@code Template}.
 */
public class TemplateDao extends HibernateDaoSupport {

    /**
     * Load object by id
     *
     * @param id
     * @return template
     */
    public Template load(long id) {
		return getHibernateTemplate().load(Template.class, new Long(id));
	}

    /**
     * Load template by specified rule, type and kid params.
     *
     * @param ruleId - rule identifier
     * @param type - template type
     * @param isKid - for finding template for kid
     * @return template
     */

	public Template load(long ruleId, Template.Type type, boolean isKid) {
		List<Template> result = getHibernateTemplate().find(
				"from Template where rule_id=? and type=? and kid=?", ruleId,
				type, isKid);
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

    /**
     * Save template object
     *
     * @param template
     */
	public void create(Template template) {
		getHibernateTemplate().save(template);
	}


    /**
     * Update template object
     *
     * @param template
     */
	public void update(Template template) {
		getHibernateTemplate().update(template);
	}


    /**
     * Remove template object by id.
     *
     * @param id
     */
    public void remove(long id) {
		Template template = getHibernateTemplate().load(Template.class,
				new Long(id));
		if (template != null) {
			getHibernateTemplate().delete(template);
		}
	}


    /**
     * Load list of variables for template
     *
     * @return list
     */
    public List<TemplateVariable> loadVariables() {
		return getHibernateTemplate().loadAll(TemplateVariable.class);
	}

}
