package com.logmanager.mapper;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by LILIN on 2023/8/7/14:26:04
 */
@SpringBootTest
public class MethodTypeMapperTests {
    @Resource
    private MethodTypeMapper methodTypeMapper;

    @Test
    public void selectOneByType() {
        System.out.println(methodTypeMapper.selectOneByType("post"));
    }
}
