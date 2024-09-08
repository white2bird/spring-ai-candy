package com.itwang.utils;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
public class WebFrameworkUtils {


    private WebFrameworkUtils(){}


    public static Mono<Void> writeJSON(ServerWebExchange exchange, Object o){
        ServerHttpResponse serverResponse = exchange.getResponse();
        serverResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return serverResponse.writeWith(Mono.fromSupplier(()->{
            DataBufferFactory bufferFactory = serverResponse.bufferFactory();
            try{
                return bufferFactory.wrap(JsonUtils.toJsonByte(o));
            }catch (Exception e){
                ServerHttpRequest request = exchange.getRequest();
                log.error("[writeJSON][uri({}/{}) 发生异常]", request.getURI(), request.getMethod(), e);
                return bufferFactory.wrap(new byte[0]);
            }
        }));

    }
}
