package mav.shan.payment.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import mav.shan.common.pojo.RateLimiterPOJO;
import mav.shan.payment.annotation.RateLimiter;
import mav.shan.payment.start_redis.redis.RedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static mav.shan.common.constants.RedisConstants.RATE_LIMITER_KEY_PREFIX;
import static mav.shan.common.utils.ResultUtils.error;
import static mav.shan.common.utils.ThreadLocalUtils.getLoginUser;

@Aspect
@Component
@Slf4j
public class RateLimiterAspect {

    @Resource
    private RedisService redisService;

    @Around(value = "@annotation(rateLimiter)")
    public Object aroundPointCut(ProceedingJoinPoint joinPoint, RateLimiter rateLimiter) {
        String value = redisService.get(formatKey(getLoginUser().getUserId()));
        RateLimiterPOJO rateLimiterPOJO = new RateLimiterPOJO();
        if (value != null) {
            rateLimiterPOJO = JSON.parseObject(value, RateLimiterPOJO.class);
            //代表限流 一分钟内点击的次数太多了
            if (rateLimiterPOJO.getCount() >= rateLimiter.count()) {
                if (rateLimiterPOJO.getLock() == 0) {
                    rateLimiterPOJO.setLock(1);
                    redisService.delete(formatKey(getLoginUser().getUserId()));
                    redisService.set(formatKey(getLoginUser().getUserId()), JSON.toJSONString(rateLimiterPOJO), rateLimiter.time(), rateLimiter.timeUnit());
                }
                return error(rateLimiter.message());
            }

            if (rateLimiterPOJO.getCount() < rateLimiter.count()) {
                if (getDateFormat(rateLimiterPOJO.getTime()).equals(getDateFormat(LocalDateTime.now()))) {
                    rateLimiterPOJO.setCount(rateLimiterPOJO.getCount() + 1);
                    redisService.set(formatKey(getLoginUser().getUserId()), JSON.toJSONString(rateLimiterPOJO));
                } else {
                    rateLimiterPOJO = new RateLimiterPOJO();
                    rateLimiterPOJO.setCount(1);
                    rateLimiterPOJO.setTime(LocalDateTime.now());
                    redisService.set(formatKey(getLoginUser().getUserId()), JSON.toJSONString(rateLimiterPOJO));
                }
            }
        } else {
            rateLimiterPOJO.setCount(1);
            rateLimiterPOJO.setTime(LocalDateTime.now());
            redisService.set(formatKey(getLoginUser().getUserId()), JSON.toJSONString(rateLimiterPOJO));
        }
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private String formatKey(Long key) {
        return String.format(RATE_LIMITER_KEY_PREFIX, key);
    }

    private static String getDateFormat(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm");
        return localDateTime.format(dateTimeFormatter);
    }
}

