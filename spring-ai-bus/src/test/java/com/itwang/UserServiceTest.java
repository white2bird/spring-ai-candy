package com.itwang;

import com.itwang.globalInterceptor.aspect.redis.RedisLock;
import org.springframework.stereotype.Component;

/**
 * @author yiming@micous.com
 * @date 2024/9/23 15:44
 */
@Component
public class UserServiceTest {

    @RedisLock(value = "#userTest.id", expire = 3L, useWatchDog = true)
    public void test(UserTest userTest) {
        System.out.println("get lock------------");
        try {
            Thread.sleep(10000L);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
