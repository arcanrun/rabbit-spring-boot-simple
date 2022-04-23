package com.arcanrun.rabbitmq.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.arcanrun.rabbitmq.config.RabbitMQConfig.HELLO_QUEUE_NAME;

@Component
@RequiredArgsConstructor
public class Sender {
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(HELLO_QUEUE_NAME, message);
    }
}
