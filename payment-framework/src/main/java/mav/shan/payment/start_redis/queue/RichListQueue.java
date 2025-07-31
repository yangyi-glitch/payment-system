package mav.shan.payment.start_redis.queue;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class RichListQueue {
    private static final String QUEUE_KEY = "queue:rich_list";
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void produceQueue(String value, long execud) {
        stringRedisTemplate.opsForZSet().add(QUEUE_KEY, value, execud);
    }

    public Set<String> consumeQueue() {
        return stringRedisTemplate.opsForZSet().rangeByScore(QUEUE_KEY, 0, 5);
    }

    public void removeQueue(String value) {
        stringRedisTemplate.opsForZSet().remove(QUEUE_KEY, value);
    }
}
