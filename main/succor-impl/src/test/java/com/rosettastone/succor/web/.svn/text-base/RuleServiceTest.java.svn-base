package com.rosettastone.succor.web;

import com.rosettastone.succor.web.model.*;
import com.rosettastone.succor.web.service.RuleServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.testng.annotations.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.*;

@Test
public class RuleServiceTest {

    private static final Log LOG = LogFactory.getLog(RuleServiceTest.class);


    @Test
    public void testDroolsGeneration() throws Exception {
        RuleServiceImpl service = new RuleServiceImpl();
        Rule rule = new Rule();
        rule.setId(5L);
        rule.setName("Test rule name");
        rule.setActions(EnumSet.of(Action.EMAIL, Action.PHONE, Action.POSTAL, Action.INSTANT_ACTION_TICKET));
        rule.setSupportLang(Language.EN);

        Product product  = new Product();
        product.setName("L1");
        product.setLongName("Level 1");

        Event event  = new Event();
        Set<Value> values = new HashSet<Value>();
        event.setValues(values);
        Value value = new Value();
        value.setVariable(new Variable("levels"));
        value.setValue("1-3");
        values.add(value);
        event.getMessageType().setClassName("LevelCompletionMessage");
        rule.setEvent(event);
        rule.setProduct(product);
        List<Rule> rules = new ArrayList<Rule>();
        rules.add(rule);
        String drl = service.createRulesByTemplate(rules);
        LOG.debug(drl);
        compileRules(drl);
    }

    private KnowledgeBuilder compileRules(String drl) {
        KnowledgeBuilder kBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        Reader rdr = new StringReader(drl);
        kBuilder.add(ResourceFactory.newReaderResource(rdr), ResourceType.DRL);

        if (kBuilder.hasErrors()) {
            for (KnowledgeBuilderError err : kBuilder.getErrors()) {
                System.err.println(err.toString());
            }
            throw new IllegalStateException("DRL errors");
        }
        return kBuilder;
    }

}
