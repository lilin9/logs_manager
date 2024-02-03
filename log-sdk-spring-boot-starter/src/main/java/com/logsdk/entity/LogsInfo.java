package com.logsdk.entity;

import java.io.Serializable;

/**
 * Created by LILIN on 2023/11/19/16:36:26
 * 普通日志实体
 */
public class LogsInfo implements Serializable {
    /**
     * 描述信息
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
     * 请求方法类型：get、post...
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
     * 请求返回的结果
     */
    private Object result;

    /**
     * 请求设备信息
     */
    private String device;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Object getParameters() {
        return parameters;
    }

    public void setParameters(Object parameters) {
        this.parameters = parameters;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
