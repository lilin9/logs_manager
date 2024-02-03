package com.logserver.mapper;

import com.logserver.entity.Users;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by LILIN on 2023/12/24/12:57:00
 */
@Mapper
public interface UsersMapper {
    /**
     * 添加一个用户
     */
    int insertOne(Users users);

    /**
     * 根据电子邮箱查询用户信息
     */
    Users selectOneByEmail(String email);

    /**
     * 根据用户名查询用户信息
     */
    Users selectOneByUsername(String username);
}
