package com.rosettastone.succor.utils.converter;

import com.rosettastone.succor.model.parature.Identifiable;
import com.rosettastone.succor.parature.ParatureProperties;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Converter from some object to Parature custom field
 */
public class CustomFieldConverter implements Converter, Names {
    /**
     * Source object class
     */
    private final Class<?> clazz;

    /**
     * Parature field name
     */
    private final String fieldKey;

    private ParatureProperties paratureProperties;

    public CustomFieldConverter(Class<?> clazz, String fieldKey, ParatureProperties paratureProperties) {
        this.clazz = clazz;
        this.fieldKey = fieldKey;
        this.paratureProperties = paratureProperties;
        checkClass();
    }

    public void marshal(Object value, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
        String fieldId = paratureProperties.get(fieldKey);
        writer.addAttribute(ID, fieldId);
        writer.startNode(OPTION);
        String key = ((Identifiable) value).getKey();
        String valueId = paratureProperties.get(key);
        writer.addAttribute(ID, valueId);
        writer.addAttribute(SELECTED, TRUE);
        writer.endNode();
    }

    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader,
                            UnmarshallingContext unmarshallingContext) {
        return null;
    }

    public boolean canConvert(@SuppressWarnings("rawtypes") Class aClass) {
        return aClass.equals(clazz);
    }

    private void checkClass() {
        //TODO may be there more simple method.
        Class<?>[] interfaces = clazz.getInterfaces();
        boolean found = false;
        for (Class<?> c : interfaces) {
            found = found || c.equals(Identifiable.class);
        }
        if (!found) {
            throw new IllegalArgumentException("Class should implement Identifiable interface");
        }
    }
}
