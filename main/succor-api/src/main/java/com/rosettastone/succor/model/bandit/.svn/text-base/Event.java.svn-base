package com.rosettastone.succor.model.bandit;

/**
 * The {@code Event} represents full info for message processing.
 */

public class Event {

    private Header header;
    private License license;
    private Customer customer;
    private Message message;
    private InternalSuccorData internalSuccorData = new InternalSuccorData();

    public Event(String json) {
        internalSuccorData.setJson(json);
    }

    public Event() {
        new Event("");
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public InternalSuccorData getInternalSuccorData() {
        return internalSuccorData;
    }
}
