package com.logsdkexample.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by LILIN on 2023/11/26/14:11:14
 */
@Data
public class ResponseResult implements Serializable {
    private int code;
    private boolean isSuccess;
    private String message;
}
