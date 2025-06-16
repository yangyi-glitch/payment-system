package mav.shan.common.aop;

import mav.shan.common.annotation.Idempotent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static mav.shan.common.utils.ResultUtils.error;
import static mav.shan.common.utils.ThreadLocalUtils.getLoginUser;
import static mav.shan.common.utils.ThreadPoolUtils.pool;

@Aspect
@Component
@Slf4j
public class IdempotentAspect {

    private static Map<Long, Integer> map = new HashMap<>();

    @Around(value = "@annotation(idempotent)")
    public Object aroundPointCut(ProceedingJoinPoint joinPoint, Idempotent idempotent) throws Throwable {
        log.info("进入了幂等切面~");
        Long userId = getLoginUser().getUserId();
        Integer lock = map.get(getLoginUser().getUserId());
        if (lock != null) {
            log.info("请勿重复提交~");
            return error("请勿重复提交");
        }
        map.put(userId, 1);
        int timeout = idempotent.timeout();
        // 锁定失败
        pool.execute(() -> {
            unlock(userId, timeout);
        });
        // 2. 执行逻辑
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    public static void unlock(Long userId, int timeout) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        map.remove(userId);
        log.info("解锁成功~userId[{}]",userId);
    }
}
