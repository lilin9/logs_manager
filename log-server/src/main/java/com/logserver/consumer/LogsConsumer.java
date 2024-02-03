package com.logserver.consumer;

import com.logserver.entity.LogsError;
import com.logserver.entity.LogsInfo;
import com.logserver.service.LogsService;
import com.logserver.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by LILIN on 2023/8/7/10:06:30
 * rabbitmq 消费者
 * 结合数据库
 * 只对关键日志信息进行记录，减少数据库压力
 *
 */
@Component
@Slf4j
public class LogsConsumer {
    private final String FILE_INFO_PATH = new File("").getAbsolutePath() + "\\src\\main\\resources\\logs\\info\\";
    private final String FILE_ERROR_PATH = new File("").getAbsolutePath() + "\\src\\main\\resources\\logs\\error\\";

    private final LogsService logsService;
    public  LogsConsumer(LogsService logsService) {
        this.logsService = logsService;
    }

    /**
     * 监听消息队列普通日志数据
     */
    @RabbitListener(queues = "log.queue")
    public void process(LogsInfo logsInfo) {
        //将日志数据持久化到本地文本文件内
        //生成文件名
        String filename = "loginfo_" + CommonUtils.getNowDateToString().split(" ")[0];
        try {
            //日志数据写入数据库
            ArrayList<LogsInfo> list = new ArrayList<>();
            list.add(logsInfo);
            logsService.insertLogsInfo(list);
            //日志数据写入本地文件
            fileWrite(filename, FILE_INFO_PATH, logsInfo);
        } catch (Exception e) {
            log.warn("{} 保存普通日志文件失败 {}", CommonUtils.getNowDateToString(), e.getMessage());
        }
        log.info("{} 监听到队列消息 {}", CommonUtils.getNowDateToString(), logsInfo);
    }

    /**
     * 监听消息队列错误日志数据
     */
    @RabbitListener(queues = "log.queue.error")
    public void processExpense(LogsError logsError) {
        //将错误日志数据持久化到本地文本文件内
        //生成文件名
        String filename = "loginfo_" + CommonUtils.getNowDateToString().split(" ")[0];
        try {
            //将日志数据写入数据库
            ArrayList<LogsError> list = new ArrayList<>();
            list.add(logsError);
            logsService.insertLogsError(list);
            //日志数据写入本地文件
            fileWrite(filename, FILE_ERROR_PATH, logsError);
        } catch (Exception e) {
            log.warn("{} 保存错误日志文件失败 {}", CommonUtils.getNowDateToString(), e.getMessage());
        }
        log.info("{} 监听到队列消息 {}", CommonUtils.getNowDateToString(), logsError);
    }

    /**
     * @param filename 文件名
     * @param path     文件路径
     * @param logs     文件数据
     * @Description 往磁盘写入文件
     */
    private <T> void fileWrite(String filename, String path, T logs) throws Exception {
        //文件夹是否存在
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            boolean result = file.mkdir();
            if (result) {
                throw new RuntimeException("文件夹创建失败");
            }
        }

        //拼接处文件写入路径
        path = path + "/" + filename + ".txt";

        //判断文件是否存在
        boolean exists = new File(path).exists();
        //打开写入流
        BufferedWriter writer = new BufferedWriter(new FileWriter(path, exists));

        //获取 logs 的实例对象
        Class<?> clazz = logs.getClass();
        Object logsObj = clazz.getConstructor().newInstance();
        //打印的日志信息
        String msg;
        if (logsObj instanceof LogsInfo logsInfo) {
            msg =
                    "=logs info==== " + logsInfo.getStartTime() + " ===\n" +
                            "LogType\t\t\t:" + "普通日志" + "\n" +
                            "Description\t\t:" + logsInfo.getDescription() + "\n" +
                            "Operator\t\t:" + logsInfo.getOperator() + "\n" +
                            "URL\t\t\t\t:" + logsInfo.getUrl() + "\n" +
                            "ExecuteTime\t\t:" + logsInfo.getExecuteTime() + "\n" +
                            "StartTime\t\t:" + logsInfo.getStartTime() + "\n" +
                            "MethodType\t\t:" + logsInfo.getMethodType() + "\n" +
                            "Ip\t\t\t\t:" + logsInfo.getIp() + "\n" +
                            "Parameters\t\t:" + logsInfo.getParameters() + "\n" +
                            "Result\t\t\t:" + logsInfo.getResult() + "\n" +
                            "Device\t\t\t:" + logsInfo.getDevice() + "\n\n";

        } else {
            LogsError logsError = (LogsError) logsObj;
            msg =
                    "=logs error==== " + logsError.getStartTime() + " ===\n" +
                            "LogType\t\t\t:" + "错误日志" + "\n" +
                            "Description\t\t:" + logsError.getDescription() + "\n" +
                            "Operator\t\t:" + logsError.getOperator() + "\n" +
                            "URL\t\t\t\t:" + logsError.getUrl() + "\n" +
                            "ExecuteTime\t\t:" + logsError.getExecuteTime() + "\n" +
                            "StartTime\t\t:" + logsError.getStartTime() + "\n" +
                            "MethodType\t\t:" + logsError.getMethodType() + "\n" +
                            "Ip\t\t\t\t:" + logsError.getIp() + "\n" +
                            "Parameters\t\t:" + logsError.getParameters() + "\n" +
                            "Device\t\t\t:" + logsError.getDevice() + "\n" +
                            "ExceptionName\t:" + logsError.getExceptionName() + "\n" +
                            "ExceptionMsg\t:" + logsError.getExceptionMsg() + "\n\n";
        }

        //写入日志数据
        writer.write(msg);
        //关闭流
        writer.close();
    }
}
