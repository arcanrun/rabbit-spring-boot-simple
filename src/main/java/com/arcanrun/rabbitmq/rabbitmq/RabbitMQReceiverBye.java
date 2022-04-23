package com.arcanrun.rabbitmq.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.arcanrun.rabbitmq.config.RabbitMQConfig.BYE_QUEUE_NAME;

@Slf4j
@Component
@RabbitListener(queues = BYE_QUEUE_NAME)
public class RabbitMQReceiverBye {

    @RabbitHandler
    public void receive(String message) {
        log.info("{}#receive: message = {}", getClass(), message);
    }
}
