package com.logserver.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by LILIN on 2023/8/4/13:59:17
 * 通用工具类
 */
@Component
@Slf4j
public class CommonUtils {
    /**
     * @Description 对日期进行格式化，返回字符串类型
     */
    public static String getNowDateToString() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
    /**
     * @Description 对日期进行格式化，返回日期类型
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * @param sourceObj 被转换的对象
     * @param newClass 要转换成为的对象的类
     * @Description 将一个 对象 转换成指定的 另一个对象
     */
    public static  <T, Q>T convertObject(Q sourceObj, Class<T> newClass) {
        //判空
        if (null == sourceObj) {
            throw new RuntimeException("对象不能为空");
        }

        //声明一个对象
        T toObj = null;
        try {
            //获取要转换成的对象实例
            toObj = newClass.getDeclaredConstructor().newInstance();
            //copy 属性
            BeanUtils.copyProperties(sourceObj, toObj);
        } catch (Exception e) {
            log.error("转换类对象出现错误:\n{}", e.getMessage());
        }
        return toObj;
    }

    /**
     * @param sourceList 被转换的列表
     * @param newClass 要转换成为的对象的类
     * @Description 批量将一个 对象 转换成指定的 另一个对象
     */
    public static <T, Q>List<T> convertObject(List<Q> sourceList, Class<T> newClass) {
        return sourceList.stream().map(item -> convertObject(item, newClass)).toList();
    }

    /**
     * 获得 UUID
     */
    public static String getGuid() {
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }

    /**
     * MD5 加密
     */
    public static String lockMD5(String password) {
        if (Strings.isEmpty(password)) {
            return null;
        }

        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    /**
     * 进行密码匹配，是否相同
     */
    public static boolean unlockMD5(String lockPassword, String password) {
        if (Strings.isEmpty(lockPassword) || Strings.isEmpty(password)) {
            return false;
        }

        String lockPas = DigestUtils.md5DigestAsHex(password.getBytes());
        return lockPas.equals(lockPassword);
    }
}