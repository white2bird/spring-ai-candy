package com.itwang.common.utils;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author yiming@micous.com
 * @date 2024/9/23 13:38
 */
@Slf4j
@Component
public class RedisLockUtils {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private ConcurrentHashMap<String, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap<>();

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public Boolean tryLockWithWatchDog(String key, String value, long expire) {
        Boolean locked = tryLock(key, value, expire);
        if (locked) {
            startWatchDog(key, value, expire);
        }
        return locked;
    }

    public Boolean tryLock(String key, String value, long expire) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, expire, TimeUnit.SECONDS);
    }

    private void startWatchDog(String key, String value, long expire) {
        ScheduledFuture<?> finalScheduledFuture = executorService.scheduleAtFixedRate(() -> {
            log.info("延期任务执行--");
            String redisValue = redisTemplate.opsForValue().get(key);
            if (value.equals(redisValue)) {
                redisTemplate.expire(key, expire, TimeUnit.SECONDS);
            } else {
                log.info("锁已过期，释放锁");
                ScheduledFuture<?> scheduledFuture = scheduledFutures.get(key);
                if (scheduledFuture != null) {
                    scheduledFuture.cancel(false);
                    scheduledFutures.remove(key);
                }
            }
        }, expire / 3, expire / 3, TimeUnit.SECONDS);
        scheduledFutures.put(key, finalScheduledFuture);

    }

    public boolean unlock(String key, String value) {
        String redisValue = redisTemplate.opsForValue().get(key);
        if (value.equals(redisValue)) {
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }
}
