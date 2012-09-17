package com.rosettastone.succor.service.sms;

import com.rosettastone.succor.exception.SMSException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * User: Nikolay Sazonov
 * Date: 5/10/11
 */
@Test
@ContextConfiguration(locations = {
        "classpath:/test-context.xml"})
public class SMSWebServiceTest extends AbstractTestNGSpringContextTests {

    private HttpClient httpClient;

    @BeforeTest
    private void setUp() {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", new PlainSocketFactory(), 80));
        HttpParams params = new BasicHttpParams();
        params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(5));
        params.setParameter(ConnManagerPNames.TIMEOUT, 60 * 1000L);
        ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
        httpClient = new DefaultHttpClient(cm, params);

    }


    @Test(enabled = false)
    public void testWebService() throws SMSException, IOException {
        SMSRequestVO vo = new SMSRequestVO();
        vo.setPhone("79231816463");
        vo.setLocale("FR");
        vo.setText("privet");
        String serialized = new ObjectMapper().writeValueAsString(vo);
        StringEntity entity = new StringEntity(serialized, HTTP.UTF_8);
        HttpPost httppost = new HttpPost("http://localhost:8080/succor-impl-2.0/sms/");
        httppost.setEntity(entity);
        String str = sendRequest(httppost);
        Assert.assertFalse(StringUtils.isEmpty(str));
    }

    protected String sendRequest(HttpUriRequest request) throws SMSException {
        try {
            request.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(request);
            request.setHeader("Accept", "application/json");
            StatusLine responseStatus = response.getStatusLine();
            if (responseStatus.getStatusCode() / 100 != 2) {
                throw new RuntimeException("Error on SMS service " + responseStatus.getStatusCode());
            }
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity);
            return responseBody;
        } catch (ClientProtocolException e) {
            throw new SMSException(e);
        } catch (IOException e) {
            throw new SMSException(e);
        }
    }

}
