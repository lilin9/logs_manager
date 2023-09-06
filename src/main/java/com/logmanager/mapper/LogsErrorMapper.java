package com.logmanager.mapper;

import com.logmanager.entity.LogsError;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by LILIN on 2023/8/8/16:42:48
 */
@Mapper
public interface LogsErrorMapper {
    /**
     * @param logsError logsError
     * @Return 返回主键
     * @Description 插入一条异常日志信息
     * @Author LILIN
     * @Date 2023/8/8 16:43:38
     */
    Integer insertError(LogsError logsError);

    /**
     * @param errorId errorId
     * @Return
     * @Description 查询一条数据，根绝id
     * @Author LILIN
     * @Date 2023/9/3 15:03:24
     */
    LogsError selectOneById(Integer errorId);
}
