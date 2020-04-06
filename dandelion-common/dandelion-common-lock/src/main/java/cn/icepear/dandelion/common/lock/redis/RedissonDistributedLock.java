package cn.icepear.dandelion.common.lock.redis;

import cn.icepear.dandelion.common.lock.AbstractDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author rim-wood
 * @description Redisson 分布式锁
 * @date Created on 2020/4/6.
 */
@Slf4j
@Component
public class RedissonDistributedLock extends AbstractDistributedLock {
    @Autowired
    private RedissonClient redissonClient;


    @Override
    public boolean lock(String key, long expire, int retryTimes, long sleepMillis) {
        RLock lock = redissonClient.getLock(key);
        try {
            boolean result = lock.tryLock(sleepMillis, expire, TimeUnit.SECONDS);
            while((!result) && retryTimes-- > 0){
                log.debug("lock failed, retrying..." + retryTimes);
                Thread.sleep(sleepMillis);
                result = lock(key, expire,retryTimes,sleepMillis);
            }
            return result;
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void releaseLock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }
}
