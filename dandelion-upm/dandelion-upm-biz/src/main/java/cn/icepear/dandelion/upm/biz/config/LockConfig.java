package cn.icepear.dandelion.upm.biz.config;

import cn.icepear.dandelion.common.lock.DistributedLock;
import cn.icepear.dandelion.common.lock.redis.RedissonDistributedLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author rim-wood
 * @description 分布式锁配置
 * @date Created on 2018/12/9.
 */
@Configuration
public class LockConfig {
    @Autowired
    private RedissonClient redissonClient;

    @Bean(name = "redisDistributedLock")
    public DistributedLock redisDistributedLock(){
        return new RedissonDistributedLock(redissonClient);
    }

}
