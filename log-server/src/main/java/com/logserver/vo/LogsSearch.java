package com.logserver.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LILIN on 2023/12/23/16:32:03
 * 封装搜索日志数据的搜索条件
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "LogsSearch", description = "日志搜索参数实体")
public class LogsSearch {
    private String temp;
}
