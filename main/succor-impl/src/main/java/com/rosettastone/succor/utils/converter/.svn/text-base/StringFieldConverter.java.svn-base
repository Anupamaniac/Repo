package com.rosettastone.succor.utils.converter;

import com.rosettastone.succor.parature.ParatureProperties;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Converter from String to Parature field
 */
public class StringFieldConverter implements Converter, Names {

    /**
     * Parature field name
     */
    private final String fieldKey;

    private ParatureProperties paratureProperties;

    public StringFieldConverter(String fieldKey, ParatureProperties paratureProperties) {
        this.fieldKey = fieldKey;
        this.paratureProperties = paratureProperties;
    }

    public void marshal(Object value, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
        String fieldId = paratureProperties.get(fieldKey);
        writer.addAttribute(ID, fieldId);
        writer.setValue((String) value);
    }

    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader,
                            UnmarshallingContext unmarshallingContext) {
        return null;
    }

    public boolean canConvert(@SuppressWarnings("rawtypes") Class aClass) {
        return aClass.equals(String.class);
    }
}
