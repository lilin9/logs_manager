package com.logmanager.mapper;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by LILIN on 2023/8/7/14:25:10
 */
@SpringBootTest
public class LogTypeMapperTests {
    @Resource
    private LogTypeMapper logTypeMapper;

    @Test
    public void selectOneByType() {
        System.out.println(logTypeMapper.selectOneByType("info"));
    }
}
