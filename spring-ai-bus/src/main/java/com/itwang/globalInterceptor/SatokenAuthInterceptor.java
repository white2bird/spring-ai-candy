package com.itwang.globalInterceptor;

import cn.dev33.satoken.stp.StpInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * todo 权限处理
 * @author yiming@micous.com
 * @date 2024/9/9 11:35
 */
@Slf4j
@Component
public class SatokenAuthInterceptor implements StpInterface {

    @Override
    public List<String> getPermissionList(Object o, String s) {
        log.info("o {} s {}", o, s);
        return List.of("amdin", "user");
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        log.info("o {} s {}", o, s);
        return List.of("user");
    }
}
