package com.rosettastone.succor.bandit;

import com.rosettastone.succor.model.bandit.License;
import com.rosettastone.succor.model.bandit.ProductRights;
import com.rosettastone.succor.model.bandit.Range;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Test
public class LicenseConverterTest {

    public void testLicense() throws IOException, JSONException {
        JSONObject obj = new JSONObject(getString("/eventparser/license_1.json"));
        LicenseConverter licenseConverter = new LicenseConverter();
        License license = licenseConverter.convert(obj);

        Assert.assertEquals(license.getCreationAccountGuid(), "735cea86-53c9-4446-a87d-b28ee5329475");
        Assert.assertEquals(license.getUpgradedAt(), new Date(1231231312123l * 1000));
        Assert.assertEquals(license.getCreationAccountType(), "FamilyCreationAccount");
        Assert.assertNotNull(license.getProductRights());

        Assert.assertEquals(license.getProductRights().size(), 4);
    }

    public void testLicenseEmptyRights() throws IOException, JSONException {
        JSONObject obj = new JSONObject(getString("/eventparser/license_empty_rights.json"));
        LicenseConverter licenseConverter = new LicenseConverter();

        License license = licenseConverter.convert(obj);

        Assert.assertEquals(license.getCreationAccountGuid(), "735cea86-53c9-4446-a87d-b28ee5329475");
        Assert.assertEquals(license.getUpgradedAt(), new Date(1231231312123l * 1000));
        Assert.assertEquals(license.getCreationAccountType(), "FamilyCreationAccount");
        Assert.assertNotNull(license.getProductRights());
        Assert.assertEquals(license.getProductRights().size(), 0);
    }

    public void testProductRights() throws IOException, JSONException {
        JSONObject obj = new JSONObject(getString("/eventparser/product_rights_1.json"));
        ProductRightsConverter converter = new ProductRightsConverter();

        ProductRights rights = converter.convert(obj);
        Assert.assertEquals(rights.getFamily(), "application");
        Assert.assertEquals(rights.getLanguageCode(), "ENG");
        Assert.assertEquals(rights.getRange(), new Range(1, 8));
    }

    public void testProductRightsEmptyRanges() throws IOException, JSONException {
        JSONObject obj = new JSONObject(getString("/eventparser/product_rights_empty_range.json"));
        ProductRightsConverter converter = new ProductRightsConverter();

        ProductRights rights = converter.convert(obj);
        Assert.assertEquals(rights.getFamily(), "application");
        Assert.assertEquals(rights.getLanguageCode(), "ENG");
        Assert.assertEquals(rights.getRange(), new Range(0, 0));
    }



    private String getString(String filename) throws IOException {
        InputStream inputStream = LicenseConverterTest.class.getResourceAsStream(filename);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int b;
        while ((b = inputStream.read()) != -1) {
            output.write(b);
        }
        return new String(output.toByteArray());
    }

}
