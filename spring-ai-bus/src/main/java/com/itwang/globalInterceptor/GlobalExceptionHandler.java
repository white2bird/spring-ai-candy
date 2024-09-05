package com.itwang.globalInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itwang.common.constants.GlobalErrorCodeConstants;
import com.itwang.common.result.CommonResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


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
        return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode(), exception.getMessage());

    }


}
