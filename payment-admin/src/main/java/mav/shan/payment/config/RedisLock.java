package mav.shan.payment.config;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;

@Component
public class RedisLock {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 尝试获取分布式锁
     *
     * @param key 锁的值（一般用唯一标识，如 UUID）
     * @return 是否获取成功
     */
    public Long tryLock(Object key) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(
                "if redis.call('hexists', KEYS[1], ARGV[1]) == 1 then " +
                        "redis.call('hincrby', KEYS[1], ARGV[1], 1); " +
                        "redis.call('expire', KEYS[1], ARGV[2]); " +
                        "return 1; " +
                        "else " +
                        "if redis.call('exists', KEYS[1]) == 0 then " +
                        "redis.call('hset', KEYS[1], ARGV[1], 1); " +
                        "redis.call('expire', KEYS[1], ARGV[2]); " +
                        "return 1; " +
                        "else " +
                        "return 0; " +
                        "end; " +
                        "end;");
        script.setResultType(Long.class);
        // 使用 hash 结构记录重入次数
        Long count = stringRedisTemplate.execute(script, Arrays.asList(String.valueOf(key.hashCode())), "1", String.valueOf(100));
        return count;
    }

    /**
     * 释放分布式锁
     *
     * @param value 锁的值
     */
    public void releaseLock(Object value) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(
                "local count = redis.call('hget', KEYS[1], ARGV[1]); " +
                        "if count == false then " +
                        "return 0; " +
                        "end; " +
                        "if tonumber(count) > 1 then " +
                        "redis.call('hincrby', KEYS[1], ARGV[1], -1); " +
                        "redis.call('expire', KEYS[1], ARGV[2]); " +
                        "return 1; " +
                        "else " +
                        "redis.call('hdel', KEYS[1], ARGV[1]); " +
                        "return 1; " +
                        "end;");
        script.setResultType(Long.class);
        stringRedisTemplate.execute(script, Arrays.asList("2"), String.valueOf(value.hashCode()));
    }
}
