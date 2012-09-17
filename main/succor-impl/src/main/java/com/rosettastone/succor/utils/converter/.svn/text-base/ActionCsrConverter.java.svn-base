package com.rosettastone.succor.utils.converter;

import com.rosettastone.succor.model.parature.ActionCsr;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * User: mixim
 * Date: 25.08.11
 */
public class ActionCsrConverter implements Converter, Names {
    public void marshal(Object value, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
        ActionCsr action = (ActionCsr) value;

        writer.startNode(ACTION);
        writer.startNode(ACTION);
        writer.addAttribute(ID, action.getId());

        writer.startNode(ASSIGN_TO_CSR);
        writer.setValue(Long.toString(action.getAssignToCsr()));
        writer.endNode();

        writer.startNode(COMMENT);
        writer.setValue(action.getComment());
        writer.endNode();

        writer.startNode(SHOW_TO_CUST);
        writer.setValue(Boolean.toString(action.isShowToCust()).toLowerCase());
        writer.endNode();

        writer.startNode(TIME_SPENT_MINUTES);
        writer.setValue(action.getTimeSpentMinutes()+"");
        writer.endNode();

        writer.endNode();
        writer.endNode();
    }

    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader,
                            UnmarshallingContext unmarshallingContext) {
        return null;
    }

    public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
        return clazz.equals(ActionCsr.class);
    }
}
