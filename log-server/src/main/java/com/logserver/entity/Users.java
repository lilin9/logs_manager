package com.logserver.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by LILIN on 2023/12/24/12:57:46
 * 用户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Users", description = "用户实体类")
public class Users {
    /**
     * 主键id
     */
    @Schema(name = "id", description = "主键id")
    private Integer id;
    /**
     * 真实Id
     */
    @Schema(name = "guid", description = "真实Id")
    private String guid;
    /**
     * 用户名
     */
    @Schema(name = "username", description = "用户名")
    private String username;
    /**
     * 密码
     */
    @Schema(name = "password", description = "密码")
    private String password;
    /**
     * 电子邮箱
     */
    @Schema(name = "email", description = "电子邮箱")
    private String email;
    /**
     * 创建时间
     */
    @Schema(name = "createTime", description = "创建时间")
    private Date createTime;
    /**
     * 创建人
     */
    @Schema(name = "createUser", description = "创建人")
    private String createUser;
}
