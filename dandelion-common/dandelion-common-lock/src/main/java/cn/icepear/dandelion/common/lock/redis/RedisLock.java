package cn.icepear.dandelion.common.lock.redis;

import java.lang.annotation.*;

/**
 * @author rim-wood
 * @description redis 锁 注解
 * @date Created on 2018/12/9.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RedisLock {
    /**
     * 锁的名称
     */
    String name() default "";

    /**
     * 锁的自定义key，联合name组合成redis的key
     */
    String[] keys() default {};

    /**
     * 持锁时间,单位毫秒
     */
    long keepMills() default 30000;

    /**
     * 当获取失败时候动作
     */
    LockFailAction action() default LockFailAction.CONTINUE;

    public enum LockFailAction{
        /**
         * 放弃
         */
        GIVEUP,
        /**
         * 继续
         */
        CONTINUE;
    }

    /**
     * 重试的间隔时间,设置GIVEUP忽略此项
     */
    long sleepMills() default 200;

    /**
     * 重试次数
     */
    int retryTimes() default 5;
}
