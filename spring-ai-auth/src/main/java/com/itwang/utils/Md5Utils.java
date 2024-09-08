package com.itwang.utils;

import cn.hutool.crypto.digest.MD5;
import org.springframework.stereotype.Component;

@Component
public class Md5Utils {

    private static MD5 md5 = new MD5();

    public String md5String(String token){
        return md5.digestHex(token);
    }
}
