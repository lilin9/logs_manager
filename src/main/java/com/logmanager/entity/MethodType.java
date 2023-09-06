package com.logmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LILIN on 2023/8/7/14:07:32
 * 请求类型实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MethodType {
    /**
     * 请求类型表 id
     */
    private Integer methodId;

    /**
     * 请求类型名
     */
    private String method;
}
