package com.logmanager.service;

import com.logmanager.entity.*;
import com.logmanager.mapper.LogTypeMapper;
import com.logmanager.mapper.LogsErrorMapper;
import com.logmanager.mapper.LogsMapper;
import com.logmanager.mapper.MethodTypeMapper;
import com.logmanager.utils.CommonUtils;
import com.logmanager.vo.LogTypeStatisticVo;
import com.logmanager.vo.LogsVo;
import com.logmanager.vo.MethodTypeStatisticVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by LILIN on 2023/8/7/14:49:51
 * 日志 service 类
 */
@Service
@Slf4j
public class LogsService {
    private final LogsMapper logsMapper;
    private final MethodTypeMapper methodTypeMapper;
    private final LogTypeMapper logTypeMapper;
    private final LogsErrorMapper logsErrorMapper;
    private final CommonUtils commonUtils;

    public LogsService(LogsMapper logsMapper,
                       MethodTypeMapper methodTypeMapper,
                       LogTypeMapper logTypeMapper,
                       LogsErrorMapper logsErrorMapper,
                       CommonUtils commonUtils) {
        this.logsMapper = logsMapper;
        this.methodTypeMapper = methodTypeMapper;
        this.logTypeMapper = logTypeMapper;
        this.logsErrorMapper = logsErrorMapper;
        this.commonUtils = commonUtils;
    }

    /**
     * @param logsVo logsVo
     * @Return
     * @Description TODO
     * @Author LILIN
     * @Date 2023/8/7 14:52:02
     */
    public void insertLogs(LogsVo logsVo) {
        //判空
        if (Objects.isNull(logsVo)) {
            log.error("insertLogs(LogsVo logsVo) 参数不能为空!");
            return;
        }
        //查询 methodTypeId 和 LogTypeId
        String type = logsVo.getType();
        LogType logType = logTypeMapper.selectOneByType(type);
        MethodType methodType = methodTypeMapper.selectOneByType(logsVo.getMethod());

        //如果没有异常信息，就不走下面这段代码
        Integer errorId = null;
        if (logsVo.getExceptionName() != null || logsVo.getExceptionMsg() != null) {
            //将异常消息存入数据库中
            errorId = logsErrorMapper.insertError(new LogsError(
                    null,
                    logsVo.getExceptionName(),
                    logsVo.getExceptionMsg()
            ));
        }

        //将 logsVo 转换成 logsSubject
        LogsSubject logsSubject = commonUtils.convertObject(logsVo, LogsSubject.class);
        //填充缺失数据
        logsSubject.setTypeId(logType.getTypeId());
        logsSubject.setMethodId(methodType.getMethodId());
        logsSubject.setErrorId(errorId);
        if (logsVo.getParameter() != null)
            logsSubject.setParameter(logsVo.getParameter().toString());
        if (logsVo.getResult() != null)
            logsSubject.setResult(logsVo.getResult().toString());

        //存入数据库
        logsMapper.insertLogs(logsSubject);
    }

    /**
     * @Return
     * @Description 查询所有日志数据
     * @Author LILIN
     * @Date 2023/9/3 14:51:08
     */
    public List<LogsVo> selectAllLogs() {
        //返回结果
        List<LogsVo> logsVoList = new ArrayList<>();

        List<LogsSubject> logsSubjects = logsMapper.selectAll();
        if (logsSubjects.isEmpty()) return logsVoList;

        LogsVo logsVo;
        MethodType methodType;
        LogType logType;
        LogsError logsError;
        for (LogsSubject item : logsSubjects) {
            //将 LogsSubject 转换成 logsVo
            logsVo = commonUtils.convertObject(item, LogsVo.class);

            //查询请求方法
            methodType = methodTypeMapper.selectOneById(item.getMethodId());
            //查询日志类别
            logType = logTypeMapper.selectOneById(item.getTypeId());
            //查询异常信息
            logsError = logsErrorMapper.selectOneById(item.getErrorId());

            //填充 logsVo 缺失的部分
            if (Objects.nonNull(methodType)) logsVo.setMethod(methodType.getMethod());
            if (Objects.nonNull(logType)) logsVo.setType(logType.getType());
            if (Objects.nonNull(logsError)) {
                logsVo.setExceptionName(logsError.getExceptionName());
                logsVo.setExceptionMsg(logsError.getExceptionMsg());
            }

            //加到数组
            logsVoList.add(logsVo);
        }
        return logsVoList;
    }

