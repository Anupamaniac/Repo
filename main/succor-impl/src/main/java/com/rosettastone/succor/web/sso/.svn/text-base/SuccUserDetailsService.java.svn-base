package com.rosettastone.succor.web.sso;

import org.jasig.cas.client.validation.Assertion;
import org.springframework.security.cas.userdetails.AbstractCasAssertionUserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom authenticationUserDetailsService for CasAuthenticationProvider.
 */
public class SuccUserDetailsService extends AbstractCasAssertionUserDetailsService{

    private static final String NON_EXISTENT_PASSWORD_VALUE = "NO_PASSWORD";

    private String[] attributes;

    private boolean convertToUpperCase = true;

    /**
     * Constructs SuccUserDetailsService from array of attributes.
     * @param attributes
     */
    public SuccUserDetailsService(String[] attributes) {
        Assert.notNull(attributes, "attributes cannot be null.");
        Assert.isTrue(attributes.length > 0, "At least one attribute is required to retrieve roles from.");
        this.attributes = attributes;
    }

    /**
     * Creates {@code SuccUserDetails} object from Assertion object.
     *
     * @param assertion
     * @return userDetails
     */
    @Override
    protected UserDetails loadUserDetails(Assertion assertion) {
        final List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

        for (final String attribute : this.attributes) {
            final Object value = assertion.getPrincipal().getAttributes().get(attribute);

            if (value == null) {
                continue;
            }

            if (value instanceof List) {
                final List list = (List) value;

                for (final Object o : list) {
                    grantedAuthorities.add(new GrantedAuthorityImpl(this.convertToUpperCase ? o.toString().toUpperCase() : o.toString()));
                }

            } else {
                grantedAuthorities.add(new GrantedAuthorityImpl(this.convertToUpperCase ? value.toString().toUpperCase() : value.toString()));
            }

        }

        String mail = (String)assertion.getPrincipal().getAttributes().get("mail");
        if (mail != null && mail.length() > 0) {
            mail = processValue(mail);
        }

        String fullName = (String)assertion.getPrincipal().getAttributes().get("cn");
        if (fullName != null && fullName.length() > 0) {
            fullName = processValue(fullName);
        }

        SuccUserDetails userDetails = new SuccUserDetails();

        userDetails.setAccountNonExpired(true);
        userDetails.setUsername(assertion.getPrincipal().getName());
        userDetails.setAccountNonLocked(true);
        userDetails.setAuthorities(grantedAuthorities);
        userDetails.setCredentialsNonExpired(true);
        userDetails.setEnabled(true);
        userDetails.setPassword(NON_EXISTENT_PASSWORD_VALUE);
        userDetails.setFullName(fullName);
        userDetails.setEmail(mail);

        return userDetails;
    }

    private String processValue(String value) {
        String removalPattern = "--- \n-";
        int pos = value.indexOf(removalPattern);
        if (pos != -1) {
            return value.replace(removalPattern, "").trim();
        }
        return value;
    }

    public void setConvertToUpperCase(final boolean convertToUpperCase) {
        this.convertToUpperCase = convertToUpperCase;
    }

}
