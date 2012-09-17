package com.rosettastone.succor.bandit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rosettastone.succor.model.bandit.License;
import com.rosettastone.succor.model.bandit.ProductRights;
import com.rosettastone.succor.model.bandit.Range;

/**
 * This generator calculates product name by available ranges
 * @link
 *
 */
public class ProductNameGenerator {

    private static final Map<Range, String> rangeMap = new HashMap<Range, String>();

    static {
        rangeMap.put(new Range(1, 1), "U1");
        rangeMap.put(new Range(1, 4), "L1");
        rangeMap.put(new Range(5, 8), "L2");
        rangeMap.put(new Range(9, 12), "L3");
        rangeMap.put(new Range(13, 16), "L4");
        rangeMap.put(new Range(17, 20), "L5");
        rangeMap.put(new Range(1, 8), "S2");
        rangeMap.put(new Range(1, 12), "S3");
        rangeMap.put(new Range(1, 20), "S5");
        rangeMap.put(new Range(1, 16), "L4");
    }

    public String generateProductName(License license, String rsLangCode) {
        if (license == null) {
            return null;
        }
        List<ProductRights> rightsList = license.getProductRights();
        if (rightsList == null || rightsList.isEmpty()) {
            return null;
        }
        ProductRights premiumRights = null;
        ProductRights applicationRight = null;
        for (ProductRights rights : rightsList) {
        	if (rights.getFamily().equalsIgnoreCase("eschool_group_sessions")&& rights.getLanguageCode().equals(rsLangCode)){
        		 license.setGrandfathered(false);
        	}
        	if (rights.getFamily().equalsIgnoreCase("eschool_one_on_one_sessions")&& rights.getLanguageCode().equals(rsLangCode)){
       		 license.setSoloRight(true);
        	}
            if (rights.getFamily().equalsIgnoreCase("premium_community")) {
                if (rsLangCode == null || rsLangCode.equals(rights.getLanguageCode())) {
                    premiumRights = rights;
                }
            } else if (rights.getFamily().equalsIgnoreCase("application")) {
                if (rsLangCode == null || rsLangCode.equals(rights.getLanguageCode())) {
                    applicationRight = rights;
                }
            }
        }

        return getProductName(applicationRight, premiumRights, license.getCreationAccountType());
    }

    private String getProductName(ProductRights applicationRights, ProductRights premiumCommunity, String type) {
        if (premiumCommunity != null && !premiumCommunity.getRange().isEmpty()) {
            if ("OnlineSubscriptionCreationAccount".equalsIgnoreCase(type)) {
                return "TOT";
            }
        }
        if (!"FamilyCreationAccount".equalsIgnoreCase(type) || applicationRights == null) {
            return null;
        }
        return rangeMap.get(applicationRights.getRange());
    }
}
