package com.logmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LILIN on 2023/8/7/14:13:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogType {
    /**
     * 日志类型表 id
     */
    private Integer typeId;

    /**
     * 日志类型
     */
    private String type;
}
