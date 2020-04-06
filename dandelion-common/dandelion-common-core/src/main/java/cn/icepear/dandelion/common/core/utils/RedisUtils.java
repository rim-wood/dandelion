package cn.icepear.dandelion.common.core.utils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author Rimwood
 * @email rimwood@haeyoo.com
 * @date 2017-03-17 21:12
 */
@Slf4j
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ValueOperations<String, String> valueOperations;
    @Autowired
    private HashOperations<String, String, Object> hashOperations;
    @Autowired
    private ListOperations<String, Object> listOperations;
    @Autowired
    private SetOperations<String, Object> setOperations;
    @Autowired
    private ZSetOperations<String, Object> zSetOperations;

    /**  默认过期时长，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1;

    public Set keys(String pattern){
        return redisTemplate.keys(pattern);
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 0) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
    /**
     * 根据通配符删除
     * @param pattern
     * @return
     */
    public Long deleteAll(String pattern){
        return redisTemplate.delete(keys(pattern));
    }



    public int size(String pattern){
        return keys(pattern).size();
    }
    //-------------------------------Redis String 操作Start------------------------------------
    /**
     * String插入，使用默认的过期时间24小时
     * @param key
     * @param value
     */
    public void set(String key, Object value){
        set(key, value, DEFAULT_EXPIRE);
    }

    /**
     * String插入，使用指定的过期时间
     * @param key
     * @param value
     * @param expire
     */
    public void set(String key, Object value, long expire){
        valueOperations.set(key, toJson(value));
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取对象，并重新设置过期时间
     * @param key
     * @param clazz
     * @param expire
     * @param <T>
     * @return
     */
    public <T> T get(String key, Class<T> clazz, long expire) {
        Object value = valueOperations.get(key);
        //如果值存在并且有过期时间则重新设置过期时间
        if(value!=null&&expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value.toString(), clazz);
    }

    /**
     * 获取未设置过期时间的对象
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    /**
     * String获取，并重新设置过期时间
     * @param key
     * @param expire
     * @return
     */
    public Object get(String key, long expire) {
        Object value = valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    /**
     * 获取未设置过期时间的String
     * @param key
     * @return
     */
    public String get(String key) {
        return toJson(get(key, NOT_EXPIRE));
    }

    /**
     * 删除
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    //-----------------------------Redis String 操作End----------------------------------

    //-----------------------------Redis Hash 操作Start----------------------------------
    /**
     * hash表插入
     * @param key
     * @param field
     * @param value
     */
    public void hput(String key, String field, Object value){
        hashOperations.put(key,field,value);
    }

    /**
     * 域下面插入整个hash表
     * @param key
     * @param m
     */
    public void hputAll(String key, HashMap m){
        hashOperations.putAll(key,m);
    }

    /**
     * hash表取值
     * @param key
     * @param field
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T hget(String key,String field,Class<T> clazz){
        Object value = hashOperations.get(key,field);
        return value == null ? null : fromJson(toJson(value), clazz);
    }

    /**
     * 判断hash表中是否存在field键
     * @param key
     * @param field
     * @return
     */
    public Boolean hhasKey(String key,String field){
        return hashOperations.hasKey(key,field);
    }

    /**
     * 为散列添加一个key-value键值对。如果存在则不添加不覆盖。返回false
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Boolean hputIfAbsent(String key,String field,Object value){
        return hashOperations.putIfAbsent(key,field,value);
    }

    /**
     * 获取hash 域里所有的键
     * @param key
     * @return
     */
    public Set<String> hkeys(String key){
        return hashOperations.keys(key);
    }

    /**
     * 删除hash表的值
     * @param key
     * @param filed
     * @return
     */
    public Long hdelete(String key,Object... filed){
        if(filed.length>0){
            return hashOperations.delete(key,filed);
        }
        return hashOperations.delete(key);
    }

    //-----------------------------Redis Hash 操作End----------------------------------

    //-----------------------------Redis List 操作Start----------------------------------
    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0到 -1代表所有值
     * @return
     */
    public List getList(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @return
     */
    public List getList(String key) {
        try {
            return redisTemplate.opsForList().range(key, 0, -1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 首个元素，依次类推；index<0时，-1，最后一个元素，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean setList(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, toJson(value));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean setList(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, toJson(value));
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean setList(String key, List value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, toJson(value));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean setList(String key, List value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, toJson(value));
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, toJson(value));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, toJson(value));
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    //-----------------------------Redis List 操作End----------------------------------

    //-----------------------------Redis Set 操作Start----------------------------------

    /**
     *  向Set添加N条记录，返回添加的value的个数
     * @param key
     * @param value
     * @return
     */
    public Long sadd(String key,Object... value){
        return setOperations.add(key,value);
    }

    /**
     * 删除给定key中元素个数
     * @param key
     * @param value
     * @return
     */
    public long sdelete(String key,Object... value) {
        return setOperations.remove(key,value);
    }

    /**
     * 随机删除集合中的一个值，并返回。
     * @param key
     * @return
     */
    public Object spop(String key){
        return setOperations.pop(key);
    }

    /**
     * 把源集合中的一个元素移动到目标集合。成功返回true.
     * @param key
     * @param value
     * @param destKey
     * @return
     */
    public Boolean smove(String key, Object value, String destKey){
        return setOperations.move(key,value,destKey);
    }

    /**
     * 返回集合中的元素个数
     * @param key
     * @return
     */
    public Long ssize(String key){
        return setOperations.size(key);
    }
    //-----------------------------Redis Set 操作End----------------------------------

    //-----------------------------Redis SortedSet 操作Start----------------------------------
    //-----------------------------Redis SortedSet 操作End----------------------------------

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object){
        if(object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String){
            return String.valueOf(object);
        }
        String jsonString = null;
        try {
            jsonString =  new Gson().toJson(object);
        } catch (JsonIOException e) {
            // 打印堆栈信息
            log.error("json对象转换字符串异常 ex={}", ThrowableUtil.getStackTrace(e));
        }
        return jsonString;
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz){
        try {
            return new Gson().fromJson(json, clazz);
        } catch (JsonIOException e) {
            // 打印堆栈信息
            log.error("json字符串转换对象异常 ex={}", ThrowableUtil.getStackTrace(e));
        }
        return null;
    }
}
