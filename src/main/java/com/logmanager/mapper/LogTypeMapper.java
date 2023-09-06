package com.logmanager.mapper;

import com.logmanager.entity.LogType;
import com.logmanager.entity.LogTypeStatistic;
import com.logmanager.entity.MethodTypeStatistic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by LILIN on 2023/8/7/14:15:45
 */
@Mapper
public interface LogTypeMapper {
    /**
     * @param logType logType
     * @Return
     * @Description 根据日志类型查询日志类型表
     * @Author LILIN
     * @Date 2023/8/7 14:17:25
     */
    LogType selectOneByType(String logType);

    /**
     * @param typeId typeId
     * @Return
     * @Description 根据id查询一条数据
     * @Author LILIN
     * @Date 2023/9/3 14:59:38
     */
    LogType selectOneById(Integer typeId);

    /**
     * @Return
     * @Description 查询所有数据
     * @Author LILIN
     * @Date 2023/9/5 16:04:19
     */
    List<LogType> selectAll();

    /**
     * @Return
     * @Description 统计所有日志数据中不同种类的日志出现的次数
     * @Author LILIN
     * @Date 2023/9/5 16:04:37
     */
    List<LogTypeStatistic> countLogsType();

    /**
     * @Return
     * @Description 统计所有日志数据中不同请求方法出现的次数
     * @Author LILIN
     * @Date 2023/9/5 16:08:11
     */
    List<MethodTypeStatistic> countMethodType();
}
