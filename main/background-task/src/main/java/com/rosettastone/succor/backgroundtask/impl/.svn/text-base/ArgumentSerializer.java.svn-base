package com.rosettastone.succor.backgroundtask.impl;

import com.thoughtworks.xstream.XStream;

/**
 * Objects serializer.
 * It can serialize array of objects to xml using xstream library and deserialize.
 * It is using in TaskExecutor for saving task arguments to DB.
 *
 * @see TaskExecutor
 */
public class ArgumentSerializer {

    private XStream xstream = new XStream();

    /**
     * Serialize array of objects to xml.
     * @param arguments - array of objects
     * @return xml string
     */
    public String serialize(Object[] arguments) {
        return xstream.toXML(arguments);
    }

    /**
     * Deserialize array of objects from string.
     * @param arguments - xml document
     * @return array of objects
     */
    public Object[] deserialize(String arguments) {
        return (Object[]) xstream.fromXML(arguments);
    }
}
