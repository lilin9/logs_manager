package com.logmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LILIN on 2023/9/5/15:48:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogTypeStatistic {
    /**
     * 日志类型表 id
     */
    private Integer typeId;
    /**
     * 日志类型出现的次数
     */
    private Double count;
}
