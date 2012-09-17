package com.rosettastone.succor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.Test;

import com.rosettastone.succor.backgroundtask.model.Message;

@Test
@ContextConfiguration({"classpath:/backgroundtask-context.xml", "classpath:/test-context.xml" })
@TransactionConfiguration(transactionManager = "taskTransactionManager")
public class DbSizeTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private HibernateTemplate hibernateTemplate;
    
    @Test(enabled = false)
    public void test() {
        Message msg = new Message();
        msg.setMessage("In several recent presentations and the Gilbane Group study on Semantic Software Technologies, I share a simple diagram of the nominal setup for the relationship of content to search and the semantic core, namely a set of terminology rules or terminology with relationships. Semantic search operates best when it focuses on a topical domain of knowledge. The language that defines that domain may range from simple to complex, broad or narrow, deep or shallow. The language may be applied to the task of semantic search from a taxonomy (usually shallow and simple), a set of language rules (numbering thousands to millions) or from an ontology of concepts to a semantic net with millions of terms and relationships among concepts.");
        
        for (int i = 0; i < 5000; i++) {
            hibernateTemplate.save(msg);
            if (i % 100 == 0) {
                hibernateTemplate.bulkUpdate("delete from Message");
                System.out.println(i);
            }
        }
    }
}
