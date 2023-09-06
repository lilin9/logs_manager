package com.logmanager.service;

import com.logmanager.entity.MethodType;
import com.logmanager.mapper.MethodTypeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LILIN on 2023/9/4/19:15:36
 */
@Service
public class MethodTypeService {
    private final MethodTypeMapper methodTypeMapper;
    public MethodTypeService(MethodTypeMapper methodTypeMapper) {
        this.methodTypeMapper = methodTypeMapper;
    }

    public List<MethodType> selectAllMethodTypes() {
        return methodTypeMapper.selectAll();
    }
}
