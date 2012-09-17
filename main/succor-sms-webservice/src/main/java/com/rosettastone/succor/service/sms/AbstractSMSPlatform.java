package com.rosettastone.succor.service.sms;

import com.rosettastone.succor.exception.SMSException;
import com.rosettastone.succor.service.SMSPlatform;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Required;

import java.io.IOException;

/**
 * User: Nikolay Sazonov
 * Date: 5/5/11
 *
 * Base abstract class for SMS .
 * We have different implementations for each mobile provider.
 */
abstract public class AbstractSMSPlatform implements SMSPlatform {

    private static final Log LOG = LogFactory.getLog(AbstractSMSPlatform.class);

    /**
     * Username for provider API
     */
    protected String username;

    /**
     * Password for provider API
     */
    protected String password;

    protected String protocol;

    /**
     * URL for provider API
     */
    protected String url;

    protected HttpClient httpClient;

    /**
     * Init method that will be called by spring after bean creation
     */
    public void init() {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", new PlainSocketFactory(), 80));
        HttpParams params = new BasicHttpParams();
        params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(5));
        params.setParameter(ConnManagerPNames.TIMEOUT, 60 * 1000L);
        ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
        httpClient = new DefaultHttpClient(cm, params);
    }

    /**
     * Send request to SMS provider API, and check response.
     *
     * @param request
     * @return response body
     * @throws SMSException
     */
    protected String sendRequest(HttpUriRequest request) throws SMSException {
        LOG.trace(request.getURI().toString());
        try {
            HttpResponse response = httpClient.execute(request);
            StatusLine responseStatus = response.getStatusLine();
            LOG.trace(responseStatus.toString());
            if (responseStatus.getStatusCode() / 100 != 2) {
                throw new SMSException("Error on SMS service " + responseStatus.getStatusCode());
            }
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity);
            LOG.trace(responseBody);
            return responseBody;
        } catch (ClientProtocolException e) {
            throw new SMSException(e);
        } catch (IOException e) {
            throw new SMSException(e);
        }
    }

    @Required
    public void setUsername(String username) {
        this.username = username;
    }

    @Required
    public void setPassword(String password) {
        this.password = password;
    }

    @Required
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Required
    public void setUrl(String url) {
        this.url = url;
    }

}
