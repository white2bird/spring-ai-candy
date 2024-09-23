package com.itwang.globalInterceptor.aspect.redis;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author yiming@micous.com
 * @date 2024/9/23 14:41
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(java.lang.annotation.ElementType.METHOD)
@Documented
public @interface RedisLock {
    String value();

    long expire() default 10L;

    boolean useWatchDog() default false;
}
