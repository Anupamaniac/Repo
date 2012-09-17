package com.rosettastone.succor.bandit;

import com.rosettastone.succor.model.bandit.License;
import com.rosettastone.succor.model.bandit.ProductRights;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import java.util.*;

/**
 * Converter for License part of JSON message
 */
public class LicenseConverter implements JsonConverter<License> {

    private static final long SECOND = 1000;

    private JsonConverter<ProductRights> rightsConverter = new ProductRightsConverter();

    @Override
    public License convert(JSONObject json) throws JSONException {
        License license = new License();
        if (json.has("test_license")) {
            license.setTestLicense(json.getBoolean("test_license"));
        }
        if (json.has("updaded_at")) {
            long secondSinceEpoch = json.getLong("updaded_at");
            license.setUpgradedAt(new Date(secondSinceEpoch * SECOND));
        }
        if (json.has("creation_account_guid")) {
            license.setCreationAccountGuid(json.getString("creation_account_guid"));
        }
        if (json.has("creation_account_type")) {
            license.setCreationAccountType(json.getString("creation_account_type"));
        }
        if (json.has("product_rights")) {
            JSONArray array = json.getJSONArray("product_rights");
            List<ProductRights> list = new ArrayList<ProductRights>(array.length());
            for (int i = 0; i < array.length(); i++) {
                ProductRights rights = rightsConverter.convert(array.getJSONObject(i));
                list.add(rights);
            }
            license.setProductRights(list);
        } else {
            license.setProductRights(Collections.<ProductRights>emptyList());
        }
        return license;
    }

}
