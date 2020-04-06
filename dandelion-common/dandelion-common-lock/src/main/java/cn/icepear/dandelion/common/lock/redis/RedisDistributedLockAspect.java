package cn.icepear.dandelion.common.lock.redis;

import cn.icepear.dandelion.common.lock.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

/**
 * @author rim-wood
 * @description redis 分布式锁 aop 实现
 * @date Created on 2018/12/9.
 */
@Aspect
@Component
@ConditionalOnBean(name = "redisDistributedLock")
@Slf4j
public class RedisDistributedLockAspect {

    public static final String LOCK_NAME_PREFIX = "lock:";

    @Autowired
    private DistributedLock redissonDistributedLock;

    @Autowired
    private BusinessKeyProvider businessKeyProvider;

    @Pointcut("@annotation(cn.icepear.dandelion.common.lock.redis.RedisLock)")
    private void lockPoint(){

    }

    @Around("lockPoint()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RedisLock redisLock = signature.getMethod().getAnnotation(RedisLock.class);

        String businessKeyName=businessKeyProvider.getKeyName(joinPoint,redisLock);

        String key = LOCK_NAME_PREFIX+getName(redisLock.name(), signature)+businessKeyName;

        int retryTimes = redisLock.action().equals(RedisLock.LockFailAction.CONTINUE) ? redisLock.retryTimes() : 0;
        boolean lock = redissonDistributedLock.lock(key, redisLock.keepMills(), retryTimes, redisLock.sleepMills());
        if(!lock) {
            log.debug("get lock failed : " + key);
            return null;
        }

        //得到锁,执行方法，释放锁
        log.debug("get lock success : " + key);
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            log.error("execute locked method occured an exception", e);
        } finally {
            redissonDistributedLock.releaseLock(key);
            log.debug("release lock : " + key + " success");
        }
        return null;
    }

    private String getName(String annotationName, MethodSignature signature) {
        if (annotationName.isEmpty()) {
            return String.format("%s.%s", signature.getDeclaringTypeName(), signature.getMethod().getName());
        } else {
            return annotationName;
        }
    }
}
