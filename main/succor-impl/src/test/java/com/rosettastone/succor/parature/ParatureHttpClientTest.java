package com.rosettastone.succor.parature;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.rosettastone.succor.exception.ParatureErrorCode;
import com.rosettastone.succor.exception.ParatureException;
import com.rosettastone.succor.model.bandit.Customer;
import com.rosettastone.succor.model.parature.Action;
import com.rosettastone.succor.model.parature.SuperTicket;
import com.rosettastone.succor.model.parature.Ticket;
import com.rosettastone.succor.model.parature.TicketLanguageType;
import com.rosettastone.succor.model.parature.TicketType;
import com.rosettastone.succor.parature.ParatureHttpClient;
import com.rosettastone.succor.parature.ParatureProperties;
import com.rosettastone.succor.parature.TicketParatureApi;
import com.rosettastone.succor.utils.ActionSerializer;
import com.rosettastone.succor.utils.TicketSerializer;
import com.rosettastone.succor.utils.TicketXMLParser;

@Test
@ContextConfiguration(locations = { "/parature-context.xml", "/properties-context.xml", "/hibernate-context.xml" })
public class ParatureHttpClientTest extends AbstractTestNGSpringContextTests {

    private static final int DAYS = 5;

    @Autowired
    private ParatureHttpClient paratureHttpClient;

    @Autowired
    private TicketSerializer ticketSerializer;

    @Autowired
    private ActionSerializer actionSerializer;

    @Autowired
    private TicketParatureApi ticketParatureApi;

    @Autowired
    private ParatureProperties paratureProperties;
    @Autowired
    private ParatureProperties productProperties;

    @Test(enabled = false)
    public void testParatureApi() {
        Customer customer = new Customer();
        // product.action.level_completion_message
        // product.action.not_logged_in_for_days
        // product.action.completion_of_level1_unit1_corelesson1
        // product.action.studio_readiness_message
        // product.action.completed_first_studio_session
        /*
        customer.setAction(productProperties.get("product.action.not_logged_in_for_days"));
        customer.setActionCreatedAt(new Date());
        customer.setAddress(new Address());
        customer.setDays(DAYS);
        customer.setEmail("kzhirov@rosettastone");
        customer.setFirstSession(true);
        customer.setLicenseId("222db1be-19eb-102c-9376-0015c5afe2a9");
        customer.setName("Konstantin Zhirov");
        customer.setPhone("");
        customer.setSupportLang("en-US");
        customer.setInstitutional(false);

        Product product = new Product();
        product.setLevel("1");
        product.setUnit("1");
        // product.setProductName("L1");
        product.getProductNames().add("L1");

        customer.setProduct(product);
*/

        //FIXME
        //ticketParatureApi.createTicket(customer, TicketType.SUPER);
    }

    @Test(enabled = false)
    public void testClient() throws JSONException, SAXException, IOException, ParserConfigurationException {
        // Get Customer
        String response = paratureHttpClient.sendGetRequest("/Customer?Last_Name_like_=Zhirov&_output_=json");

        JSONObject json = new JSONObject(response);
        JSONObject customerJSON = (JSONObject) json.getJSONObject("Entities").getJSONArray("Customer").get(0);
        Customer customer = new Customer();
        customer.setParatureId(customerJSON.getLong("@id"));

        SuperTicket superTicket = new SuperTicket();
        //superTicket.setCustomer(customer);
        superTicket.setLanguage(TicketLanguageType.AMERICAN_ENGLISH);

        // Create ticket
        response = paratureHttpClient.sendPostRequest("/Ticket/?_enforceRequiredFields_=false",
                ticketSerializer.serialize(superTicket));

        String ticketId = TicketXMLParser.getTicketIdFromXML(response);

        Action action = new Action();
        // action.setType(TicketActionType.LEVEL_COMPLETION_MESSAGE1);
        action.setComment("Test comment");
        action.setShowToCust(false);
        action.setShowToAdditionalContact(true);

        paratureHttpClient.sendPutRequest("/Ticket/" + ticketId, actionSerializer.serialize(action));

        // Resolve
        paratureHttpClient.sendPutRequest("/Ticket/" + ticketId, actionSerializer.serialize(createSolveAction()));

        // Delete
        paratureHttpClient.sendDeleteRequest("/Ticket/" + ticketId);
    }

