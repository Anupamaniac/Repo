package com.rosettastone.succor.parature;

import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.utils.CsrJSONParser;
import com.rosettastone.succor.utils.CustomerJSONParser;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.springframework.beans.factory.annotation.Required;

/**
 * The {@code CsrParatureApi} provides Csr's functionality.
 * User: mixim
 * Date: 29.08.11
 */
public class CsrParatureApi {

    private static final String CSR_URL_EMAIL = "/Csr?_output_=json&Email=";

    private CsrJSONParser csrJsonParser;
    private ParatureHttpClient paratureHttpClient;
    private ParatureProperties paratureProperties;

    private String buildCsrEmailURL(String agent, Customer customer) {
    	return String.format("%s%s", CSR_URL_EMAIL, agent);
	}

   /**
     * Gets a csr by the dedicated agent and sets csrId for customer.
     *
     * @param customer
     * @throws JSONException
     */
    public void setCsrToCustomer(Customer customer) throws JSONException {
        String agent = customer.getDedicatedAgent();
        if(agent!=null && agent.length()!=0){
            String urlC = buildCsrEmailURL(agent, customer);
            String response = paratureHttpClient.sendGetRequest(urlC);
            customer.setCsrId( Long.valueOf(parseCsrResponse(response)) );
        }
    }

    /**
      * Parses id of csr from JSON string.
      *
      * @param response
      * @return
      * @throws JSONException
      */
     String parseCsrResponse(String response) throws JSONException{
//         JSONObject csrObject = customerJsonParser.parseCsrJSON(response);
         JSONObject csrObject = csrJsonParser.parseCsrJSON(response);
         String id = csrObject.getString("@id");
         return id;
     }


    @Required
    public void setParatureProperties(ParatureProperties paratureProperties) {
        this.paratureProperties = paratureProperties;
    }

    @Required
    public void setCsrJsonParser(CsrJSONParser csrJsonParser) {
        this.csrJsonParser = csrJsonParser;
    }

    @Required
    public void setParatureHttpClient(ParatureHttpClient paratureHttpClient) {
        this.paratureHttpClient = paratureHttpClient;
    }

}
