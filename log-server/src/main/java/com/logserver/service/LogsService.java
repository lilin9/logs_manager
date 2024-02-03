package com.logserver.service;

import com.logserver.entity.ResponseResult;
import com.logserver.utils.CommonUtils;
import com.logserver.vo.LogsSearch;
import com.logserver.entity.LogsError;
import com.logserver.entity.LogsInfo;
import com.logserver.mapper.LogsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LILIN on 2023/12/23/13:47:59
 * 日志 service 类
 */
@Service
public class LogsService {
    private final LogsMapper logsMapper;
    public LogsService(LogsMapper logsMapper) {
        this.logsMapper = logsMapper;
    }

    /**
     * 批量添加 LogsInfo 数据
     */
    public ResponseResult<Object> insertLogsInfo(List<LogsInfo> list) {
        ResponseResult<Object> responseResult = new ResponseResult<>();

        if (list == null || list.size() == 1) {
            return responseResult.failed();
        }

        list.forEach(item -> {
            item.setGuid(CommonUtils.getGuid());
            item.setCreateTime(CommonUtils.getNowDate());
            item.setCreateUser(item.getOperator());
        });

        int row = logsMapper.InsertLogsInfo(list);
        //判断成功与否
        if (row == 0) {
            return responseResult.failed();
        } else {
            return responseResult.success(row);
        }
    }

    /**
     * 批量添加 LogsError 数据
     * 这里操作了两个数据库，需要使用事务进行约束
     */
    @Transactional
    public ResponseResult<Object> insertLogsError(List<LogsError> list) {
        ResponseResult<Object> responseResult = new ResponseResult<>();

        if (list == null || list.size() == 1) {
            return responseResult.failed();
        }

        //List<LogsError> 转成 List<LogsInfo>
        ArrayList<LogsInfo> logsInfoList = new ArrayList<>();
        list.forEach(item -> {
            LogsInfo logsInfo = CommonUtils.convertObject(item, LogsInfo.class);
            //logsInfo 补全计划
            logsInfo.setGuid(CommonUtils.getGuid());
            logsInfo.setCreateTime(CommonUtils.getNowDate());
            logsInfo.setCreateUser(item.getOperator());
            //融入集体吧
            logsInfoList.add(logsInfo);
            //logsError 补全计划
            item.setGuid(CommonUtils.getGuid());
            item.setInfoId(logsInfo.getGuid());
            item.setCreateTime(CommonUtils.getNowDate());
            item.setCreateUser(item.getOperator());
        });

        //加进数据库
        int infoRow = logsMapper.InsertLogsInfo(logsInfoList);
        int errorRow = logsMapper.InsertLogsError(list);

        if (infoRow <= 0 && errorRow <= 0) {
            return responseResult.failed();
        } else {
            return responseResult.success(infoRow);
        }
    }

    /**
     * 查询所有日志数据
     */
    public ResponseResult<LogsError> selectAllLogs(LogsSearch search) {
        return null;
    }
}
