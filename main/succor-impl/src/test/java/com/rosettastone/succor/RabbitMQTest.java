package com.rosettastone.succor;

import java.io.IOException;

import org.testng.annotations.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConnectionParameters;
import com.rabbitmq.client.QueueingConsumer;

@Test
public class RabbitMQTest {

    private static final int RABBIT_MQ_PORT = 5672;

    @Test
    public void rabbitMQTest() throws IOException {
        ConnectionParameters parameters = new ConnectionParameters();
        parameters.setUsername("guest");
        parameters.setPassword("guest");
        ConnectionFactory factory = new ConnectionFactory(parameters);

        Connection conn = factory.newConnection("rshbgdev11.lan.flt", RABBIT_MQ_PORT);

        Channel channel = conn.createChannel();
        String exchangeName = "exName";
        String routingKey = "amq-key";

        channel.exchangeDeclare(exchangeName, "direct", true);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, exchangeName, routingKey);


        byte[] messageBodyBytes = "Hello, world!".getBytes();
        channel.basicPublish(exchangeName, routingKey, null, messageBodyBytes);


        boolean noAck = false;
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, noAck, consumer);
        int i = 0;
        while (i++ == 0) {
            QueueingConsumer.Delivery delivery;
            try {
                delivery = consumer.nextDelivery();
            } catch (InterruptedException ie) {
                continue;
            }
            // (process the message components ...)
            String message = new String(delivery.getBody());
            System.out.println("Received message: "  + message);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }

        channel.close();
        conn.close();
    }
}
