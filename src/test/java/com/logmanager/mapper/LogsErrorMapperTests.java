package com.logmanager.mapper;

import com.logmanager.entity.LogsError;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by LILIN on 2023/8/8/16:52:31
 */
@SpringBootTest
public class LogsErrorMapperTests {
    @Resource
    private LogsErrorMapper logsErrorMapper;

    @Test
    public void insertError() {
        LogsError logsError = new LogsError(null, "test", "test");
        System.out.println("数据插入成功，主键ID是 " + logsErrorMapper.insertError(logsError));
    }
}
