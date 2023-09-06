package com.logmanager.controller;

import com.logmanager.entity.LogType;
import com.logmanager.entity.ResponseResult;
import com.logmanager.enums.ResponseEnum;
import com.logmanager.service.LogsTypeService;
import com.logmanager.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by LILIN on 2023/9/4/17:13:56
 */
@Slf4j
@RestController
@RequestMapping("/logsType")
public class LogsTypeController {
    private final LogsTypeService logsTypeService;
    private final CommonUtils commonUtils;
    public LogsTypeController(LogsTypeService logsTypeService,
                              CommonUtils commonUtils) {
        this.logsTypeService = logsTypeService;
        this.commonUtils = commonUtils;
    }

    /**
     * @Return
     * @Description 获取所有日志类型
     * @Author LILIN
     * @Date 2023/9/4 17:14:59
     */
    @GetMapping("getAll")
    public ResponseResult<Object> getAllLogsType() {
        List<LogType> logTypeList = logsTypeService.selectAllLogTypes();
        log.info("{} 查询日志类型数据 {}",commonUtils.formatDate(), logTypeList);
        return logTypeList.isEmpty() ? new ResponseResult<>(ResponseEnum.FAILED) :
                new ResponseResult<>(ResponseEnum.SUCCESS, logTypeList);
    }
}
