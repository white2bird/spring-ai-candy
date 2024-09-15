package com.itwang.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import com.itwang.globalInterceptor.TimeRecordInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yiming@micous.com
 * @date 2024/9/5 15:27
 */
@Configuration
public class HttpConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new OptionsRequestHandler());
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                // 放行如下接口 不进行登录审核
                .excludePathPatterns("/user/login", "/user/register")
                .excludePathPatterns("/swagger/**", "/v3/**", "/doc.html")
                .excludePathPatterns("/favicon.ico")
                .excludePathPatterns("/webjars/**")
                ;

        registry.addInterceptor(new TimeRecordInterceptor());
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
        ;

    }
}
