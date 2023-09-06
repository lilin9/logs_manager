package com.logmanager.service;

import com.logmanager.vo.LogsVo;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by LILIN on 2023/9/3/15:10:40
 */
@SpringBootTest
public class LogsServiceTests {
    @Resource
    private LogsService logsService;

    @Test
    public void selectAllLogsTest() {
        logsService.selectAllLogs().forEach(System.out::println);
    }

    @Test
    public void searchLogsTest() {
        LogsVo logsVo = new LogsVo();
        logsVo.setStartTime("2023年08月11日");
        logsService.searchLogs(logsVo).forEach(System.out::println);
    }
}
