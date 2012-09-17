package com.rosettastone.succor.web;

import com.rosettastone.succor.drools.Item;
import com.rosettastone.succor.drools.ItemService;
import com.rosettastone.succor.rules.RulesInvocationCounter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.KnowledgeBase;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentConfiguration;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.rule.Rule;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;
import org.easymock.EasyMock;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStreamReader;
import java.io.Reader;

@Test
public class DroolsKnowledgeBaseTest {

    private static final Log LOG = LogFactory.getLog(DroolsKnowledgeBaseTest.class);

    private ItemService service = EasyMock.createMock(ItemService.class);


    public void testDroolsGeneration(int i) throws Exception {
        String filename = (i % 2 == 0) ? "/drools/stress1.drl" : "/drools/stress2.drl";
        InputStreamReader reader = new InputStreamReader(DroolsKnowledgeBaseTest.class.getResourceAsStream(filename));

        KnowledgeAgentConfiguration aconf = KnowledgeAgentFactory.newKnowledgeAgentConfiguration();
        aconf.setProperty("drools.agent.newInstance", "false");
        aconf.setProperty("drools.agent.monitorChangeSetEvents", "false");
        aconf.setProperty("drools.agent.scanResources", "false");

        KnowledgeBuilder builder = compileRules(reader);

        KnowledgeAgent kagent = KnowledgeAgentFactory.newKnowledgeAgent("Success Correspondence Agent", aconf);
        KnowledgeBase kbase = kagent.getKnowledgeBase();
        kbase.addKnowledgePackages(builder.getKnowledgePackages());
//        kagent.monitorResourceChangeEvents(false);
        Rule rule1 = kbase.getRule("com.rosettastone.succor", "Equal rule 1");
        Rule rule5 = kbase.getRule("com.rosettastone.succor", "Equal rule 5");
        Rule rule6 = kbase.getRule("com.rosettastone.succor", "Equal rule 6");

        StatelessKnowledgeSession session = kbase.newStatelessKnowledgeSession();
        if (i % 2 == 0) {
            Assert.assertNotNull(rule1);
            Assert.assertNotNull(rule5);
            Assert.assertNull(rule6);
            testRulesFirst(session);
        } else {
            Assert.assertNull(rule1);
            Assert.assertNotNull(rule5);
            Assert.assertNotNull(rule6);
            testRulesSecond(session);
        }
    }

    private void testRulesFirst(StatelessKnowledgeSession session) {
        RulesInvocationCounter counter = new RulesInvocationCounter();
        session.addEventListener(counter);

        EasyMock.reset(service);

        session.setGlobal("itemService", service);

        Item item1 = new Item();
        item1.setName("My test item1 name");
        item1.setPrice(1);
        service.equal(item1, 1);

        Item item5 = new Item();
        item5.setName("My test item5 name");
        item5.setPrice(5);
        service.equal(item5, 5);

        Item item6 = new Item();
        item6.setName("My test item6 name");
        item6.setPrice(12);

        EasyMock.replay(service);

        session.execute(item1);
        Assert.assertEquals(counter.getCounter(), 1);
        session.execute(item5);
        Assert.assertEquals(counter.getCounter(), 2);
        session.execute(item6);
        Assert.assertEquals(counter.getCounter(), 2);

        EasyMock.verify(service);
    }

    private void testRulesSecond(StatelessKnowledgeSession session) {
        RulesInvocationCounter counter = new RulesInvocationCounter();
        session.addEventListener(counter);

        EasyMock.reset(service);

        session.setGlobal("itemService", service);

        Item item1 = new Item();
        item1.setName("My test item1 name");
        item1.setPrice(1);

        Item item5 = new Item();
        item5.setName("My test item5 name");
        item5.setPrice(10);
        service.equal(item5, 5);

        Item item6 = new Item();
        item6.setName("My test item6 name");
        item6.setPrice(12);
        service.equal(item6, 6);

        EasyMock.replay(service);

        session.execute(item1);
        Assert.assertEquals(counter.getCounter(), 0);
        session.execute(item5);
        Assert.assertEquals(counter.getCounter(), 1);
        session.execute(item6);
        Assert.assertEquals(counter.getCounter(), 2);

        EasyMock.verify(service);
    }

    private KnowledgeBuilder compileRules(Reader reader) {
        KnowledgeBuilder kBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kBuilder.add(ResourceFactory.newReaderResource(reader), ResourceType.DRL);

        if (kBuilder.hasErrors()) {
            for (KnowledgeBuilderError err : kBuilder.getErrors()) {
                System.err.println(err.toString());
            }
            throw new IllegalStateException("DRL errors");
        }
        return kBuilder;
    }

    @Test(enabled = false)
    public void testMemoryLeak() throws Exception {
        for (int i = 0; i < 1000; i++) {
            testDroolsGeneration(i);
        }
    }


}
