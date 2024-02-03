package com.logserver.mapper;

import com.logserver.entity.LogsError;
import com.logserver.entity.LogsInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by LILIN on 2023/12/23/12:41:02
 * 日志 mapper 类
 */
@Mapper
public interface LogsMapper {
    /**
     * 批量添加 LogsInfo 数据
     */
    int InsertLogsInfo(List<LogsInfo> list);

    /**
     * 批量添加 LogsError 数据
     */
    int InsertLogsError(List<LogsError> list);
}
