package com.rosettastone.succor.bandit;


import com.rosettastone.succor.model.bandit.License;
import com.rosettastone.succor.model.bandit.ProductRights;
import com.rosettastone.succor.model.bandit.Range;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Test
public class ProductNameGeneratorTest {


    private ProductNameGenerator generator = new ProductNameGenerator();

    public void testTOT() {
        License license = new License();
        license.setCreationAccountType("OnlineSubscriptionCreationAccount");
        List<ProductRights> list = new ArrayList<ProductRights>();
        ProductRights rights = new ProductRights();
        rights.setFamily("premium_community");
        rights.setLanguageCode("ARA");
        rights.setRange(new Range(1, 1));
        list.add(rights);
        license.setProductRights(list);

        Assert.assertEquals(generator.generateProductName(license, "ARA"), "TOT");
    }

    public void testL1() {
        License license = new License();
        license.setCreationAccountType("FamilyCreationAccount");
        List<ProductRights> list = new ArrayList<ProductRights>();
        ProductRights rights = new ProductRights();
        rights.setFamily("application");
        rights.setLanguageCode("ARA");
        rights.setRange(new Range(1, 4));
        list.add(rights);
        license.setProductRights(list);

        Assert.assertEquals(generator.generateProductName(license, "ARA"), "L1");
    }

}
