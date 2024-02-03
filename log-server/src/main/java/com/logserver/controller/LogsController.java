package com.logserver.controller;
import com.logserver.entity.ResponseResult;
import com.logserver.service.LogsService;
import com.logserver.vo.LogsSearch;
import com.logserver.entity.LogsError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by LILIN on 2023/12/23/16:29:52
 */
//@Tag(name = "日志管理")
@RestController
@RequestMapping("logs")
public class LogsController {
    private final LogsService logsService;
    public LogsController(LogsService logsService) {
        this.logsService = logsService;
    }

    /**
     * 查询所有日志数据
     */
//    @Operation(summary = "查询所有日志数据")
    @GetMapping("/getAllLogs")
    public ResponseResult<LogsError> getAllLogs(@RequestParam LogsSearch search) {
        return logsService.selectAllLogs(search);
    }
}
