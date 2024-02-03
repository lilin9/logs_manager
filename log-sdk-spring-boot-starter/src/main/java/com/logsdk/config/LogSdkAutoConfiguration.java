package com.logsdk.config;

import com.logsdk.service.IOperatorService;
import com.logsdk.service.impl.DefaultOperatorServiceImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * Created by LILIN on 2023/11/19/13:45:13
 * 自定义 start 配置类
 */
@Configuration
@ComponentScan("com.logsdk")
@EnableConfigurationProperties({LogSdkProperties.class})
@ConditionalOnProperty(prefix = "logsdk", name = "enable", havingValue = "true", matchIfMissing = true)
public class LogSdkAutoConfiguration {
    @Bean
    @Role(BeanDefinition.ROLE_APPLICATION)
    @ConditionalOnMissingBean(IOperatorService.class)
    public IOperatorService operatorService() {
        return new DefaultOperatorServiceImpl();
    }
}
