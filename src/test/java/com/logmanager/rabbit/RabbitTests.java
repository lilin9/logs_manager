package com.logmanager.rabbit;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by LILIN on 2023/8/6/14:40:51
 */
@SpringBootTest
public class RabbitTests {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void rabbitConnectionTest() {
        String msg = "hello rabbit";
        Message message = new Message(msg.getBytes());
        rabbitTemplate.convertAndSend("logExchange", "queue.log.info", message);
        System.out.println("消息发送完毕");
    }
}
