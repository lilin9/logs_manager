package com.logmanager.consumer;

import com.logmanager.service.LogsService;
import com.logmanager.utils.CommonUtils;
import com.logmanager.vo.LogsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by LILIN on 2023/8/7/10:06:30
 * 日志处理类，监听四个队列消息并进行处理
 */
@Component
@Slf4j
public class LogsReceiver {
    //读取配置文件
    @Value("${my.file.filenamePrefix}")
    private String filenamePrefix;
    @Value("${my.file.filepath}")
    private String filepath;

    private final LogsService logsService;
    private final CommonUtils commonUtils;

    public LogsReceiver(LogsService logsService,
                        CommonUtils commonUtils) {
        this.logsService = logsService;
        this.commonUtils = commonUtils;
    }

    //监听队列
    @RabbitListener(queues = "log.queue")
    public void process(LogsVo logsVo) {
        //将日志数据存入数据库
        logsService.insertLogs(logsVo);

        //将日志数据持久化到本地文件
        String filename = filenamePrefix + commonUtils.formatDate().split(" ")[0];
        try {
            fireWrite(filename, filepath, logsVo);
        } catch (IOException e) {
            log.error("写入文件失败 {}", e.getMessage());
        }
        log.info("{} 监听到队列消息 {}", commonUtils.formatDate(), logsVo);
    }

    /**
     * @param filename 文件名
     * @param path     文件路径
     * @param logsVo   文件数据
     * @Return
     * @Description 往磁盘写入文件
     * @Author LILIN
     * @Date 2023/8/7 11:06:36
     */
    private void fireWrite(String filename, String path, LogsVo logsVo) throws IOException {
        //拼接处文件写入路径
        path = path + "/" + filename + ".txt";

        //判断文件是否存在
        boolean exists = new File(path).exists();
        //打开写入流
        BufferedWriter writer = new BufferedWriter(new FileWriter(path, exists));

        //打印的日志信息
        String msg;
        if (logsVo.getExceptionName() == null || logsVo.getExceptionName().isEmpty()) {
            msg =
                    "=日志信息==== " + logsVo.getStartTime() + " ===\n" +
                            "日志类型\t\t:" + logsVo.getType() + "\n" +
                            "操作描述\t\t:" + logsVo.getDescription() + "\n" +
                            "操作用户\t\t:" + logsVo.getUsername() + "\n" +
                            "URL\t\t\t:" + logsVo.getUrl() + "\n" +
                            "消耗时间\t\t:" + logsVo.getSpendTime() + "\n" +
                            "请求类型\t\t:" + logsVo.getMethod() + "\n" +
                            "IP地址\t\t:" + logsVo.getIp() + "\n" +
                            "请求参数\t\t:" + logsVo.getParameter() + "\n" +
                            "请求返回的结果\t:" + logsVo.getResult() + "\n" +
                            "请求设备信息\t:" + logsVo.getDevice() + "\n\n";

        } else {
            msg =
                    "=日志信息==== " + logsVo.getStartTime() + " ===\n" +
                            "日志类型\t\t:" + logsVo.getType() + "\n" +
                            "操作描述\t\t:" + logsVo.getDescription() + "\n" +
                            "操作用户\t\t:" + logsVo.getUsername() + "\n" +
                            "URL\t\t\t:" + logsVo.getUrl() + "\n" +
                            "消耗时间\t\t:" + logsVo.getSpendTime() + "\n" +
                            "请求类型\t\t:" + logsVo.getMethod() + "\n" +
                            "IP地址\t\t:" + logsVo.getIp() + "\n" +
                            "请求参数\t\t:" + logsVo.getParameter() + "\n" +
                            "请求设备信息\t:" + logsVo.getDevice() + "\n" +
                            "异常名\t\t:" + logsVo.getExceptionName() + "\n" +
                            "异常信息\t\t:" + logsVo.getExceptionMsg() + "\n\n";
        }

        //写入日志数据
        writer.write(msg);
        //关闭流
        writer.close();
    }
}
