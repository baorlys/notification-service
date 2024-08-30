package com.example.notification.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public FanoutExchange fanoutExchange(@Value("${spring.rabbitmq.fanout-exchange}") String fanoutExchange) {
        return new FanoutExchange(fanoutExchange);
    }

    @Bean
    public DirectExchange notificationExchange(@Value("${spring.rabbitmq.direct-exchange}") String directExchange) {
        return new DirectExchange(directExchange);
    }

    @Bean
    public Queue notificationQueue(@Value("${spring.rabbitmq.queue.notification}") String notificationQueueName) {
        return new Queue(notificationQueueName, true);
    }

    @Bean
    public Binding notificationBinding(FanoutExchange fanoutExchange,
                                       Queue notificationQueue) {
        return BindingBuilder.bind(notificationQueue).to(fanoutExchange);
    }


    @Bean
    public Queue smsQueue(@Value("${spring.rabbitmq.queue.sms}") String smsQueueName) {
        return new Queue(smsQueueName, true);
    }

    @Bean
    public Binding smsBinding(DirectExchange notificationExchange,
                              @Value("${spring.rabbitmq.queue.sms}") String smsQueueName,
                              @Value("${spring.rabbitmq.routing-key.sms}") String smsRoutingKey) {
        return BindingBuilder.bind(new Queue(smsQueueName, true)).to(notificationExchange).with(smsRoutingKey);
    }

    @Bean
    public Queue emailQueue(@Value("${spring.rabbitmq.queue.email}") String emailQueueName) {
        return new Queue(emailQueueName, true);
    }

    @Bean
    public Binding emailBinding(DirectExchange notificationExchange,
                                @Value("${spring.rabbitmq.queue.email}") String emailQueueName,
                                @Value("${spring.rabbitmq.routing-key.email}") String emailRoutingKey) {
        return BindingBuilder.bind(new Queue(emailQueueName, true)).to(notificationExchange).with(emailRoutingKey);
    }

    @Bean
    public Queue voiceQueue(@Value("${spring.rabbitmq.queue.voice}") String voiceQueueName) {
        return new Queue(voiceQueueName, true);
    }

    @Bean
    public Binding voiceBinding(DirectExchange notificationExchange,
                                @Value("${spring.rabbitmq.queue.voice}") String voiceQueueName,
                                @Value("${spring.rabbitmq.routing-key.voice}") String voiceRoutingKey) {
        return BindingBuilder.bind(new Queue(voiceQueueName, true)).to(notificationExchange).with(voiceRoutingKey);
    }


}