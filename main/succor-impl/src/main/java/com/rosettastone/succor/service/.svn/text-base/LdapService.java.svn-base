package com.rosettastone.succor.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

/**
 * User: mixim
 * Date: 07.10.11
 */
public class LdapService {

    private String url = "ldap://10.1.2.40:389";
    private String principal = "linuxldap@rosettastone.local";
    private String credentials = "IH2v3N0P2ssw0rd";

    private String accessGroup = "~RS Success Correspondence - Users";

    private String dC = "dc=rosettastone,dc=local";
    private String oU = "OU=Consultants,OU=Corporate";

    private String attrName = "memberOf";

    LdapContext ctx;

    private static final Log LOG = LogFactory.getLog(LdapService.class);

    /**
     * Connects to Active Directory .
     *
     * @return boolean result of connection.
     */
    boolean connectToAD() {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "Simple");
        env.put("java.naming.ldap.attributes.binary","tokenGroups");
        env.put(Context.SECURITY_PRINCIPAL, principal);
        env.put(Context.SECURITY_CREDENTIALS, credentials);
        env.put(Context.PROVIDER_URL, url);
        try {
            ctx = new InitialLdapContext(env,null);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        if (ctx!=null) {
            return true;
        }
        return false;
    }

    /**
     * Checks the user's permission.
     *
     * @param user string format "Surname Name"
     * @return boolean test result
     */
    public boolean checkUserAccess(String user) {
        if (connectToAD()) {
          SearchControls userSearchCtls = new SearchControls();
          userSearchCtls.setSearchScope(SearchControls.OBJECT_SCOPE);
          String userSearchFilter = "(objectClass=user)";
          user = convertUser(user);
          String userSearchBase = "CN=" + user  + "," + oU + "," + dC;
          String userReturnedAtts[]={attrName};
          userSearchCtls.setReturningAttributes(userReturnedAtts);
          try {
            NamingEnumeration userAnswer = ctx.search(userSearchBase, userSearchFilter, userSearchCtls);
            while (userAnswer.hasMoreElements()) {
                SearchResult sr = (SearchResult)userAnswer.next();
                Attributes attrs = sr.getAttributes();
                if (attrs != null) {
                    try {
                        for (NamingEnumeration ae = attrs.getAll();ae.hasMore();) {
                            Attribute attr = (Attribute)ae.next();
                            for (NamingEnumeration e = attr.getAll();e.hasMore();) {
                                String group = (String) e.next();
                                if (getGroupName(group).equals(accessGroup)) {
                                    return true;
                                }
                            }
                        }
                    } catch (NamingException e) {
                        LOG.error("Problem listing membership: " + e);
                    }
                }
}
          } catch (NamingException e) {
            e.printStackTrace();
          }
      }
      return false;
    }

    /**
     * Adds "\," characters before space.
     *
     * @param user
     * @return string with added "\," characters.
     */
    String convertUser(String user) {
        if(user.indexOf(" ")!=-1){
            user = user.replaceFirst("\\s", "\\\\, ");
        }
        return user;
    }

    private String getGroupName(String str){
        str = str.substring( (str.indexOf("=")+1), str.indexOf(","));
        return str;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setdC(String dC) {
        this.dC = dC;
    }

    public void setoU(String oU) {
        this.oU = oU;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public void setAccessGroup(String accessGroup) {
        this.accessGroup = accessGroup;
    }
}
