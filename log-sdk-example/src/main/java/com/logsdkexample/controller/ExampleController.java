package com.logsdkexample.controller;

import com.logsdk.annotation.LogsRecord;
import com.logsdkexample.entity.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by LILIN on 2023/11/26/14:10:34
 */
@RestController
@RequestMapping("example")
@Slf4j
public class ExampleController {
    @GetMapping("/test")
    @LogsRecord(Description = "测试程序")
    public ResponseResult Test() {
        ResponseResult result = new ResponseResult();
        result.setCode(200);
        result.setSuccess(true);
        result.setMessage("hello world");

        String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.info("{} Test执行 {}", nowDate, result);
        return result;
    }

    @GetMapping("/expense/{id}")
    @LogsRecord(Description = "异常测试")
    public ResponseResult ExpenseTest(@PathVariable String id) {
        if (Integer.parseInt(id) == 1) {
            ResponseResult result = new ResponseResult();
            result.setCode(200);
            result.setSuccess(true);
            result.setMessage("success");
        } else {
            throw new RuntimeException("发生了一个异常，但是我不知道怎么回事");
        }
        return null;
    }
}
