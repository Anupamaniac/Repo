package com.rosettastone.succor.backgroundtask.impl;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.rosettastone.succor.backgroundtask.model.Task;

@Test
public class ArgumentSerializerTest {

    public void test() {
        ArgumentSerializer serializer = new ArgumentSerializer();
        Object[] arguments = new Object[] {
          "String arg",
          1L,
          1,
          Boolean.TRUE,
          new Task()
        };

        String serializedArguments = serializer.serialize(arguments);
        Object[] deserializedArguments = serializer.deserialize(serializedArguments);
        for (int i = 0; i < arguments.length; i++) {
            Object originalArg = arguments[i];
            Object deserializedArg = deserializedArguments[i];
            if (originalArg instanceof Task) {
                Assert.assertEquals(originalArg.getClass(), deserializedArg.getClass());
            } else {
                Assert.assertEquals(originalArg, deserializedArg);
            }
        }
    }

}
