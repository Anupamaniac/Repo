package com.rosettastone.succor.web.dto;

/**
 * The {@code EventDTO} represents Event object for flex.
 */
public class EventDTO {

    private long id;

    private String name;

    private String className;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
