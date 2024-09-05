package com.itwang.config;

import com.itwang.globalInterceptor.TimeRecordInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yiming@micous.com
 * @date 2024/9/5 15:33
 */
@Configuration
public class AiConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TimeRecordInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/favicon.ico");
    }
}
