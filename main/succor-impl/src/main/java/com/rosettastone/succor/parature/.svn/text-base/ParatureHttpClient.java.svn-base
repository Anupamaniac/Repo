package com.rosettastone.succor.parature;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import com.rosettastone.succor.exception.ApplicationException;
import com.rosettastone.succor.exception.ParatureErrorCode;
import com.rosettastone.succor.exception.ParatureException;
import com.rosettastone.succor.utils.UnicodeHandlerHttpResponseException;
import com.rosettastone.succor.utils.UnicodeResponseHandler;

/**
 * The {@code ParatureHttpClient} provides the methods for interaction with parature.
 */
public class ParatureHttpClient {

    private static final Log LOG = LogFactory.getLog(ParatureHttpClient.class);
    private static final String PARAM_NAME_TOKEN = "_token_";

    private String baseUrl;
    private String accountId;
    private String departmentId;
    private String authToken;

    private HttpClient httpClient;

    private enum RequestMethod {
        GET, POST, PUT, DELETE
    };

    @SuppressWarnings("unchecked")
    public String sendGetRequest(String urlTxt) {
        return sendHtmlRequest(urlTxt, RequestMethod.GET, null, Collections.EMPTY_LIST, Collections.EMPTY_LIST);
    }

    /**
     * Update object.
     * 
     * @param urlTxt
     * @param bodyStream
     * @return
     */
    @SuppressWarnings("unchecked")
    public String sendPutRequest(String urlTxt, InputStream bodyStream) {
        return sendHtmlRequest(urlTxt, RequestMethod.PUT, bodyStream, Collections.EMPTY_LIST, Collections.EMPTY_LIST);
    }

    /**
     * Create object.
     * 
     * @param urlTxt
     * @param bodyStream
     * @return
     */
    @SuppressWarnings("unchecked")
    public String sendPostRequest(String urlTxt, InputStream bodyStream) {
        return sendHtmlRequest(urlTxt, RequestMethod.POST, bodyStream, Collections.EMPTY_LIST, Collections.EMPTY_LIST);
    }

    /**
     * Delete object.
     * 
     * @param urlTxt
     * @return
     */
    @SuppressWarnings("unchecked")
    public String sendDeleteRequest(String urlTxt) {
        return sendHtmlRequest(urlTxt, RequestMethod.DELETE, null, Collections.EMPTY_LIST, Collections.EMPTY_LIST);
    }

    /**
     * Sends HTML request.
     * 
     * @param urlTxt
     *            URL where to send
     * @param requestMethod
     *            method (GET/POST/PUT)
     * @param bodyStream
     *            body (optional)
     * @param headers
     *            headers (optional)
     * @return content of response
     */
    private String sendHtmlRequest(String urlTxt, RequestMethod requestMethod, InputStream bodyStream,
            Collection<Header> headers, Collection<NameValuePair> parameters) {
        String response = null;
        try {
            HttpRequestBase request = createRequest(urlTxt, requestMethod, bodyStream);
            request.setParams(createRequestParameters(parameters));

            ResponseHandler<String> responseHandler = new UnicodeResponseHandler();
            LOG.debug(requestMethod + " " + request.getRequestLine().getUri());
            response = httpClient.execute(request, responseHandler);
            // LOG.debug("Response: " + response);
            return response;
        } catch (HttpResponseException ex) {
            LOG.debug("HTTP response error", ex);
            if (ex instanceof UnicodeHandlerHttpResponseException) {
                response = ((UnicodeHandlerHttpResponseException) ex).getResponse();
            }
            String errorMessage = ParatureErrorMessageBuilder.buildException(ex.getMessage(), generateUrl(urlTxt),
                    bodyStream, response);
            switch (ex.getStatusCode()) {
            case HttpStatus.SC_BAD_REQUEST:
                throw new ParatureException(errorMessage, ParatureErrorCode.BAD_REQUEST_400);
            case HttpStatus.SC_UNAUTHORIZED:
                throw new ParatureException(errorMessage, ParatureErrorCode.UNAUTORIZED_401);
            case HttpStatus.SC_FORBIDDEN:
                throw new ParatureException(errorMessage, ParatureErrorCode.FORBIDDEN_403);
            case HttpStatus.SC_NOT_FOUND:
                throw new ParatureException(errorMessage, ParatureErrorCode.NOT_FOUND_404);
            case HttpStatus.SC_INTERNAL_SERVER_ERROR:
                throw new ParatureException(errorMessage, ParatureErrorCode.INTERNAL_SERVER_ERROR_500);
            case HttpStatus.SC_BAD_GATEWAY:
                throw new ParatureException(errorMessage, ParatureErrorCode.BAD_GATEWAY_502);
            case HttpStatus.SC_SERVICE_UNAVAILABLE:
                throw new ParatureException(errorMessage, ParatureErrorCode.SERVICE_UNAVAILABLE_503);
            default:
                throw new ApplicationException("HTTP Request error.\n" + errorMessage, ex);
            }
        } catch (Exception ex) {
            String errorMessage = ParatureErrorMessageBuilder.buildException(ex.getMessage(), generateUrl(urlTxt),
                    bodyStream, response);
            throw new ApplicationException("HTTP Request error.\n" + errorMessage, ex);
        }
    }

    /**
     * Creates HTTP request base.
     *
     * @param urlTxt url for request
     * @param requestMethod method of request
     * @param body body for request
     * @return base base for http request
     */
    private HttpRequestBase createRequest(String urlTxt, RequestMethod requestMethod, InputStream body) {
        HttpRequestBase httpRequest = null;
        String url = generateUrl(urlTxt);
        switch (requestMethod) {
        case POST:
            HttpPost post = new HttpPost(url);
            if (body != null) {
                HttpEntity entity = new InputStreamEntity(body, -1);
                post.setHeader("content-type", "text/xml;charset=UTF-8");
                post.setEntity(entity);
            }
            httpRequest = post;
            break;
        case GET:
            httpRequest = new HttpGet(url);
            break;
        case PUT:
            HttpPut put = new HttpPut(url);
            if (body != null) {
                HttpEntity entity = new InputStreamEntity(body, -1);
                put.setEntity(entity);
            }
            httpRequest = put;
            break;
        case DELETE:
            httpRequest = new HttpDelete(url);
            break;
        default:
            throw new ApplicationException("Unsupported request method: " + requestMethod);
        }
        return httpRequest;
    }

    /**
     *
     * Generates URL.
     *
     * @param urlTxt
     * @return url
     */
    public String generateUrl(String urlTxt) {
        String urlPrefix = baseUrl + "/api/v1/" + accountId + "/" + departmentId;
        String urlSuffix;
        if (urlTxt.contains("?")) {
            urlSuffix = "&" + PARAM_NAME_TOKEN + "=" + authToken;
        } else {
            urlSuffix = "?" + PARAM_NAME_TOKEN + "=" + authToken;
        }
        return urlPrefix + urlTxt + urlSuffix;
    }

    /**
     * Creates parameters for request.
     *
     * @param parameters
     * @return http params
     */
    private HttpParams createRequestParameters(Collection<NameValuePair> parameters) {
        HttpParams httpParams = new BasicHttpParams();
        for (NameValuePair nameValuePair : parameters) {
            httpParams.setParameter(nameValuePair.getName(), nameValuePair.getValue());
        }
        return httpParams;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
