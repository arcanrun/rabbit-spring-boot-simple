package com.arcanrun.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String HELLO_QUEUE_NAME = "hello.queue";
    public static final String BYE_QUEUE_NAME = "bye.queue";

    public static final String ROUTING_KEY_ONE = "one";
    public static final String ROUTING_KEY_TWO = "two";

    public static final String HELLO_FANOUT_EXCHANGE = "hello.fanout.exchange";
    public static final String HELLO_DIRECT_EXCHANGE = "hello.direct.exchange";
    public static final String HELLO_TOPIC_EXCHANGE = "hello.topic.exchange";


    // Queues
    @Bean
    public Queue helloQueue() {
        return new Queue(HELLO_QUEUE_NAME, false, false, true);
    }

    @Bean
    public Queue byeQueue() {
        return new Queue(BYE_QUEUE_NAME, false, false, true);
    }

    // Exchanges
    @Bean
    public FanoutExchange helloFanoutExchange() {
        return new FanoutExchange(HELLO_FANOUT_EXCHANGE, false, true);
    }

    @Bean
    public DirectExchange helloDirectExchange() {
        return new DirectExchange(HELLO_DIRECT_EXCHANGE, false, true);
    }

    @Bean
    public TopicExchange helloTopicExchange() {
        return new TopicExchange(HELLO_TOPIC_EXCHANGE, false, true);
    }

    // Bindings
    @Bean
    public Binding bindingHello() {
        return BindingBuilder
                .bind(helloQueue())
                .to(helloFanoutExchange());
    }

    @Bean
    public Binding bindingBye() {
        return BindingBuilder
                .bind(byeQueue())
                .to(helloFanoutExchange());
    }

    @Bean
    public Binding bindingHelloDirect() {
        return BindingBuilder
                .bind(helloQueue())
                .to(helloDirectExchange())
                .with(ROUTING_KEY_ONE);
    }

    @Bean
    public Binding bindingHelloDirect2() {
        return BindingBuilder
                .bind(helloQueue())
                .to(helloDirectExchange())
                .with(ROUTING_KEY_TWO);
    }

    @Bean
    public Binding bindingByeDirect() {
        return BindingBuilder
                .bind(byeQueue())
                .to(helloDirectExchange())
                .with(ROUTING_KEY_TWO);
    }

    @Bean
    public Binding bindingHelloTopic() {
        return BindingBuilder
                .bind(helloQueue())
                .to(helloTopicExchange())
                .with("foo.bar.*");
    }

    @Bean
    public Declarables bindingByeTopic2() {
        var binding = BindingBuilder
                .bind(byeQueue())
                .to(helloTopicExchange())
                .with("#.bar.baz");
        var binding2 = BindingBuilder
                .bind(byeQueue())
                .to(helloTopicExchange())
                .with("*.bar.baz");

        return new Declarables(binding, binding2);
    }
}
