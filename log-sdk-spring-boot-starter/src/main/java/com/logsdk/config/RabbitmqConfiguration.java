package com.logsdk.config;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by LILIN on 2023/12/9/10:46:22
 */
@Configuration
@EnableConfigurationProperties(LogSdkProperties.class)
@ConditionalOnProperty(prefix = "logsdk.rabbitmq", name = "enable", havingValue = "true", matchIfMissing = true)
public class RabbitmqConfiguration {
    private final LogSdkProperties logSdkProperties;
    private final AmqpAdmin amqpAdmin;

    public RabbitmqConfiguration(LogSdkProperties logSdkProperties,
                                 AmqpAdmin amqpAdmin) {
        this.logSdkProperties = logSdkProperties;
        this.amqpAdmin = amqpAdmin;
    }

    /**
     * 设置交换机和队列，并给交换机绑定队列
     */
    @PostConstruct
    public void setupExchangeAndQueue() {
        LogSdkProperties.Rabbitmq rabbitmq = logSdkProperties.getRabbitmq();

        String exchangeName = rabbitmq.getExchange();
        String queueName = rabbitmq.getQueue();
        String routeKey = rabbitmq.getRouteKey();

        String exchangeErrorName = rabbitmq.getExchangeError();
        String queueErrorName = rabbitmq.getQueueError();
        String routerKeyError = rabbitmq.getRouterKeyError();

        //创建直连交换机
        DirectExchange exchange = new DirectExchange(exchangeName);
        DirectExchange exchangeError = new DirectExchange(exchangeErrorName);
        //注册交换机
        amqpAdmin.declareExchange(exchange);
        amqpAdmin.declareExchange(exchangeError);

        //创建队列
        Queue queue = new Queue(queueName);
        Queue queueError = new Queue(queueErrorName);
        //注册队列
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareQueue(queueError);

        //绑定交换机和队列
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(routeKey);
        amqpAdmin.declareBinding(binding);

        Binding bindingError = BindingBuilder.bind(queueError).to(exchangeError).with(routerKeyError);
        amqpAdmin.declareBinding(bindingError);
    }
}
