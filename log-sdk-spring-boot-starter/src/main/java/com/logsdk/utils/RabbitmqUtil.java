package com.logsdk.utils;

import com.logsdk.config.LogSdkProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by LILIN on 2023/11/19/16:21:48
 * Rabbitmq 消息队列工具类
 */
@Component
@Slf4j
public class RabbitmqUtil {
    private final RabbitTemplate rabbitTemplate;
    private final LogSdkProperties logSdkProperties;
    public RabbitmqUtil(RabbitTemplate rabbitTemplate,
                        LogSdkProperties logSdkProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.logSdkProperties = logSdkProperties;
    }

    /**
     * 发送普通消息到 RabbitMQ
     */
    public void sendLogs(Object message) {
        //获取 mq 路由键和交换机
        String routeKey = logSdkProperties.getRabbitmq().getRouteKey();
        String exchange = logSdkProperties.getRabbitmq().getExchange();

        if (Strings.isEmpty(routeKey) || Strings.isEmpty(exchange)) {
            throw new RuntimeException("路由 key 和交换机不能为空");
        }
        rabbitTemplate.convertAndSend(exchange, routeKey, message);

        String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.info("{}发送一条日志信息到RabbitMQ{}", nowDate, message);
    }

    /**
     * 发送异常消息到 RabbitMQ
     */
    public void sendExceptionLogs(Object message) {
        //获取 mq 路由键和交换机
        String routerKeyError = logSdkProperties.getRabbitmq().getRouterKeyError();
        String exchangeError = logSdkProperties.getRabbitmq().getExchangeError();

        if (Strings.isEmpty(routerKeyError) || Strings.isEmpty(exchangeError)) {
            throw new RuntimeException("路由 key 和交换机不能为空");
        }
        rabbitTemplate.convertAndSend(exchangeError, routerKeyError, message);

        String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.info("{}发送一条异常日志信息到RabbitMQ{}", nowDate, message);
    }
}
