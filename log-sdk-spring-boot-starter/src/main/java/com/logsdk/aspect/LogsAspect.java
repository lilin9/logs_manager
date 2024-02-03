package com.logsdk.aspect;

import com.logsdk.annotation.LogsRecord;
import com.logsdk.entity.LogsError;
import com.logsdk.entity.LogsInfo;
import com.logsdk.service.IOperatorService;
import com.logsdk.utils.RabbitmqUtil;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by LILIN on 2023/11/19/14:33:06
 */
@Aspect
@Component
public class LogsAspect {
    //方法开始时间
    private String methodStartDate;
    //方法执行时间
    private String methodExecuteTime;

    private final RabbitmqUtil rabbitmqUtil;
    private final HttpServletRequest request;
    private final IOperatorService operatorService;

    public LogsAspect(RabbitmqUtil rabbitmqUtil,
                      HttpServletRequest request,
                      IOperatorService operatorService) {
        this.rabbitmqUtil = rabbitmqUtil;
        this.request = request;
        this.operatorService = operatorService;
    }

    /**
     * 定义切点
     */
    @Pointcut("@annotation(com.logsdk.annotation.LogsRecord)")
    public void pointCut() {
    }

    /**
     * 通过后置通知，拦截所有普通日志信息
     */
    @AfterReturning(value = "pointCut()", returning = "returns")
    public void interceptOperateLogs(JoinPoint joinPoint, Object returns) {
        LogsInfo logsInfo = getLogsInfo(joinPoint, returns);
        rabbitmqUtil.sendLogs(logsInfo);
    }

    /**
     * 通过后置通知，拦截所有异常日志信息
     */
    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void interceptExceptionLogs(JoinPoint joinPoint, Throwable exception) {
        LogsError logsError = getLogsErrorInfo(joinPoint, exception);
        rabbitmqUtil.sendExceptionLogs(logsError);
    }

    /**
     * 通过环绕通知，获取注解所在方法的开始执行时间，与执行消耗时间
     */
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        //获取当前时间戳
        long startTimeMillis = System.currentTimeMillis();
        //获取当前格式化时间
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd/HH-mm-ss");
        methodStartDate = dateFormat.format(date);

        //执行当前方法
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();

            //获取当前时间戳
            long endTimeMillis = System.currentTimeMillis();
            //获取方法执行时间
            methodExecuteTime = (endTimeMillis - startTimeMillis) + "ms";
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return proceed;
    }

    /**
     * 获取普通日志实体
     */
    private LogsInfo getLogsInfo(JoinPoint joinPoint, Object returns) {
        LogsInfo logsInfo = new LogsInfo();

        //获取注解当前所在的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //获取 LogsRecord 注解
        if (method.isAnnotationPresent(LogsRecord.class)) {
            LogsRecord logsRecord = method.getAnnotation(LogsRecord.class);
            //获取 @LogsRecord 的描述信息
            logsInfo.setDescription(logsRecord.Description());

            //获取 @LogsRecord 的操作人信息
            String operator = logsRecord.Operator();
            if (Strings.isEmpty(operator)) {
                operator = operatorService.getOperator();
            }
            logsInfo.setOperator(operator);
        }

        //设置方法开始执行时间
        logsInfo.setStartTime(methodStartDate);
        //设置方法执行消耗时间
        logsInfo.setExecuteTime(methodExecuteTime);
        //获取方法请求路径
        logsInfo.setUrl(request.getRequestURL().toString());
        //获取请求方法类型
        logsInfo.setMethodType(request.getMethod().toLowerCase());
        //获取 IP 地址
        logsInfo.setIp(getIpAddress(request));
        //获取方法的请求参数
        logsInfo.setParameters(getParameters(method, joinPoint.getArgs()));
        //获取请求返回结果
        if (!Objects.isNull(returns)) {
            logsInfo.setResult(returns);
        }
        //获取请求设备信息
        logsInfo.setDevice(request.getHeader("User-Agent"));

        return logsInfo;
    }

    /**
     * 获取异常日志实体
     */
    private LogsError getLogsErrorInfo(JoinPoint joinPoint, Throwable exception) {
        LogsError logsError = new LogsError();

        //获取注解当前所在的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //获取 LogsRecord 注解
        if (method.isAnnotationPresent(LogsRecord.class)) {
            LogsRecord logsRecord = method.getAnnotation(LogsRecord.class);
            //获取 @LogsRecord 的描述信息
            logsError.setDescription(logsRecord.Description());

            //获取 @LogsRecord 的操作人信息
            String operator = logsRecord.Operator();
            if (Strings.isEmpty(operator)) {
                operator = operatorService.getOperator();
            }
            logsError.setOperator(operator);
        }

        //设置方法开始执行时间
        logsError.setStartTime(methodStartDate);
        //设置方法执行消耗时间
        logsError.setExecuteTime(methodExecuteTime);
        //获取方法请求路径
        logsError.setUrl(request.getRequestURL().toString());
        //获取请求方法类型
        logsError.setMethodType(request.getMethod().toLowerCase());
        //获取 IP 地址
        logsError.setIp(getIpAddress(request));
        //获取方法的请求参数
        logsError.setParameters(getParameters(method, joinPoint.getArgs()));
        //获取请求设备信息
        logsError.setDevice(request.getHeader("User-Agent"));
        //获取异常名
        logsError.setExceptionName(exception.getClass().getName());
        //获取异常信息
        logsError.setExceptionMsg(formatExceptionMessage(
                logsError.getExceptionName(),
                exception.getMessage(),
                exception.getStackTrace()
        ));

        return logsError;
    }

    /**
     * 异常信息格式化
     */
    public String formatExceptionMessage(String exceptionName,
                                         String exceptionMessage,
                                         StackTraceElement[] elements) {
        StringBuilder stringBuffer = new StringBuilder();
        for (StackTraceElement stet : elements) {
            stringBuffer.append(stet).append("\n");
        }
        return exceptionName + ":" + exceptionMessage + "\n\t" + stringBuffer;
    }

    /**
     * 根据方法和传入的参数，获取请求参数
     */
    public Object getParameters(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();

        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            //过滤掉参数中可能包含的 ServletRequest、ServletResponse等内容
            if (args[i] instanceof ServletRequest ||
                    args[i] instanceof ServletResponse ||
                    args[i] instanceof MultipartFile) {
                continue;
            }

            //将 @RequestBody 注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            //将 @RequestParam 注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);

            //获取参数名
            String key = parameters[i].getName();
            if (!Objects.isNull(requestBody)) {
                argList.add(args[i]);
            } else if (!Objects.isNull(requestParam)) {
                map.put(key, args[i]);
            } else {
                map.put(key, args[i]);
            }
        }

        if (map.size() > 0) {
            argList.add(map);
        }
        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }

    /**
     * 获取请求的 IP 地址
     */
    public String getIpAddress(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    if (inet != null) {
                        ipAddress = inet.getHostAddress();
                    }
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }
}
