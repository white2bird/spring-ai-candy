package com.itwang.customServiceBean;

import cn.dev33.satoken.stp.StpInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yiming@micous.com
 * @date 2024/9/5 17:53
 */
@Component
@Slf4j
public class StpInterfaceImpl implements StpInterface {
    @Override
    public List<String> getPermissionList(Object o, String s) {
        log.info("getPermissionList -- {}, {}", o, s);
        return List.of("all", "user", "admin");
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        return List.of();
    }
}
