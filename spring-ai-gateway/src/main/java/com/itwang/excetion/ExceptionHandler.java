package com.itwang.excetion;

import com.itwang.common.result.CommonResult;
import com.itwang.utils.WebFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.itwang.common.constants.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;


@Order(-1)
@Component
@Slf4j
public class ExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if(response.isCommitted()){
            return Mono.error(ex);
        }
        CommonResult<?> result = null;
        if(ex instanceof ResponseStatusException){
            result = responseStatusExceptionHandler(exchange, (ResponseStatusException) ex);
        }else{
            result = defaultExceptionHandler(exchange, ex);
        }

        return WebFrameworkUtils.writeJSON(exchange, result);


    }

    public CommonResult<?> defaultExceptionHandler(ServerWebExchange exchange,
                                                   Throwable ex) {
        ServerHttpRequest request = exchange.getRequest();
        log.error("[defaultExceptionHandler][uri({}/{}) 发生异常]", request.getURI(), request.getMethod(), ex);
        // TODO 芋艿：是否要插入异常日志呢？
        // 返回 ERROR CommonResult
        return CommonResult.error(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getMsg());
    }

    /**
     * 处理 Spring Cloud Gateway 默认抛出的 ResponseStatusException 异常
     */
    private CommonResult<?> responseStatusExceptionHandler(ServerWebExchange exchange,
                                                           ResponseStatusException ex) {
        // TODO 芋艿：这里要精细化翻译，默认返回用户是看不懂的
        ServerHttpRequest request = exchange.getRequest();
        log.error("[responseStatusExceptionHandler][uri({}/{}) 发生异常]", request.getURI(), request.getMethod(), ex);
        return CommonResult.error(ex.getStatusCode().value(), ex.getReason());
    }
}
