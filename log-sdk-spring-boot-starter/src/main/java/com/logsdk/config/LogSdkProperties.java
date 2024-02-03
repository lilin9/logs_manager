package com.logsdk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * Created by LILIN on 2023/11/19/15:45:32
 */
@ConfigurationProperties(prefix = "logsdk")
@Data
public class LogSdkProperties implements Serializable {
    /**
     * 是否开启日志管理系统，true 开启，false 不开启
     */
    private boolean enable = true;

    private Rabbitmq rabbitmq = new Rabbitmq();

    public boolean isEnable() {
        return enable;
    }

    @Data
    public static class Rabbitmq {
        /**
         * 是否启用 rabbitmq
         */
        private Boolean enable = true;
        /**
         * 普通日志路由 key
         */
        private String routeKey;
        /**
         * 普通日志交换机
         */
        private String exchange;
        /**
         * 普通日志队列
         */
        private String queue;


        /**
         * 错误日志路由 key
         */
        private String routerKeyError;
        /**
         * 错误日志交换机
         */
        private String exchangeError;
        /**
         * 错误日志队列
         */
        private String queueError;
    }
}
