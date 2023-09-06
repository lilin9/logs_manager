package com.logmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by LILIN on 2023/9/4/13:59:15
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    //跨域配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  //允许所有接口
                .allowCredentials(true) //是否发生 cookie
                .allowedOriginPatterns("*") //支持域
                .allowedMethods("GET", "POST", "PUT", "DELETE") //支持的方法
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}







