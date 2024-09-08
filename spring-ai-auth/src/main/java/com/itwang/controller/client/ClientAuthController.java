package com.itwang.controller.client;


import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.itwang.common.result.CommonResult;
import com.itwang.request.LoginRequest;
import com.itwang.utils.Md5Utils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ClientAuthController {

    @Value("${key}")
    private String key;

    @Resource
    private Md5Utils md5Utils;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/login")
    public CommonResult<?> login(@RequestBody LoginRequest loginRequest){
        if(!loginRequest.getUserName().equals("wang") && loginRequest.getPassword().equals("123")){
            throw new RuntimeException("用户名或者账号错误");
        }
        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("username", loginRequest.getUserName());
        infoMap.put("id", 1);
        infoMap.put("creatTime", System.currentTimeMillis());
        String token = JWTUtil.createToken(infoMap, key.getBytes(StandardCharsets.UTF_8));
        String s = md5Utils.md5String(token).strip();
        redisTemplate.opsForValue().set(s, JSONUtil.toJsonStr(infoMap));
        return CommonResult.success(s);
    }


    public static void main(String[] args) {
        HashMap<String, Object> infoMap = new HashMap<>();
        infoMap.put("name", "wang");
        infoMap.put("id", 1);
        infoMap.put("createTime", System.currentTimeMillis());
        String token = JWTUtil.createToken(infoMap, "123456".getBytes(StandardCharsets.UTF_8));
        System.out.println(token);
        JWT jwt = JWTUtil.parseToken(token);
        System.out.println(JWTUtil.verify(token, "123456".getBytes(StandardCharsets.UTF_8)));
        String s = new MD5().digestHex(token);
        System.out.println(s);
        JSONObject payloads = jwt.getPayloads();
        System.out.println(payloads);
    }
}
