package com.example.notification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public DirectExchange notificationExchange() {
        return new DirectExchange("notificationExchange");
    }

    @Bean
    public Queue smsQueue() {
        return new Queue("smsQueue", true);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue("emailQueue", true);
    }

    @Bean
    public Queue pushQueue() {
        return new Queue("pushQueue", true);
    }

    @Bean
    public Binding smsBinding(DirectExchange notificationExchange, Queue smsQueue) {
        return BindingBuilder.bind(smsQueue).to(notificationExchange).with("smsRoutingKey");
    }

    @Bean
    public Binding emailBinding(DirectExchange notificationExchange, Queue emailQueue) {
        return BindingBuilder.bind(emailQueue).to(notificationExchange).with("emailRoutingKey");
    }

    @Bean
    public Binding pushBinding(DirectExchange notificationExchange, Queue pushQueue) {
        return BindingBuilder.bind(pushQueue).to(notificationExchange).with("pushRoutingKey");
    }
}
