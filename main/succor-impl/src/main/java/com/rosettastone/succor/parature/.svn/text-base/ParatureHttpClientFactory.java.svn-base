package com.rosettastone.succor.parature;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.springframework.beans.factory.annotation.Required;

public class ParatureHttpClientFactory {

    private static final int HTTP_PORT = 80;
    private static final int HTTPS_PORT = 443;
    private static final int REQUEST_TIMEOUT = 15000;

    private HttpClient httpClient;
    private ParatureProperties paratureProperties;

    @PostConstruct
    public void init() throws IOException {
        HttpParams params = new BasicHttpParams();
        params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, REQUEST_TIMEOUT);
        params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, REQUEST_TIMEOUT);

        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), HTTP_PORT));
        registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), HTTPS_PORT));

        httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(params, registry), params);
    }

    @PreDestroy
    public void shutdown() {
        if (httpClient != null) {
            httpClient.getConnectionManager().shutdown();
        }
    }

    public ParatureHttpClient getHttpClient() {
        ParatureHttpClient client = new ParatureHttpClient();
        client.setHttpClient(httpClient);
        client.setAccountId(paratureProperties.getAccountId());
        client.setAuthToken(paratureProperties.getAuthToken());
        client.setBaseUrl(paratureProperties.getBaseUrl());
        client.setDepartmentId(paratureProperties.getDepartmentId());

        return client;
    }

    @Required
    public void setParatureProperties(ParatureProperties paratureProperties) {
        this.paratureProperties = paratureProperties;
    }
}
