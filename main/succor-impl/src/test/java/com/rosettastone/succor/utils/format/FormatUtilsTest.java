package com.rosettastone.succor.utils.format;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.sling.commons.json.JSONException;
import org.testng.annotations.Test;

import com.rosettastone.succor.model.bandit.Customer;

@Test
public class FormatUtilsTest {

    @Test
    public void csvFormatTest() throws IOException, JSONException {
        Customer customer = new Customer();
        Assert.assertEquals(null, FormatUtils.csvformatCustomerAddress(customer));

        customer.setCity("city");
        Assert.assertEquals(null, FormatUtils.csvformatCustomerAddress(customer));

        customer.setAddressLine1("streetAddress1");
        Assert.assertEquals(null, FormatUtils.csvformatCustomerAddress(customer));

        customer.setCountryIso("country");
        Assert.assertEquals("streetAddress1, city, country", FormatUtils.csvformatCustomerAddress(customer));

        customer.setPostalCode("postal");
        Assert.assertEquals("streetAddress1, city, postal, country", FormatUtils.csvformatCustomerAddress(customer));

        customer.setStateProvince("state");
        Assert.assertEquals("streetAddress1, city, state postal, country",
                FormatUtils.csvformatCustomerAddress(customer));

        customer.setAddressLine2("streetAddress2");
        Assert.assertEquals("streetAddress1, streetAddress2, city, state postal, country",
                FormatUtils.csvformatCustomerAddress(customer));
    }

    @Test
    public void detailsFormatTest() throws IOException, JSONException {
        Customer customer = new Customer();
        Assert.assertEquals(null, FormatUtils.formatCustomerAddress(customer));

        customer.setCity("city");
        Assert.assertEquals(null, FormatUtils.formatCustomerAddress(customer));

        customer.setAddressLine1("streetAddress1");
        Assert.assertEquals(null, FormatUtils.formatCustomerAddress(customer));

        customer.setCountryIso("country");
        Assert.assertEquals("streetAddress1\ncity, country", FormatUtils.formatCustomerAddress(customer));

        customer.setPostalCode("postal");
        Assert.assertEquals("streetAddress1\ncity, postal\ncountry", FormatUtils.formatCustomerAddress(customer));

        customer.setStateProvince("state");
        Assert.assertEquals("streetAddress1\ncity, state postal\ncountry", FormatUtils.formatCustomerAddress(customer));

        customer.setAddressLine2("streetAddress2");
        Assert.assertEquals("streetAddress1\nstreetAddress2\ncity, state postal\ncountry",
                FormatUtils.formatCustomerAddress(customer));

    }

    @Test
    public void phoneNumberTest() {
        Assert.assertTrue(FormatUtils.isPhoneValid("  1"));
        
        Assert.assertTrue(FormatUtils.isPhoneValid("phone number is 1234"));
        
        Assert.assertFalse(FormatUtils.isPhoneValid("Not available"));
    }

}
