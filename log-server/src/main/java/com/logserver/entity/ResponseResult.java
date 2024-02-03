package com.logserver.entity;

import com.logserver.enums.ResponseEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LILIN on 2023/7/27/10:54:56
 * 统一返回响应类
 */
@NoArgsConstructor
@Data
public class ResponseResult<T> {
    //返回代码  统一设置：200成功，500 错误
    private Integer code;
    //返回信息
    private String message;
    //返回数据
    private T data;

    public ResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(ResponseEnum responseEnum, T data) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
        this.data = data;
    }

    public ResponseResult(ResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

    /**
     * 成功
     */
    public ResponseResult<Object> success() {
        return new ResponseResult<>(ResponseEnum.SUCCESS);
    }

    /**
     * 成功，提供返回数据
     */
    public ResponseResult<T> success(T data) {
        return new ResponseResult<>(ResponseEnum.SUCCESS, data);
    }

    /**
     * 失败
     */
    public ResponseResult<Object> failed() {
        return new ResponseResult<>(ResponseEnum.FAILED);
    }
    /**
     * 失败，提供返回数据
     */
    public ResponseResult<T> failed(T data) {
        return new ResponseResult<>(ResponseEnum.FAILED, data);
    }
    /**
     * 失败，自定义返回信息
     */
    public ResponseResult<Object> failed(String msg) {
        return new ResponseResult<>(500, msg);
    }
}
