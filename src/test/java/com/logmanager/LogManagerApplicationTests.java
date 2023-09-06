package com.logmanager;

import com.logmanager.utils.CommonUtils;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LogManagerApplicationTests {
    @Resource
    private CommonUtils commonUtils;

    @Test
    void contextLoads() {
        String msg =
                "=日志信息==== " + "logsVo.getStartTime()" + " ===\n" +
                        "日志类型\t\t:" + "logsVo.getType()" + "\n" +
                        "操作描述\t\t:" + "logsVo.getDescription()" + "\n" +
                        "操作用户\t\t:" + "logsVo.getUsername()" + "\n" +
                        "URL\t\t\t:" + "logsVo.getUrl()" + "\n" +
                        "消耗时间\t\t:" + "logsVo.getSpendTime()" + "\n" +
                        "请求类型\t\t:" + "logsVo.getMethod()" + "\n" +
                        "IP地址\t\t:" + "logsVo.getIp()" + "\n" +
                        "请求参数\t\t:" + "logsVo.getParameter()" + "\n" +
                        "请求设备信息\t:" + "logsVo.getDevice()" + "\n" +
                        "异常名\t\t:" + "logsVo.getExceptionName()" + "\n" +
                        "异常信息\t\t:" + "logsVo.getExceptionMsg()" + "\n\n";

        System.out.println(msg);
    }

}
