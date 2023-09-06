package com.logmanager.mapper;

import com.logmanager.entity.MethodType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by LILIN on 2023/8/7/14:14:38
 */
@Mapper
public interface MethodTypeMapper {
    /**
     * @param methodType methodType
     * @Return
     * @Description 根据请求方法类型查询请求类型表
     * @Author LILIN
     * @Date 2023/8/7 14:17:50
     */
    MethodType selectOneByType(String methodType);

    /**
     * @param methodId id
     * @Return
     * @Description 根据id查一条数据
     * @Author LILIN
     * @Date 2023/9/3 14:54:26
     */
    MethodType selectOneById(Integer methodId);

    /**
     * @Return
     * @Description 查询所有请求方法类型数据
     * @Author LILIN
     * @Date 2023/9/4 19:19:05
     */
    List<MethodType> selectAll();
}
