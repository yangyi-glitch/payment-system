package mav.shan.payment.start_redis.redis;

import java.util.concurrent.TimeUnit;

public interface RedisService {

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * 带过期时间的缓存
     *
     * @param key
     * @param value
     * @param timeout
     * @param unit
     */
    void set(String key, String value, long timeout, TimeUnit unit);

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 删除缓存
     *
     * @param key
     */
    void delete(String key);
}
