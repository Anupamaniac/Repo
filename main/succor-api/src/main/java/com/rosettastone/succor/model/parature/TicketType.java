package com.rosettastone.succor.model.parature;

/**
 * The {@code TicketType} represents the types of tickets.
 */

public enum TicketType implements Identifiable {
    SUPER("parature.field.type.super"),
    POSTAL("parature.field.type.outreach"),
    PHONE("parature.field.type.outreach"),
    INST("parature.field.type.institutional"),
    EMAIL(""),
    SMS("");

    private final String key;

    private TicketType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
