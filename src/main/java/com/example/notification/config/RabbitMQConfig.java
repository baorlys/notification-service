package com.example.notification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public DirectExchange notificationExchange(@Value("${spring.rabbitmq.direct-exchange}") String directExchange) {
        return new DirectExchange(directExchange);
    }

    @Bean
    public Queue smsQueue(@Value("${spring.rabbitmq.queue.sms}") String smsQueueName) {
        return new Queue(smsQueueName, true);
    }

    @Bean
    public Queue emailQueue(@Value("${spring.rabbitmq.queue.email}") String emailQueueName) {
        return new Queue(emailQueueName, true);
    }

    @Bean
    public Binding smsBinding(DirectExchange notificationExchange,
                              @Value("${spring.rabbitmq.queue.sms}") String smsQueueName,
                              @Value("${spring.rabbitmq.routing-key.sms}") String smsRoutingKey) {
        return BindingBuilder.bind(new Queue(smsQueueName, true)).to(notificationExchange).with(smsRoutingKey);
    }

    @Bean
    public Binding emailBinding(DirectExchange notificationExchange,
                                @Value("${spring.rabbitmq.queue.email}") String emailQueueName,
                                @Value("${spring.rabbitmq.routing-key.email}") String emailRoutingKey) {
        return BindingBuilder.bind(new Queue(emailQueueName, true)).to(notificationExchange).with(emailRoutingKey);
    }
}