    // falls on timeout
    @Test(enabled = false)
    public void testLoadCustomer() {
        paratureHttpClient.sendGetRequest("/Customer/488423?_output_=json");
    }

    @Test(enabled = false)
    public void testCreatePhoneTicket() throws SAXException, IOException, ParserConfigurationException, JSONException {
        testTicket(TicketType.PHONE);
    }

    @Test(enabled = false)
    public void testCreatePostalTicket() throws SAXException, IOException, ParserConfigurationException, JSONException {
        testTicket(TicketType.POSTAL);
    }

    @Test(enabled = false)
    public void testCreateSuperTicket() throws SAXException, IOException, ParserConfigurationException, JSONException {
        testTicket(TicketType.SUPER);
    }

    // FIXME Implement testing of TicketParatureApi methods in separated class.
    @Test(enabled = false)
    private void testTicket(TicketType type) throws JSONException, SAXException, IOException,
            ParserConfigurationException {
        // Get Customer
        String response = paratureHttpClient.sendGetRequest("/Customer?Last_Name_like_=Zhirov&_output_=json");

        JSONObject json = new JSONObject(response);
        JSONObject customerJSON = (JSONObject) json.getJSONObject("Entities").getJSONArray("Customer").get(0);
        Customer customer = new Customer();
        customer.setParatureId(customerJSON.getLong("@id"));

        Ticket ticket = TestTicketFactory.createTestTicket(customer, type);
        InputStream stream = ticketSerializer.serialize(ticket);

        // Create ticket
        response = paratureHttpClient.sendPostRequest("/Ticket/?_enforceRequiredFields_=false", stream);

        String ticketId = TicketXMLParser.getTicketIdFromXML(response);

        // create solve action
        Action action = createSolveAction();

        // Resolve
        paratureHttpClient.sendPutRequest("/Ticket/" + ticketId, actionSerializer.serialize(action));

        // Delete
        paratureHttpClient.sendDeleteRequest("/Ticket/" + ticketId);
    }

    private Action createSolveAction() {
        Action action = new Action();
        action.setComment("Test comment");
        action.setShowToCust(false);
        action.setShowToAdditionalContact(true);
        action.setType(paratureProperties.get("parature.action.type.solve"));
        return action;
    }

    @Test(enabled = false)
    public void testException400() {
        sendBadRequest("/invalid_request?abc=bcd", ParatureErrorCode.BAD_REQUEST_400);
    }

    @Test(enabled = false)
    public void testException401() throws IOException {
        /*
        ParatureHttpClient invalidClient = new ParatureHttpClient(paratureHttpClient.getBaseUrl(),
                paratureHttpClient.getAccountId(), paratureHttpClient.getDepartmentId(), paratureHttpClient
                        .getAuthToken().toLowerCase());
        invalidClient.init();
        try {
            invalidClient.sendGetRequest("/Customer/1");
            Assert.fail("Exception expected");
        } catch (ParatureException e) {
            Assert.assertEquals(e.getErrorCode(), ParatureErrorCode.UNAUTORIZED_401);
        }
        */
    }

    @Test(enabled = false)
    public void testException403() {
        sendBadRequest("/Article/1", ParatureErrorCode.FORBIDDEN_403);
    }

    @Test(enabled = false)
    public void testException404() {
        sendBadRequest("/Customer/5006045", ParatureErrorCode.NOT_FOUND_404);
    }

    @Test(enabled = false)
    private void sendBadRequest(String url, ParatureErrorCode expectedError) {
        try {
            paratureHttpClient.sendGetRequest(url);
            Assert.fail("Exception expected");
        } catch (ParatureException e) {
            Assert.assertEquals(e.getErrorCode(), expectedError);
        }
    }
}
