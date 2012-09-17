package com.rosettastone.succor.utils;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

/**
 * User: mixim
 * Date: 29.08.11
 */
public class CsrJSONParser {

    /**
      * Parses Csr JSON from response.
      * @param response
      * @return  JSONObject
      * @throws org.apache.sling.commons.json.JSONException
      */
     public JSONObject parseCsrJSON(String response) throws JSONException {
         JSONObject json = new JSONObject(response);
         JSONObject entities = json.getJSONObject("Entities");
         if (entities.getInt("@total") == 0) {
             throw new NullPointerException();
         }
         JSONObject csrJSON = entities.getJSONArray("Csr")
                 .getJSONObject(0);
         return csrJSON;
     }

}
