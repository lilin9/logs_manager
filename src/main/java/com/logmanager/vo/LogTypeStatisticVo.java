package com.logmanager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LILIN on 2023/9/5/15:48:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogTypeStatisticVo {
    /**
     * 日志类型
     */
    private String type;
    /**
     * 日志类型出现的次数
     */
    private Double count;
}
