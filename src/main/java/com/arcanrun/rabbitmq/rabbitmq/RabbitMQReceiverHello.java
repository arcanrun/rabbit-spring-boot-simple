package com.arcanrun.rabbitmq.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.arcanrun.rabbitmq.config.RabbitMQConfig.HELLO_QUEUE_NAME;

@Slf4j
@Component
@RabbitListener(queues = HELLO_QUEUE_NAME)
public class RabbitMQReceiverHello {

    @RabbitHandler
    public void receive(String message) {
        log.info("{}#receive: message = {}", getClass(), message);
    }
}
