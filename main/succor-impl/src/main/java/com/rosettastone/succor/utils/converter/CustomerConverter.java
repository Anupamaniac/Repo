package com.rosettastone.succor.utils.converter;

import com.rosettastone.succor.model.bandit.Customer;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Converter Customer object to xml fragment for Parature.
 */
public class CustomerConverter implements Converter, Names {

    public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
                return clazz.equals(Customer.class);
        }

        public void marshal(Object value, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
                Customer customer = (Customer) value;
                writer.startNode(CUSTOMER);
                if (customer.getParatureId() != null) {
                    writer.addAttribute(ID, customer.getParatureId().toString());
                }
                writer.endNode();
        }

        public Object unmarshal(HierarchicalStreamReader reader,
                        UnmarshallingContext context) {
            return null;
        }
}
