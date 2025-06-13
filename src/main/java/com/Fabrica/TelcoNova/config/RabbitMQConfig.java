package com.Fabrica.TelcoNova.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "notifications.exchange";
    public static final String QUEUE_NAME = "email.queue";
    public static final String ROUTING_KEY = "email.routingkey";

    @Bean
    public DirectExchange notificationExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public Binding binding(Queue emailQueue, DirectExchange notificationExchange) {
        return BindingBuilder.bind(emailQueue)
                             .to(notificationExchange)
                             .with(ROUTING_KEY);
    }
}