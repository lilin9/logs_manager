package com.logmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LILIN on 2023/8/4/14:08:27
 * 日志实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogsSubject {
    /**
     * 日志数据表 id
     */
    private Integer logsId;
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
     * 请求类型表 id
     */
    private Integer methodId;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private String parameter;

    /**
     * 请求返回的结果
     */
    private String result;

    /**
     * 请求设备信息
     */
    private String device;
    /**
     * 日志类型表 id
     */
    private Integer typeId;
    /**
     * 异常表 id
     */
    private Integer errorId;
}