package com.itwang.globalInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itwang.common.constants.GlobalErrorCodeConstants;
import com.itwang.common.result.CommonResult;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Mono;


/**
 * todo 全局拦截不能全部返回application结果 拦截todo 优化
 * 全局错误拦截 拦截器需要增加controller
 * @author yiming@micous.com
 * @date 2024/9/4 15:42
 */
@Slf4j
@Order(-1)
@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
    public Mono<ServerSentEvent<CommonResult>> handleException(Exception ex, ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_EVENT_STREAM);

        CommonResult errorResult = new CommonResult();
        errorResult.setCode(500);
        errorResult.setData("An error occurred: " + ex.getMessage());

        return Mono.just(ServerSentEvent.<CommonResult>builder()
                .event("error")
                .data(errorResult)
                .build());
    }

    @ExceptionHandler(AsyncRequestTimeoutException.class)
    @ResponseBody
    public SseEmitter sseTimeoutException(AsyncRequestTimeoutException e) {
        log.error("SSE TIMEOUT EXCEPTION:{}", e.getMessage(), e);
        return null;
    }

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
