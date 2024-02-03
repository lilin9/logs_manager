package com.logsdk.annotation;

import java.lang.annotation.*;

/**
 * Created by LILIN on 2023/11/19/14:56:04
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogsRecord {
    //操作者
    String Operator() default "";
    //说明
    String Description() default "";
}
