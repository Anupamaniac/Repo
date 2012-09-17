package com.rosettastone.succor.rules;

import java.io.Reader;
import java.io.StringReader;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentConfiguration;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

import com.rosettastone.succor.model.bandit.Event;
import com.rosettastone.succor.model.bandit.StudioReminderMessage;
import com.rosettastone.succor.model.bandit.StudioSessionCancellationMessage;
import com.rosettastone.succor.service.EventService;
import com.rosettastone.succor.timertask.dao.ReportDao;
import com.rosettastone.succor.timertask.model.ReportEntityType;
import com.rosettastone.succor.web.service.RuleService;

/**
 * The {@code RulesInvoker} provides the functionality for invoking of rules.
 */

public class RulesInvoker {

    private static final Log LOG = LogFactory.getLog(RulesInvoker.class);

    private EventService eventService;
    private ReportDao reportDao;

    private RuleService ruleService;

    private KnowledgeBase kbase;

    private volatile boolean needUpdate;

    @PostConstruct
    public synchronized void init() {
        needUpdate = true;
    }

    @PreDestroy
    public synchronized void destroy() {
        if (kbase == null) {
            return;
        }
        // ResourceFactory.getResourceChangeNotifierService().stop();
        // ResourceFactory.getResourceChangeScannerService().stop();
    }

    public synchronized void updateRules() {
        needUpdate = true;
    }

    /**
     * Invokes rules for the event.
     * 
     * @param event
     *            event for rules
     */
    public void invokeForEvent(Event event) {
    	/*LOG.debug("11111111111111"+ event.getMessage().getClass());
    	if(event.getMessage()instanceof StudioSessionCancellationMessage){
    		LOG.debug("22222222222222"+ ((StudioSessionCancellationMessage)(event.getMessage())).getReason());
    		LOG.debug("33333333333333"+ ((StudioSessionCancellationMessage)(event.getMessage())).getSolo());
    	}
    	LOG.debug("4444444444444444product"+ event.getCustomer().getProductNames());
    	LOG.debug("5555555555555555Grandfathered"+event.getLicense().getGrandfathered());
    	LOG.debug("6666666666666666solo"+((StudioReminderMessage)event.getMessage()).getSolo());
    	LOG.debug("7777777777777777numberofseats"+((StudioReminderMessage)event.getMessage()).getNumberOfSeats());
        */checkIsInitialized();
        StatelessKnowledgeSession ksession = kbase.newStatelessKnowledgeSession();
        ksession.setGlobal("eventService", eventService);

        RulesInvocationCounter counter = new RulesInvocationCounter();
        ksession.addEventListener(counter);

        ksession.execute(event);

        if (counter.getCounter() == 0) {
            LOG.debug("No rule invoked for event " + event.getMessage().getGuid());
            reportDao.createNewEntity(ReportEntityType.MESSAGE_SKIPPED);
        }
    }

    /**
     * Creates rules or updates rules if needed.
     */
    private synchronized void checkIsInitialized() {
        if (kbase != null && !needUpdate) {
            return;
        }
        LOG.info("Update drools rules.");

        String drl = ruleService.createRulesByTemplate();
        // LOG.debug("DRLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL\n" + drl);
        Reader reader = new StringReader(drl);

        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newReaderResource(reader), ResourceType.DRL);
        Assert.isTrue(!kbuilder.hasErrors(), kbuilder.getErrors().toString());

        KnowledgeAgentConfiguration aconf = KnowledgeAgentFactory.newKnowledgeAgentConfiguration();
        aconf.setProperty("drools.agent.newInstance", "false");
        aconf.setProperty("drools.agent.monitorChangeSetEvents", "false");
        aconf.setProperty("drools.agent.scanResources", "false");

        KnowledgeAgent kagent = KnowledgeAgentFactory.newKnowledgeAgent("Success Correspondence Agent", aconf);
        kbase = kagent.getKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

        needUpdate = false;
    }

    @Required
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Required
    public void setReportDao(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    public void setRuleService(RuleService ruleService) {
        this.ruleService = ruleService;
    }
}
