package com.rosettastone.succor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.service.CustomerSynchronizationService;

/**
 * 
 * The address of the customer need to take from DB only if the address is empty in the message from Product Trigger
 * Queue.
 * 
 * DB has a different languages codes than Product
 */
@Test
@ContextConfiguration(locations = { "classpath:/properties-context.xml", "classpath:/services-test-context.xml" })
public class CustomerSynchronizationServiceTest extends AbstractTestNGSpringContextTests {

    private static final String ID = "18c45920-19eb-102c-9376-0015c5afe2a9";
    private static final String STREET = "Some street";
    private static final String LANG = "ITA";

    @Autowired
    private CustomerSynchronizationService customerSynchronizationService;

    @Test(enabled = false)
    public void testWithoutAddress() {
        Customer customer = new Customer();
        /*
        Product product = new Product();
        product.setRsLangCode(LANG);
        customer.setProduct(product);
        customer.setLicenseId(ID);
        */
        //FIXME
        //customerSynchronizationService.synchronize(customer);

        checkGeneralFields(customer);

        /*
        Address address = customer.getAddress();
        Assert.assertNotNull(address);
        Assert.assertEquals(address.getTitle(), CustomerDaoStub.TITLE);
        Assert.assertEquals(address.getCity(), CustomerDaoStub.CITY);
        Assert.assertEquals(address.getCountry(), CustomerDaoStub.COUNTRY);
        Assert.assertEquals(address.getPostalCode(), CustomerDaoStub.POSTAL_CODE);
        Assert.assertEquals(address.getStreetAddress1(), CustomerDaoStub.STREET_ADDRESS1);
        Assert.assertEquals(address.getStreetAddress2(), CustomerDaoStub.STREET_ADDRESS2);
        Assert.assertEquals(address.getStateCode(), CustomerDaoStub.STATE);
        Assert.assertEquals(address.getCountry(), CustomerDaoStub.COUNTRY);
        */
    }

    @Test(enabled = false)
    public void testWithAddress() {
        /*
        Customer customer = new Customer();
        Product product = new Product();
        product.setRsLangCode(LANG);
        customer.setProduct(product);
        customer.setLicenseId(ID);
        Address address = new Address();
        address.setStreetAddress1(STREET);
        customer.setAddress(address);
        //FIXME
        //customerSynchronizationService.synchronize(customer);

        checkGeneralFields(customer);

        address = customer.getAddress();
        Assert.assertNotNull(address);
        Assert.assertEquals(address.getTitle(), null);
        Assert.assertEquals(address.getCity(), null);
        Assert.assertEquals(address.getCountry(), null);
        Assert.assertEquals(address.getPostalCode(), null);
        Assert.assertEquals(address.getStreetAddress1(), STREET);
        Assert.assertEquals(address.getStreetAddress2(), null);
        Assert.assertEquals(address.getStateCode(), null);
        Assert.assertEquals(address.getCountry(), null);
        */
    }

    private void checkGeneralFields(Customer customer) {
        /*
        Assert.assertEquals(customer.getLicenseId(), ID);
        Assert.assertEquals(customer.getEmail(), null);
       // Assert.assertEquals(customer.getProduct().getProductNames().get(0), CustomerDaoStub.LANG_LEVEL);
        Assert.assertEquals(customer.getPhone(), null);
        Assert.assertEquals(customer.getName(), null);
        // service should not set language
        Assert.assertEquals(customer.getSupportLang(), null);
        Assert.assertEquals(customer.getProduct().getLanguage(), null);
        */
    }

}
