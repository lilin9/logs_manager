package com.logserver.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by LILIN on 2024/1/1/20:25:01
 * swagger 配置类
 */
//@Configuration
@Slf4j
public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        //打印版本信息和 swagger 访问地址
        printVersionLog();
        return new OpenAPI()
                .info(new Info().title("日志系统API")
                        .description("日志系统API文档")
                        .version("v1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("外部文档")
                        .url("https://springshop.wiki.github.org/docs"));
    }

    /**
     * 打印版本信息和访问地址
     */
    private void printVersionLog() {
        log.info("\n");
        log.info("http://localhost:9090/swagger-ui/index.html");
        log.info("artifactId: spring-boot-starter-parent");
        log.info("version   : 3.1.0");
        log.info("artifactId: springdoc-openapi-starter-webmvc-api ");
        log.info("version   : 2.1.0");
        log.info("artifactId: springdoc-openapi-starter-webmvc-ui");
        log.info("version   : 2.0.4\n");
    }
}
