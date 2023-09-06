package com.logmanager.controller;

import com.logmanager.entity.MethodType;
import com.logmanager.entity.ResponseResult;
import com.logmanager.enums.ResponseEnum;
import com.logmanager.service.MethodTypeService;
import com.logmanager.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by LILIN on 2023/9/4/17:24:22
 */
@Slf4j
@RestController
@RequestMapping("/methodType")
public class MethodTypeController {
    private final MethodTypeService methodTypeService;
    private final CommonUtils commonUtils;
    public MethodTypeController(MethodTypeService methodTypeService,
                                CommonUtils commonUtils) {
        this.methodTypeService =  methodTypeService;
        this.commonUtils = commonUtils;
    }

    /**
     * @Return
     * @Description 获取所有请求方法类型
     * @Author LILIN
     * @Date 2023/9/4 19:14:25
     */
    @GetMapping("/getAll")
    public ResponseResult<Object> getAllMethodTypes() {
        List<MethodType> methodTypeList = methodTypeService.selectAllMethodTypes();
        log.info("{} 查询请求方法数据 {}",commonUtils.formatDate(), methodTypeList);
        return methodTypeList.isEmpty() ? new ResponseResult<>(ResponseEnum.FAILED) :
                new ResponseResult<>(ResponseEnum.SUCCESS, methodTypeList);
    }
}
