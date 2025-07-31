package mav.shan.payment.start_redis.queue;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class TimeLapseQueue {
    private static final String QUEUE_KEY = "queue:time_lapse";
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void produceQueue(String value, long execud) {
        stringRedisTemplate.opsForZSet().add(QUEUE_KEY, value, execud);
    }

    public Set<String> consumeQueue(long timeout) {
        return stringRedisTemplate.opsForZSet().rangeByScore(QUEUE_KEY, 0, timeout);
    }

    public void removeQueue(String value) {
        stringRedisTemplate.opsForZSet().remove(QUEUE_KEY, value);
    }
}
