package com.itwang.globalInterceptor.aspect.redis;

import cn.hutool.core.util.StrUtil;
import com.itwang.common.utils.RedisLockUtils;
import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 分布式锁
 * @author yiming@micous.com
 * @date 2024/9/23 14:46
 */
@Aspect
@Component
public class RedisLockAspect implements BeanFactoryAware {

    private final String REDIS_LOCK_PREFIX = "redisLock:";
    private BeanFactory beanFactory;
    private final ExpressionParser parser = new SpelExpressionParser();
    private final ParameterNameDiscoverer pnd= new DefaultParameterNameDiscoverer();

    @Resource
    private RedisLockUtils redisLockUtils;

    @Pointcut("@annotation(com.itwang.globalInterceptor.aspect.redis.RedisLock)")
    public void pointcut(){

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedisLock annotation = method.getAnnotation(RedisLock.class);
        boolean watchDog = annotation.useWatchDog();

        if(StrUtil.isEmpty(annotation.value())){
            throw new RuntimeException("redisLock注解value不能为空");
        }

        MethodBasedEvaluationContext context =
                new MethodBasedEvaluationContext(null, method, args, pnd);
        context.setBeanResolver(new BeanFactoryResolver(beanFactory));
        Object value = parser.parseExpression(annotation.value()).getValue(context);
        String lockValue = Thread.currentThread().getName();
        Boolean lock = watchDog ? redisLockUtils.tryLockWithWatchDog(REDIS_LOCK_PREFIX + String.valueOf(value), lockValue, annotation.expire()) : redisLockUtils.tryLock(REDIS_LOCK_PREFIX + String.valueOf(value), lockValue, annotation.expire());
        if(!lock){
            throw new RuntimeException(Thread.currentThread().getName() + "获取锁失败");
        }
        try{
            return joinPoint.proceed();
        } finally {
            redisLockUtils.unlock(REDIS_LOCK_PREFIX + String.valueOf(value), lockValue);
        }

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
