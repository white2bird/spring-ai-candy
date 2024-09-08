package com.itwang.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文档接口处理
 */
@Configuration
public class ApiDocConfiguration {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(getApiInfo());
    }

    private Info getApiInfo(){
        return new Info()
                .title("spring candy ai")
                .description("糖果ai接口文档")
                .contact(new Contact().name("lumingze"))
                .version("2.0");
    }
}
