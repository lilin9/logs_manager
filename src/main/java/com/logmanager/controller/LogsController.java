package com.logmanager.controller;

import com.logmanager.entity.ResponseResult;
import com.logmanager.enums.ResponseEnum;
import com.logmanager.service.LogsService;
import com.logmanager.utils.CommonUtils;
import com.logmanager.utils.RabbitUtils;
import com.logmanager.vo.LogTypeStatisticVo;
import com.logmanager.vo.LogsVo;
import com.logmanager.vo.MethodTypeStatisticVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by LILIN on 2023/8/6/15:39:40
 * 和 日志操作 有关的控制类
 */
@RequestMapping("/logs")
@RestController
@Slf4j
public class LogsController {
    //获取配置文件中的路由key
    @Value("${my.rabbit.routeKey}")
    private String routeKey;

    private final RabbitUtils rabbitUtils;
    private final CommonUtils commonUtils;
    private final LogsService logsService;
    public LogsController(RabbitUtils rabbitUtils,
                          CommonUtils commonUtils,
                          LogsService logsService) {
        this.rabbitUtils = rabbitUtils;
        this.commonUtils = commonUtils;
        this.logsService = logsService;
    }

    /**
     * @param logsVo 传入日志实体
     * @Return
     * @Description 保存日志数据
     * @Author LILIN
     * @Date 2023/8/6 16:04:02
     */
    @PostMapping("/save")
    public ResponseResult<Object> saveLogs(@RequestBody LogsVo logsVo) {
        log.info("{} 接收到消息 {}",commonUtils.formatDate(), logsVo);
        rabbitUtils.sendMessage(routeKey, logsVo);
        return new ResponseResult<>().success();
    }

    /**
     * @Return
     * @Description 查询所有日志信息
     * @Author LILIN
     * @Date 2023/9/3 14:17:55
     */
    @GetMapping("/getAll")
    public ResponseResult<Object> getAllLogs() {
        List<LogsVo> logsVoList = logsService.selectAllLogs();
        log.info("{} 查询日志数据 {}",commonUtils.formatDate(), logsVoList);
        return logsVoList.isEmpty() ? new ResponseResult<>(ResponseEnum.FAILED) :
                new ResponseResult<>(ResponseEnum.SUCCESS, logsVoList);
    }

    /**
     * @param logsVo logsVo
     * @Return
     * @Description 搜索日志数据
     * @Author LILIN
     * @Date 2023/9/5 09:54:30
     */
    @PostMapping("/searchLog")
    public ResponseResult<Object> searchLogs(@RequestBody LogsVo logsVo) {
        List<LogsVo> logsVoList = logsService.searchLogs(logsVo);
        log.info("{} 搜索日志数据 {}",commonUtils.formatDate(), logsVoList);
        return logsVoList.isEmpty() ? new ResponseResult<>(ResponseEnum.FAILED) :
                new ResponseResult<>(ResponseEnum.SUCCESS, logsVoList);
    }

    /**
     * @Return
     * @Description 统计所有日志数据中不同种类的日志出现的次数
     * @Author LILIN
     * @Date 2023/9/5 15:38:11
     */
    @GetMapping("/countLogsType")
    public ResponseResult<Object> countLogsType() {
        List<LogTypeStatisticVo> logTypeStatisticVoList = logsService.countLogsType();
        log.info("{} 日志数据中不同类型日志出现的次数 {}",commonUtils.formatDate(), logTypeStatisticVoList);
        return  logTypeStatisticVoList.isEmpty() ? new ResponseResult<>(ResponseEnum.FAILED) :
                new ResponseResult<>(ResponseEnum.SUCCESS, logTypeStatisticVoList);
    }

    /**
     * @Return
     * @Description 统计所有日志数据中不同请求方法出现的次数
     * @Author LILIN
     * @Date 2023/9/5 15:39:08
     */
    @GetMapping("/countMethodType")
    public ResponseResult<Object> countMethodType() {
        List<MethodTypeStatisticVo> methodTypeStatisticVoList = logsService.countMethodType();
        log.info("{} 日志数据中不同请求方法出现的次数 {}",commonUtils.formatDate(), methodTypeStatisticVoList);
        return  methodTypeStatisticVoList.isEmpty() ? new ResponseResult<>(ResponseEnum.FAILED) :
                new ResponseResult<>(ResponseEnum.SUCCESS, methodTypeStatisticVoList);
    }
}
