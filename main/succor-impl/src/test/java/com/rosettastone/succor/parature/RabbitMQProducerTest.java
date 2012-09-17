package com.rosettastone.succor.parature;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.testng.annotations.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConnectionParameters;
import com.rabbitmq.client.MessageProperties;


public class RabbitMQProducerTest {

    private static final int MESSAGE_AMOUNT = 1000;
    private static final int RABBIT_MQ_PORT = 5672;

    public static void main(String[] args) throws IOException {
       // new RabbitMQProducerTest().sendJSON("/bug.json");
       // new RabbitMQProducerTest().sendJSON("/studi_reminder_message.json");
//    	new RabbitMQProducerTest().sendJSON("/studi_reminder_message_non_GF_solo.json");
//    	new RabbitMQProducerTest().sendJSON("/studi_reminder_message_non_GF_group.json");
//    	new RabbitMQProducerTest().sendJSON("/studi_reminder_message_GF_solo.json");
//    	new RabbitMQProducerTest().sendJSON("/studi_reminder_message_GF.json");
//    	new RabbitMQProducerTest().sendJSON("/studi_reminder_message_GF_1.json");
//    	new RabbitMQProducerTest().sendJSON("/studi_reminder_message_GF_4.json");
    	new RabbitMQProducerTest().sendJSON("/studi_reminder_message_GF_no_num_of_seats.json");
       // new RabbitMQProducerTest().sendJSON("/studio_reminder_message_japanese.json");
       // new RabbitMQProducerTest().sendJSON("/expiring_subscription.json");
       // new RabbitMQProducerTest().sendJSON("/initial_profile_setup.json");
//new RabbitMQProducerTest().sendJSON("/studio_satisfaction.json");
        System.out.println("Done");
    }

    @Test(enabled = false)
    public void sendStudioReadinessTest() throws IOException {
        sendJSON("/rosetta_world_encouragement_message.json");
        sendJSON("/claimed_extensions_message.json");
        sendJSON("/studio_session_cancellation_message_no_coach_yes_learner.json");
        sendJSON("/levels_complete_1-3_S5_english_postal.json");
        sendJSON("/levels_complete_1-5_S5_japanese_postal.json");
        sendJSON("/messages/jp/test.json");
        sendJSON("/first_completed_studio_session.json");
        sendJSON("/first_core_lesson_completion.json");
        sendJSON("first_studio_readiness.json");
        sendJSON("/invalid_guid.json");
        sendJSON("/level_completion_1.json");
        sendJSON("/level_completion_2.json");
        sendJSON("/level_completion_3.json");
        sendJSON("/level_completion_4.json");
        sendJSON("/level_completion_5.json");
        sendJSON("/levels_complete_1-3.json");
        sendJSON("/no_customer_data.json");
        sendJSON("/not_logged_in_for_days30.json");
        sendJSON("/not_logged_institutional.json");
        sendJSON("/null_values_test.json");
        sendJSON("/studio_readiness.json");
        sendJSON("/studio_reminder_message.json");
        sendJSON("/not_logged_in_for_days_30_first_L1_english_phone_email.json");
        sendJSON("/not_logged_in_for_days_30_first_L1_japanese_phone_email.json");
    }

    @Test(enabled = false)
    public void massiveSendTest() throws IOException {
        String[] messages = new String[] {"/first_completed_studio_session.json",
                "/first_core_lesson_completion.json", "/level_completion.json", "/not_logged_in_for_days15.json",
                "/not_logged_in_for_days5.json", "/null_values_test.json", "/studio_readiness.json" };
        for (int i = 0; i < MESSAGE_AMOUNT; i++) {
            for (String message : messages) {
                sendJSON(message);
            }
        }
    }

    @Test(enabled = false)
    public void sendBanditMessagesTest() throws Exception {
        File directory = new File("target/test-classes/bandit");

        if (!directory.exists() || !directory.isDirectory()) {
            throw new Exception("directory not found or not a directory");
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            String fileName = file.getName();
            sendJSON("/bandit/" + fileName);
        }
    }

    private void sendJSON(String resourceName) throws IOException {
        InputStream is = RabbitMQProducerTest.class.getResourceAsStream("/application.properties");
        Properties p = new Properties();
        p.load(is);
        is.close();

        InputStream dataInputStream = RabbitMQProducerTest.class.getResourceAsStream(resourceName);
        byte[] messageBodyBytes = getBytes(dataInputStream);

        ConnectionParameters params = new ConnectionParameters();
        params.setUsername(p.getProperty("rabbitmq.userName"));
        params.setPassword(p.getProperty("rabbitmq.password"));
        params.setVirtualHost(p.getProperty("rabbitmq.vhost"));
        params.setRequestedHeartbeat(0);
        ConnectionFactory factory = new ConnectionFactory(params);
        String host = p.getProperty("rabbitmq.host");
        Connection conn = factory.newConnection(host, RABBIT_MQ_PORT);
        Channel channel = conn.createChannel();
        String exchangeName = p.getProperty("rabbitmq.exchange");
        String routingKey = p.getProperty("rabbitmq.queue");
        channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_BASIC, messageBodyBytes);
        channel.close();
        conn.close();
    }

    private byte[] getBytes(InputStream dataInputStream) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int b;
        while ((b = dataInputStream.read()) != -1) {
            output.write(b);
        }
        return output.toByteArray();
    }
}
