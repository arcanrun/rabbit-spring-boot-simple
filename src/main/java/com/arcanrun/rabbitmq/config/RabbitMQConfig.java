package com.arcanrun.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String HELLO_QUEUE_NAME = "hello-queue";

    @Bean
    public Queue helloQueue() {
        return new Queue(HELLO_QUEUE_NAME, false, false, true);
    }
}
