package com.itwang.globalInterceptor;

import cn.dev33.satoken.exception.NotLoginException;
import com.itwang.common.constants.GlobalErrorCodeConstants;
import com.itwang.common.result.CommonResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



/**
 * todo 全局拦截不能全部返回application结果 拦截todo 优化
 * 全局错误拦截 拦截器需要增加controller
 * @author yiming@micous.com
 * @date 2024/9/4 15:42
 */
@Slf4j
@Order(-1)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public CommonResult<?> handleNotLoginException(NotLoginException e) {
        log.info("handle not login exception", e);
        return CommonResult.error(GlobalErrorCodeConstants.UNAUTHORIZED.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public CommonResult<?> handleException(Exception exception) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String uri = "";
        uri = request.getRequestURI();
        log.info("handle exception url {} {}", uri, exception.getMessage(), exception);
        return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode(), exception.getMessage());

    }


}
