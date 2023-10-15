package com.example.sellars.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUEUE_NAME = "q.provider-queue";
    public static final String EXCHANGE_NAME = "x.provider-exchange";
    public static final String QUEUE_NAME2 = "q.delivery-queue";
    public static final String EXCHANGE_NAME2 = "x.delivery-exchange";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME, false, false);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).withQueueName();
    }

    @Bean
    public Queue queue2() {
        return new Queue(QUEUE_NAME2, false);
    }

    @Bean
    public DirectExchange exchange2() {
        return new DirectExchange(EXCHANGE_NAME2, false, false);
    }

    @Bean
    Binding binding2(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).withQueueName();
    }
}
