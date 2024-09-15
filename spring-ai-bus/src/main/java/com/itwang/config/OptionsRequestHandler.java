package com.itwang.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 处理前后端无nginx跨域的问题
 */
public class OptionsRequestHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if("OPTIONS".equalsIgnoreCase(request.getMethod())){
            // 不进行后续逻辑处理
            return false;
        }
        return true;
    }
}
