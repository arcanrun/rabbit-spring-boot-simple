package com.arcanrun.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String HELLO_QUEUE_NAME = "hello.queue";
    public static final String BYE_QUEUE_NAME = "bye.queue";
    public static final String HELLO_FANOUT_EXCHANGE = "hello.fanout.exchange";


    @Bean
    public Queue helloQueue() {
        return new Queue(HELLO_QUEUE_NAME, false, false, true);
    }

    @Bean
    public Queue byeQueue() {
        return new Queue(BYE_QUEUE_NAME, false, false, true);
    }


    @Bean
    public FanoutExchange helloFanoutExchange() {
        return new FanoutExchange(HELLO_FANOUT_EXCHANGE, false, true);
    }

    @Bean
    public Binding bindingHello() {
        return BindingBuilder
                .bind(helloQueue())
                .to(helloFanoutExchange());
    }

    @Bean
    Binding bindingBye() {
        return BindingBuilder
                .bind(byeQueue())
                .to(helloFanoutExchange());
    }
}
