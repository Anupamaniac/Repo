package com.rosettastone.succor.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Test
public class FreemarkerConfigTest {

    private Configuration freemarkerConfiguration;

    @BeforeClass
    public void setup() throws IOException, TemplateException {
        FreeMarkerConfigurationFactoryBean configurationFactory = new FreeMarkerConfigurationFactoryBean();
        configurationFactory.setTemplateLoaderPath("classpath:/templates/");
        configurationFactory.setDefaultEncoding("utf-8");
        freemarkerConfiguration = configurationFactory.createConfiguration();
    }

    public void test() throws IOException, TemplateException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringWriter str = new StringWriter();
        Template template = freemarkerConfiguration.getTemplate("mail_template.ftl");
        template.process(map, str);
    }
}