    /**
     * @param logsVo logsVo
     * @Return
     * @Description 根据条件搜索日志数据
     * @Author LILIN
     * @Date 2023/9/5 09:57:41
     */
    public List<LogsVo> searchLogs(LogsVo logsVo) {
        if (Objects.isNull(logsVo)) return null;

        //将 logsVo 转换成 LogsSubject
        LogsSubject logsSubject = commonUtils.convertObject(logsVo, LogsSubject.class);

        //查询日志类型id
        LogType logType = logTypeMapper.selectOneByType(logsVo.getType());
        //查询请求方法 id
        MethodType methodType = methodTypeMapper.selectOneByType(logsVo.getMethod());

        //补全 logsSubject
        if (Objects.nonNull(logType)) logsSubject.setTypeId(logType.getTypeId());
        if (Objects.nonNull(methodType)) logsSubject.setMethodId(methodType.getMethodId());

        //根据条件查询日志数据
        List<LogsSubject> logsSubjectList = logsMapper.selectLogsByLike(logsSubject);
        //循环遍历数组，将其转换成 logsVo
        List<LogsVo> logsVoList = new ArrayList<>();
        for (LogsSubject item : logsSubjectList) {
            //查询日志类型
            logType = logTypeMapper.selectOneById(item.getTypeId());
            //查询请求方法类型
            methodType = methodTypeMapper.selectOneById(item.getMethodId());

            //转换
            logsVo = commonUtils.convertObject(item, LogsVo.class);
            //补全
            logsVo.setMethod(methodType.getMethod());
            logsVo.setType(logType.getType());

            //添加进数组
            logsVoList.add(logsVo);
        }

        return logsVoList;
    }

    /**
     * @Return
     * @Description 统计所有日志数据中不同种类的日志出现的次数
     * @Author LILIN
     * @Date 2023/9/5 15:58:13
     */
    public List<LogTypeStatisticVo> countLogsType() {
        List<LogTypeStatistic> logTypeStatisticList = logTypeMapper.countLogsType();

        LogType logType;
        LogTypeStatisticVo logTypeStatisticVo;
        List<LogTypeStatisticVo> logTypeStatisticVoList = new ArrayList<>();
        for (LogTypeStatistic item : logTypeStatisticList) {
            logType = logTypeMapper.selectOneById(item.getTypeId());

            logTypeStatisticVo = commonUtils.convertObject(item, LogTypeStatisticVo.class);
            logTypeStatisticVo.setType(logType.getType());

            logTypeStatisticVoList.add(logTypeStatisticVo);
        }

        return logTypeStatisticVoList;
    }

    /**
     * @Return
     * @Description 统计所有日志数据中不同请求方法出现的次数
     * @Author LILIN
     * @Date 2023/9/5 16:05:46
     */
    public List<MethodTypeStatisticVo> countMethodType() {
        List<MethodTypeStatistic> methodTypeStatisticList = logTypeMapper.countMethodType();

        MethodType methodType;
        MethodTypeStatisticVo methodTypeStatisticVo;
        List<MethodTypeStatisticVo> methodTypeStatisticVoList = new ArrayList<>();
        for (MethodTypeStatistic item : methodTypeStatisticList) {
            methodType = methodTypeMapper.selectOneById(item.getMethodId());

            methodTypeStatisticVo = commonUtils.convertObject(item, MethodTypeStatisticVo.class);
            methodTypeStatisticVo.setMethod(methodType.getMethod());

            methodTypeStatisticVoList.add(methodTypeStatisticVo);
        }

        return methodTypeStatisticVoList;
    }
}
