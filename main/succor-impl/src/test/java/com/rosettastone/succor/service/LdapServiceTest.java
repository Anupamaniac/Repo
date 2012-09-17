package com.rosettastone.succor.service;

import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Hashtable;

/**
 * User: mixim
 * Date: 11.10.11
 */
@Test
@ContextConfiguration(locations = { "classpath:/test-context.xml" })
public class LdapServiceTest extends AbstractTestNGSpringContextTests {
    @Autowired
    LdapService ldapService;

    private static final Log LOG = LogFactory.getLog(LdapService.class);

    @Test
    void testRosettaConnection(){
        ldapService.setUrl("ldap://10.1.2.40:389");
        Assert.assertEquals(true,ldapService.connectToAD());
    }
    @Test
    void testRosettaSecureConnection(){
        ldapService.setUrl("ldaps://10.1.2.40:636");
        Assert.assertEquals(true,ldapService.connectToAD());
    }

    @Test
    void testModifyUser(){
        Assert.assertEquals("Mikhail\\, Borisov", ldapService.convertUser("Mikhail Borisov"));
    }

    @Test
    void testModifyUserLong(){
        Assert.assertEquals("Ali\\, Mohammed Sarfaraz", ldapService.convertUser("Ali Mohammed Sarfaraz"));
    }

    @Test
    public void testRosettaAccess(){
        ldapService.setAccessGroup("~RS Success Correspondence - Users");
        Assert.assertEquals(true,ldapService.checkUserAccess("Borisov Mikhail"));
    }
    @Test
    public void testRosettaAccessI(){
        ldapService.setAccessGroup("~RS Coach Portal - T1 Leads");
        Assert.assertEquals(true,ldapService.checkUserAccess("Ali Mohammed Sarfaraz"));
    }

}
