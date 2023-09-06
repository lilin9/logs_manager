package com.logmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LILIN on 2023/9/5/15:49:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MethodTypeStatistic {
    /**
     * 请求类型表 id
     */
    private Integer methodId;
    /**
     * 请求类型出现的次数
     */
    private Double count;
}
