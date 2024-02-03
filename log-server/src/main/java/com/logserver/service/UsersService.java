package com.logserver.service;

import com.logserver.entity.Users;
import com.logserver.mapper.UsersMapper;
import com.logserver.utils.CommonUtils;
import com.logserver.entity.ResponseResult;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by LILIN on 2023/12/24/13:58:56
 */
@Service
public class UsersService {
    private final UsersMapper usersMapper;

    public UsersService(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    /**
     * 添加一个用户
     */
    public ResponseResult<Object> insertOne(Users user) {
        ResponseResult<Object> result = new ResponseResult<>();
        if (Objects.isNull(user)) {
            return result.failed();
        }
        String returnMsg = "";
        if (Strings.isEmpty(user.getUsername())) {
            returnMsg = "用户名不能为空";
        }
        if (Strings.isEmpty(user.getEmail())) {
            returnMsg = "邮箱不能为空";
        }
        if (Strings.isEmpty(user.getPassword())) {
            returnMsg = "密码不能为空";
        }
        if (Strings.isNotEmpty(returnMsg)) {
            return result.failed(returnMsg);
        }

        //用户名重复
        if (Strings.isNotEmpty(user.getUsername()) &&
                !Objects.isNull(usersMapper.selectOneByUsername(user.getUsername()))) {
            return result.failed("用户名已存在");
        }

        //邮箱重复
        if (Strings.isNotEmpty(user.getEmail()) &&
                Objects.nonNull(usersMapper.selectOneByEmail(user.getEmail()))) {
            return result.failed("邮箱已存在");
        }

        user.setGuid(CommonUtils.getGuid());
        user.setPassword(CommonUtils.lockMD5(user.getPassword()));
        user.setCreateTime(CommonUtils.getNowDate());
        user.setCreateUser(user.getUsername());

        int row = usersMapper.insertOne(user);
        if (row <= 0) {
            return result.failed("添加用户信息失败");
        } else {
            return result.success();
        }
    }

    /**
     * 根据邮箱查询用户信息
     */
    public ResponseResult<Object> selectOneByEmail(String email) {
        ResponseResult<Object> result = new ResponseResult<>();
        if (Strings.isEmpty(email)) {
            return result.failed("邮箱不能为空");
        }

        Users users = usersMapper.selectOneByEmail(email);
        if (Objects.isNull(users)) {
            return result.failed("邮箱不存在");
        }
        return result.success(users);
    }

}
