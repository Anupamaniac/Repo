package com.rosettastone.succor.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Test
public class EmailBodyGeneratorTest {

    @Test
    public void testBodyGenerator() throws IOException, TemplateException {
        Configuration cfg = new Configuration();
        String body = "<html>${body}</html>";
        StringWriter writer = new StringWriter();
        Map<Object, Object> params = new HashMap<Object, Object>();
        params.put("body", "aaa");
        Template t = new Template("email", new StringReader(body), cfg);
        t.process(params, writer);
        System.out.println(writer.toString());
    }
}
