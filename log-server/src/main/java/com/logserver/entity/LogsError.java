package com.logserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by LILIN on 2023/11/19/16:36:26
 * 异常日志实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogsError implements Serializable {
    /**
     * 表 Id
     */
    private int id;
    /**
     * 真实 Id
     */
    private String guid;
    /**
     * 日志数据表Id
     */
    private String infoId;
    /**
     * 详细信息
     */
    private String description;

    /**
     * 操作者
     */
    private String operator;

    /**
     * 操作时间
     */
    private String startTime;

    /**
     * 消耗时间
     */
    private String executeTime;

    /**
     * URL
     */
    private String url;

    /**
     * 请求方法类型
     */
    private String methodType;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private Object parameters;

    /**
     * 请求设备信息
     */
    private String device;
    /**
     * 异常名
     */
    private String exceptionName;
    /**
     * 异常信息
     */
    private String exceptionMsg;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createUser;

    public LogsError(String description, String operator, String  startTime, String executeTime, String url, String methodType, String ip, Object parameters, String device, String exceptionName, String exceptionMsg) {
        this.description = description;
        this.operator = operator;
        this.startTime = startTime;
        this.executeTime = executeTime;
        this.url = url;
        this.methodType = methodType;
        this.ip = ip;
        this.parameters = parameters;
        this.device = device;
        this.exceptionName = exceptionName;
        this.exceptionMsg = exceptionMsg;
    }
}
