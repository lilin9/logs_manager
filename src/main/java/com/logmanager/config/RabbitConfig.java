package com.logmanager.config;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by LILIN on 2023/8/4/15:08:22
 * rabbitMQ配置类
 */
@Configuration
public class RabbitConfig {
    //从配置文件读取
    //交换机名称
    @Value("${my.rabbit.logExchange}")
    private String logExchange;
    //队列名称
    @Value("${my.rabbit.logQueue}")
    private String logQueue;
    //路由 key
    @Value("${my.rabbit.routeKey}")
    private String routeKey;

    //创建一个 AmqpAdmin 对象
    private final AmqpAdmin amqpAdmin;
    //自动注入
    public RabbitConfig(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    /**
     * @Return
     * @Description 设置交换机和队列，并给交换机绑定队列
     * @Author LILIN
     * @Date 2023/8/6 14:05:21
     */
    @PostConstruct
    public void setupExchangeAndQueue() {
        //创建一个主题交换机
        Exchange exchange = new TopicExchange(logExchange);
        //注册交换机
        amqpAdmin.declareExchange(exchange);

        //创建队列
        Queue queue = new Queue(logQueue, true);
        //注册队列
        amqpAdmin.declareQueue(queue);

        //绑定交换机和队列
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(routeKey).noargs();
        amqpAdmin.declareBinding(binding);
    }
}
