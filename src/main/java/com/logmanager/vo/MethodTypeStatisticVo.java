package com.logmanager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LILIN on 2023/9/5/15:49:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MethodTypeStatisticVo {
    /**
     * 请求类型名
     */
    private String method;
    /**
     * 请求类型出现的次数
     */
    private Double count;
}
