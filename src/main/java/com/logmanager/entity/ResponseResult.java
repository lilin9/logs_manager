package com.logmanager.entity;

import com.logmanager.enums.ResponseEnum;
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
     * @Return
     * @Description 成功
     * @Author LILIN
     * @Date 2023/8/6 15:59:00
     */
    public ResponseResult<Object> success() {
        return new ResponseResult<>(ResponseEnum.SUCCESS);
    }

    /**
     * @Return
     * @Description 失败
     * @Author LILIN
     * @Date 2023/8/6 15:59:09
     */
    public ResponseResult<Object> failed() {
        return new ResponseResult<>(ResponseEnum.FAILED);
    }
}
