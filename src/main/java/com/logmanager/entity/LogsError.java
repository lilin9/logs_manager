package com.logmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LILIN on 2023/8/8/16:40:34
 * 异常实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogsError {
    /**
     * 异常表 id
     */
    private Integer errorId;
    /**
     * 异常名
     */
    private String exceptionName;
    /**
     * 异常信息
     */
    private String exceptionMsg;
}
