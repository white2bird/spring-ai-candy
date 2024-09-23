package com.itwang;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author yiming@micous.com
 * @date 2024/9/23 15:28
 */
@SpringBootTest
@EnableAspectJAutoProxy(exposeProxy = true)
public class RedisLockTest {




    @Resource
    private UserServiceTest userServiceTest;

    @Test
    public void test2() throws Exception{
        for(int i = 0; i < 10; i++){
            try{
                new Thread(() -> {
                    try{
                        userServiceTest.test(new UserTest(13));
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }).start();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        Thread.sleep(40000L);
    }
}
