package com.rosettastone.succor;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rabbitmq.client.Channel;
import com.rosettastone.succor.mdp.RabbitMqChannelManager;

public class RabbitMqCleaner {

    public static void main(String[] args) throws IOException {
        new RabbitMqCleaner().clean();
    }

    public void clean() throws IOException {
        System.out.println("Starting app");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/properties-context.xml",
        "/rabbitmq-context.xml");
        RabbitMqChannelManager rabbitMqChannelManager = ctx.getBean(RabbitMqChannelManager.class);
        Channel channel = rabbitMqChannelManager.currentChannel();

        Properties p = new Properties();
        p.load(ctx.getResource("classpath:/application.properties").getInputStream());

        System.out.println("Deleting queue");
        channel.queueDelete(p.getProperty("rabbitmq.queue"));

        System.out.println("Done");
    }

}
