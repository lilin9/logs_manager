package com.logserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by LILIN on 2023/11/19/16:36:26
 * 普通日志实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogsInfo implements Serializable {
    /**
     * 表 Id
     */
    private int id;
    /**
     * 真实 Id
     */
    private String guid;
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
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createUser;
}
