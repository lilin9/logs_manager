package com.logmanager.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by LILIN on 2023/8/4/17:04:59
 * 和 rabbitmq 有关的工具类
 */
@Component
@Slf4j
public class RabbitUtils {
    //交换机名称
    @Value("${my.rabbit.logExchange}")
    private String logExchange;

    private final RabbitTemplate rabbitTemplate;
    private final CommonUtils commonUtils;

    public RabbitUtils(RabbitTemplate rabbitTemplate,
                       CommonUtils commonUtils) {
        this.rabbitTemplate = rabbitTemplate;
        this.commonUtils = commonUtils;
    }

    /**
     * @param routingKey 路由key
     * @param msg 要发送的消息
     * @Return
     * @Description 往 rabbitmq 交换机发送消息
     * @Author LILIN
     * @Date 2023/8/6 15:18:11
     */
    public void sendMessage(String routingKey, Object msg) {
        //发送消息
        rabbitTemplate.convertAndSend(logExchange, routingKey, msg);
        log.info("{} 消息发送完毕!", commonUtils.formatDate());
    }
}
