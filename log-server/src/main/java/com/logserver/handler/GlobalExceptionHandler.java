package com.logserver.handler;

import com.logserver.entity.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by LILIN on 2024/1/1/21:50:55
 * 全局异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理空指针异常
     * @return 返回自定义返回类
     */
    @ResponseBody
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseResult<Object> exceptionHandler(HttpServletRequest request,
                                                   NullPointerException e) {
        log.error("出现空指针异常\n" + e.getMessage());
        return new ResponseResult<>().failed(e.getMessage());
    }

    /**
     * 其他异常
     * @return 返回自定义返回类
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseResult<Object> exceptionHandler(HttpServletRequest request,
                                                   Exception e) {
        log.error("出现未知异常\n" + e.getMessage());
        return new ResponseResult<>().failed(e.getMessage());
    }
}
