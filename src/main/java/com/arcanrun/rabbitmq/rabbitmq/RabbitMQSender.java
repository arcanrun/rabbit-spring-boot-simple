package com.arcanrun.rabbitmq.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.arcanrun.rabbitmq.config.RabbitMQConfig.HELLO_DIRECT_EXCHANGE;
import static com.arcanrun.rabbitmq.config.RabbitMQConfig.HELLO_FANOUT_EXCHANGE;
import static com.arcanrun.rabbitmq.config.RabbitMQConfig.HELLO_QUEUE_NAME;
import static com.arcanrun.rabbitmq.config.RabbitMQConfig.HELLO_TOPIC_EXCHANGE;

@Component
@RequiredArgsConstructor
public class RabbitMQSender {
    private final RabbitTemplate rabbitTemplate;

    public void sendMessageToDefaultExchange(String message) {
        rabbitTemplate.convertAndSend(HELLO_QUEUE_NAME, message);
    }

    public void sendMessageFanout(String message) {
        rabbitTemplate.convertAndSend(HELLO_FANOUT_EXCHANGE, "", message);
    }

    public void sendMessageWithRoutingKey(String message, String routingKey) {
        rabbitTemplate.convertAndSend(HELLO_DIRECT_EXCHANGE, routingKey, message);
    }

    public void sendMessageToTopics(String message, String key) {
        rabbitTemplate.convertAndSend(HELLO_TOPIC_EXCHANGE, key, message);
    }
}
