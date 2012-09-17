package com.rosettastone.succor.drools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;
import org.drools.template.ObjectDataCompiler;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Test
public class DroolsTest {

    private static final Log LOG = LogFactory.getLog(DroolsTest.class);

    @Test
    public void testDroolsTemplates() throws Exception {

        String drl = createRulesByTemplate();

        KnowledgeBuilder kBuilder = compileRules(drl);

        KnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
        kBase.addKnowledgePackages(kBuilder.getKnowledgePackages());
        StatelessKnowledgeSession session = kBase.newStatelessKnowledgeSession();

        testRules(session);

    }

    private void testRules(StatelessKnowledgeSession session) {
        ItemService service = EasyMock.createMock(ItemService.class);

        Item lessItem = new Item();
        lessItem.setName("My test lessItem name");
        lessItem.setPrice(5);
        service.less(lessItem);

        Item greaterItem = new Item();
        lessItem.setName("My test greaterItem name");
        greaterItem.setPrice(20);
        service.greater(greaterItem);

        EasyMock.replay(service);

        session.setGlobal("itemService", service);
        session.execute(lessItem);
        session.execute(greaterItem);
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


    private String createRulesByTemplate() {
        List<RuleParams> templateParams = new ArrayList<RuleParams>();
        templateParams.add(new RuleParams("Less rule", "price <= 10", "itemService.less($item);"));
        templateParams.add(new RuleParams("Greater rule", "price > 10", "itemService.greater($item);"));

        InputStream templateInput = DroolsTest.class.getResourceAsStream("/drools/rule-template-test.drl");

        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drlRules = converter.compile(templateParams, templateInput);
        System.out.println("DRL=" + drlRules);
        return drlRules;
    }

}