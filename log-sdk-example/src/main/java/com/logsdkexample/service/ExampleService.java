package com.logsdkexample.service;

import com.logsdk.service.IOperatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by LILIN on 2023/11/26/14:29:38
 */
@Service
@Slf4j
public class ExampleService implements IOperatorService {
    @Override
    public String getOperator() {
        return "admin";
    }
}
