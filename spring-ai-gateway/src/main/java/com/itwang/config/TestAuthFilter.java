package com.itwang.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.itwang.common.constants.GlobalErrorCodeConstants;
import com.itwang.common.result.CommonResult;
import com.itwang.properties.UrlWhiteListProperties;
import com.itwang.utils.WebFrameworkUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


@Component
@Slf4j
public class TestAuthFilter implements WebFilter {

    @Value("${key}")
    private String key;

    @Resource
    private UrlWhiteListProperties urlWhiteListProperties;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if(CollUtil.isNotEmpty(urlWhiteListProperties.getWhiteList())){
            for(String skip: urlWhiteListProperties.getWhiteList()){
                if(request.getPath().value().contains(skip)){
                    return chain.filter(exchange);
                }
            }
        }
        String token = request.getQueryParams().getFirst("token");
        if(StrUtil.isEmpty(token)){
            token = request.getHeaders().getFirst("token");
        }
        if(StrUtil.isEmpty(token)){
            return WebFrameworkUtils.writeJSON(exchange, CommonResult.error(GlobalErrorCodeConstants.FORBIDDEN));
        }
        log.info("get token is {}", token);
        String o = stringRedisTemplate.opsForValue().get(token);
        JSONObject entries = JSONUtil.parseObj(o);
        Object id = entries.get("id");
        ServerHttpRequest build = exchange.getRequest().mutate().header("id", id.toString()).build();


        return chain.filter(exchange.mutate().request(build).build());
    }
}
