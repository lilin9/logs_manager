package com.logmanager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by LILIN on 2023/8/4/14:08:27
 * 日志实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogsVo implements Serializable {
    /**
     * 操作描述
     */
    private String description;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 操作时间
     */
    private String startTime;

    /**
     * 消耗时间
     */
    private String spendTime;

    /**
     * URL
     */
    private String url;

    /**
     * 请求类型
     */
    private String method;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private Object parameter;

    /**
     * 请求返回的结果
     */
    private Object result;

    /**
     * 请求设备信息
     */
    private String device;
    /**
     * 日志类型
     */
    private String type;
    /**
     * 异常名
     */
    private String exceptionName;
    /**
     * 异常信息
     */
    private String exceptionMsg;
}