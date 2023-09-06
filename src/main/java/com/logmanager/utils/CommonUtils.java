package com.logmanager.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LILIN on 2023/8/4/13:59:17
 * 通用工具类
 */
@Component
@Slf4j
public class CommonUtils {
    /**
     * @Return
     * @Description 对日期进行格式化
     * @Author LILIN
     * @Date 2023/8/6 17:32:45
     */
    public String formatDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        return format.format(date);
    }

    /**
     * @param sourceObj 被转换的对象
     * @param newClass 要转换成为的对象的类
     * @Return
     * @Description 将一个 对象 转换成指定的 另一个对象
     * @Author LILIN
     * @Date 2023/8/7 14:59:27
     */
    public <T, Q>T convertObject(Q sourceObj, Class<T> newClass) {
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
}
















