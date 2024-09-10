package com.itwang.globalInterceptor;

import com.itwang.common.result.CommonResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class GlobalResponseBodyHandler implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        if(returnType.getMethod() == null){
            return false;
        }
        // 只拦截结果为CommonResult的返回值
        return returnType.getMethod().getReturnType() == CommonResult.class;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
//        WebFrameworkUtils.setCommonResult(((ServletServerHttpRequest) request).getServletRequest(), (CommonResult<?>) body);
        return body;
    }
}
