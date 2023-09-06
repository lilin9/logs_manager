package com.logmanager.mapper;

import com.logmanager.entity.LogsSubject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by LILIN on 2023/8/7/11:53:45
 */
@Mapper
public interface LogsMapper {
    /**
     * @param logsSubject logsSubject
     * @Return
     * @Description 往数据库插入一条日志信息
     * @Author LILIN
     * @Date 2023/8/7 13:56:25
     */
    void insertLogs(LogsSubject logsSubject);

    /**
     * @Return
     * @Description 查询所有日志数据
     * @Author LILIN
     * @Date 2023/9/3 14:45:48
     */
    List<LogsSubject> selectAll();

    /**
     * @param logsSubject logsSubject
     * @Return
     * @Description 根据条件模糊查询日志数据
     * @Author LILIN
     * @Date 2023/9/5 10:15:05
     */
    List<LogsSubject> selectLogsByLike(LogsSubject logsSubject);
}
