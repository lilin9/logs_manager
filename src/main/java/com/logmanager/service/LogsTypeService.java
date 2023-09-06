package com.logmanager.service;

import com.logmanager.entity.LogType;
import com.logmanager.mapper.LogTypeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LILIN on 2023/9/4/17:17:21
 */
@Service
public class LogsTypeService {
    private LogTypeMapper logTypeMapper;
    public LogsTypeService(LogTypeMapper logTypeMapper) {
        this.logTypeMapper = logTypeMapper;
    }

    /**
     * @Return
     * @Description 查询所有日志类型数据
     * @Author LILIN
     * @Date 2023/9/4 17:19:16
     */
    public List<LogType> selectAllLogTypes() {
        return logTypeMapper.selectAll();
    }
}
