package com.rosettastone.succor.web.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.rosettastone.succor.mdp.MessagePreProcessor;
import com.rosettastone.succor.web.model.Product;

/**
 * The {@code ProductDao} provides methods for working with {@code Product}.
 */
public class ProductDao extends HibernateDaoSupport {

    public static String NAME = "name";

    /**
     * Load Product by id
     * 
     * @param id
     * @return product
     */
    public Product load(String id) {
        return getHibernateTemplate().load(Product.class, id);
    }

    /**
     * Load all Products
     * 
     * @return list
     */
    public List<Product> loadAll() {
        return getHibernateTemplate().loadAll(Product.class);
    }

    /**
     * Load Products for specified language code
     * 
     * @param langCode
     *            - language code, for example en, ko
     * @return list
     */
    @SuppressWarnings("unchecked")
    public List<Product> loadByLang(String langCode) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
        if (langCode.toLowerCase().equals("ko") || langCode.toLowerCase().equals("ja")) {
            return getHibernateTemplate().loadAll(Product.class);
        } else {
            criteria.add(Restrictions.ne(NAME, MessagePreProcessor.REFLEX_PRODUCT_NAME));
            return getHibernateTemplate().findByCriteria(criteria);
        }
    }

    /**
     * Save Product to DB
     * 
     * @param product
     */
    public void create(Product product) {
        getHibernateTemplate().save(product);
    }

    /**
     * Update Product in DB
     * 
     * @param product
     */
    public void update(Product product) {
        getHibernateTemplate().update(product);
    }

    /**
     * Remove Product from DB
     * 
     * @param id
     */
    public void remove(String id) {
        Product product = getHibernateTemplate().load(Product.class, id);
        if (product != null) {
            getHibernateTemplate().delete(product);
        }
    }

}
