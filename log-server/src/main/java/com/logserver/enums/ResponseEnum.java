package com.logserver.enums;

import lombok.Getter;

/**
 * Created by LILIN on 2023/8/6/15:53:04
 */
@Getter
public enum ResponseEnum {
    SUCCESS(200, "成功"),
    FAILED(500, "失败");

    private final Integer code;
    private final String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
