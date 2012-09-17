package com.rosettastone.succor.mdp;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rabbitmq.client.Channel;

@Test
@ContextConfiguration({"classpath:/rabbitmq-context.xml", "classpath:/properties-context.xml" })
public class RabbitMqChannelManagerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private RabbitMqChannelManager rabbitMqChannelManager;

    public void simpleTest() throws IOException {
        Channel channel = rabbitMqChannelManager.currentChannel();
        Assert.assertTrue(channel.isOpen());
        rabbitMqChannelManager.closeCurrentChannel();
        Assert.assertFalse(channel.isOpen());
    }
}
