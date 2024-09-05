package com.itwang.globalInterceptor;

import cn.hutool.core.date.StopWatch;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.TimeUnit;

/**
 * @author yiming@micous.com
 * @date 2024/9/5 15:08
 */
@Slf4j
public class TimeRecordInterceptor implements HandlerInterceptor, Ordered {

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object attribute = request.getAttribute("org.springframework.web.servlet.HandlerMapping.producibleMediaTypes");
        System.out.println(attribute);
        if (request.getAttribute("org.springframework.web.servlet.HandlerMapping.producibleMediaTypes") != null) {
            // 这是第二次拦截，专门用于 SSE 设置
            System.out.println("SSE specific interception");
        } else {
            // 这是第一次拦截，用于常规 HTTP 请求处理
            System.out.println("Regular HTTP request interception");
        }
        log.info(request.getRequestURI() + "进入处理" + Thread.currentThread().getName());
        StopWatch stopWatch = new StopWatch();
        // 设置上下文
        request.setAttribute("stopWatch", stopWatch);
        stopWatch.start();
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Object stopWatch = request.getAttribute("stopWatch");
        String requestURI = request.getRequestURI();
        if(stopWatch instanceof StopWatch){
            StopWatch watch = (StopWatch) stopWatch;
            watch.stop();
            log.info("request --- {} ---- take time {} {}", requestURI, watch.shortSummary(TimeUnit.SECONDS), Thread.currentThread().getName());
        }
    }
}
