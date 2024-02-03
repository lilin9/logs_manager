package com.logserver.controller;

import com.logserver.entity.Users;
import com.logserver.service.UsersService;
import com.logserver.utils.CommonUtils;
import com.logserver.entity.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * Created by LILIN on 2023/12/24/14:59:04
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("users")
@Slf4j
public class UsersController {
    private final UsersService usersService;
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public ResponseResult<Object> login(@RequestBody Users user) {
        log.info("print ==> " + user.getEmail() + ", " + user.getPassword());
        ResponseResult<Object> result = usersService.selectOneByEmail(user.getEmail());
        if (result.getCode() == 200) {
            String lockPassword = ((Users) result.getData()).getPassword();
            if (!CommonUtils.unlockMD5(lockPassword, user.getPassword())) {
                result = new ResponseResult<>().failed("密码错误");
            }
        }
        return result;
    }

    /**
     * 注册用户
     */
    @PostMapping("/register")
    @Operation(summary = "注册用户")
    public ResponseResult<Object> register(@RequestBody Users users) {
        return usersService.insertOne(users);
    }
}
