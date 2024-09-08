package com.itwang.globalInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itwang.common.constants.GlobalErrorCodeConstants;
import com.itwang.common.result.CommonResult;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * 全局错误拦截 拦截器需要增加controller
 * @author yiming@micous.com
 * @date 2024/9/4 15:42
 */
@Slf4j
@Order(-1)
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public CommonResult<?> handleException(Exception exception){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String uri = "";
        if(request != null){
            uri = request.getRequestURI();
        }
        log.info("handle exception url {} {}", uri, exception.getMessage(), exception);
        return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode(), exception.getMessage());

    }


}
