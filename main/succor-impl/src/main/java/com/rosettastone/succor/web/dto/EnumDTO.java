package com.rosettastone.succor.web.dto;

/**
 * The {@code EnumDTO} represents Enum for flex.
 */
public class EnumDTO {

    private String name;
    private String label;

    public EnumDTO(String name, String label) {
        this.name = name;
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
