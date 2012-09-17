package com.rosettastone.succor.web.dao;

import com.rosettastone.succor.web.model.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@Test
@ContextConfiguration({"classpath:/hibernate-context.xml",
        "classpath:/properties-context.xml"})
public class TemplateDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("templateDao")
    private TemplateDao templateDAO;


//    @Test
//    public void loadTemplate() {
//        templateDAO.load(3, Template.Type.SMS);
//    }

}


