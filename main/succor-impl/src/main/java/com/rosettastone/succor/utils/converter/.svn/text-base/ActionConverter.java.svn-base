package com.rosettastone.succor.utils.converter;

import com.rosettastone.succor.model.parature.Action;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Converter Action object to Parature Action field
 * It converts action object to xml fragment.
 */
public class ActionConverter implements Converter, Names {


    public void marshal(Object value, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
        Action action = (Action) value;

        writer.startNode(ACTION);
        writer.startNode(ACTION);
        writer.addAttribute(ID, action.getType());

        writer.startNode(COMMENT);
        writer.setValue(action.getComment());
        writer.endNode();

        writer.startNode(SHOW_TO_CUST);
        writer.setValue(Boolean.toString(action.isShowToCust()).toLowerCase());
        writer.endNode();

        writer.startNode(SHOW_TO_ADDITIONAL_CONTACT);
        writer.setValue(Boolean.toString(action.isShowToAdditionalContact()).toLowerCase());
        writer.endNode();

        writer.endNode();
        writer.endNode();
    }

    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader,
                            UnmarshallingContext unmarshallingContext) {
        return null;
    }

    public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
        return clazz.equals(Action.class);
    }

}
