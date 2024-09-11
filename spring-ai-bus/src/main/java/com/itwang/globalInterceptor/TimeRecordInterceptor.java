package com.itwang.globalInterceptor;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author yiming@micous.com
 * @date 2024/9/5 15:08
 */
@Slf4j
public class TimeRecordInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 全局上下文放入UUID
        String traceId = request.getHeader("traceId");
        traceId = StrUtil.isEmpty(traceId) ? UUID.randomUUID().toString() : traceId;
        MDC.put("traceId", traceId);
        StopWatch stopWatch = new StopWatch();

        log.info("当前登陆用户 --- {}", StpUtil.isLogin() ? StpUtil.getLoginIdAsLong() : "none");
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
        MDC.clear();
    }
}
