package com.logserver.enums;

import lombok.Getter;

/**
 * Created by LILIN on 2023/8/8/14:07:54
 * 日志类型枚举类
 */
@Getter
public enum LogTypeEnum {
    ERROR("error"),
    INFO("info"),
    DEBUG("debug"),
    WARNING("warning");

    private final String value;

    LogTypeEnum(String value) {
        this.value = value;
    }
}
