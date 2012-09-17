package com.rosettastone.succor.web.sso;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.authentication.AttributePrincipalImpl;
import org.jasig.cas.client.proxy.Cas20ProxyRetriever;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.jasig.cas.client.proxy.ProxyRetriever;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.XmlUtils;
import org.jasig.cas.client.validation.AbstractCasProtocolUrlBasedTicketValidator;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.AssertionImpl;
import org.jasig.cas.client.validation.TicketValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * The {@code SuccCas20ServiceTicketValidator} provides validation of ticket.
 */
public class SuccCas20ServiceTicketValidator extends AbstractCasProtocolUrlBasedTicketValidator {

    /** The CAS 2.0 protocol proxy callback url. */
    private String proxyCallbackUrl;

    /** The storage location of the proxy granting tickets. */
    private ProxyGrantingTicketStorage proxyGrantingTicketStorage;

    /** Implementation of the proxy retriever. */
    private ProxyRetriever proxyRetriever;

    /**
     * Constructs an instance of the CAS 2.0 Service Ticket Validator with the
     * supplied CAS server url prefix.
     *
     * @param casServerUrlPrefix
     *            the CAS Server URL prefix.
     */
    public SuccCas20ServiceTicketValidator(final String casServerUrlPrefix) {
        super(casServerUrlPrefix);
        this.proxyRetriever = new Cas20ProxyRetriever(casServerUrlPrefix);
    }

    @Override
    protected String getUrlSuffix() {
        return "serviceValidate";
    }

    /**
     * Creates assertion from response.
     *
     * @param response
     * @return assertion
     * @throws TicketValidationException
     */
    @Override
    protected Assertion parseResponseFromServer(String response) throws TicketValidationException {
        final String error = XmlUtils.getTextForElement(response, "authenticationFailure");

        if (CommonUtils.isNotBlank(error)) {
            throw new TicketValidationException(error);
        }

        final String principal = XmlUtils.getTextForElement(response, "user");
        final String proxyGrantingTicketIou = XmlUtils.getTextForElement(response, "proxyGrantingTicket");
        final String proxyGrantingTicket = this.proxyGrantingTicketStorage != null ? this.proxyGrantingTicketStorage
                .retrieve(proxyGrantingTicketIou) : null;

        if (CommonUtils.isEmpty(principal)) {
            throw new TicketValidationException("No principal was found in the response from the CAS server.");
        }

        final Assertion assertion;
        Map<String, String> wholeAttributes = new HashMap<String, String>();
        wholeAttributes.putAll(extractCustomAttributes(response));
        String[] attributesNames = new String[] {"cn", "mail"};
        wholeAttributes.putAll(extractCustomAttributesNotWellFormed(response, Arrays.asList(attributesNames)));
        if (CommonUtils.isNotBlank(proxyGrantingTicket)) {
            final AttributePrincipal attributePrincipal = new AttributePrincipalImpl(principal, wholeAttributes,
                    proxyGrantingTicket, this.proxyRetriever);
            assertion = new AssertionImpl(attributePrincipal);
        } else {
            assertion = new AssertionImpl(new AttributePrincipalImpl(principal, wholeAttributes));
        }

        customParseResponse(response, assertion);

        return assertion;
    }

    /**
     * Gets user name from custom attributes.
     *
     * @param xml
     * @return map
     */
    protected Map extractCustomAttributes(final String xml) {
        final int pos1 = xml.indexOf("<cas:attributes>");
        final int pos2 = xml.indexOf("</cas:attributes>");

        if (pos1 == -1) {
            return Collections.EMPTY_MAP;
        }

        final String attributesText = xml.substring(pos1+16, pos2);

        final Map attributes = new HashMap();
        final BufferedReader br = new BufferedReader(new StringReader(attributesText));

        String line;
        final List attributeNames = new ArrayList();
        try {
            while ((line = br.readLine()) != null) {
                final String trimmedLine = line.trim();
                if (trimmedLine.length() > 0) {
                    final int leftPos = trimmedLine.indexOf(":");
                    final int rightPos = trimmedLine.indexOf(">");
                    attributeNames.add(trimmedLine.substring(leftPos+1, rightPos));
                }
            }
            br.close();
        } catch (final IOException e) {
            //ignore
        }

        for (final Iterator iter = attributeNames.iterator(); iter.hasNext();) {
            final String name = (String) iter.next();
            attributes.put(name, XmlUtils.getTextForElement(xml, name));
        }

        return attributes;
    }

    /**
     * Creates map from custom attributes with values from xml.
     *
     * @param xml
     * @param attributesList
     * @return map
     */
    protected Map extractCustomAttributesNotWellFormed(final String xml, Collection<String> attributesList) {
        final Map attributes = new HashMap();
        for (final Iterator iter = attributesList.iterator(); iter.hasNext();) {
            final String name = (String) iter.next();
            attributes.put(name, XmlUtils.getTextForElement(xml, name));
        }

        return attributes;
    }

    /**
     * Adds the pgtUrl to the list of parameters to pass to the CAS server.
     *
     * @param urlParameters
     *            the Map containing the existing parameters to send to the
     *            server.
     */
    protected final void populateUrlAttributeMap(final Map urlParameters) {
        urlParameters.put("pgtUrl", encodeUrl(this.proxyCallbackUrl));
    }

    /**
     * Template method if additional custom parsing (such as Proxying) needs to
     * be done.
     *
     * @param response
     *            the original response from the CAS server.
     * @param assertion
     *            the partially constructed assertion.
     * @throws TicketValidationException
     *             if there is a problem constructing the Assertion.
     */
    protected void customParseResponse(final String response, final Assertion assertion)
            throws TicketValidationException {
        // nothing to do
    }

    public final void setProxyCallbackUrl(final String proxyCallbackUrl) {
        this.proxyCallbackUrl = proxyCallbackUrl;
    }

    public final void setProxyGrantingTicketStorage(final ProxyGrantingTicketStorage proxyGrantingTicketStorage) {
        this.proxyGrantingTicketStorage = proxyGrantingTicketStorage;
    }

    public final void setProxyRetriever(final ProxyRetriever proxyRetriever) {
        this.proxyRetriever = proxyRetriever;
    }
}
