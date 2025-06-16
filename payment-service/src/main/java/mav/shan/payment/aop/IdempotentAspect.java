package mav.shan.payment.aop;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import mav.shan.payment.annotation.Idempotent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static mav.shan.common.utils.ResultUtils.error;
import static mav.shan.common.utils.ThreadLocalUtils.getLoginUser;

@Aspect
@Component
@Slf4j
@Service
public class IdempotentAspect {

    private static Map<Long, Integer> map = new HashMap<>();

    @Around(value = "@annotation(idempotent)")
    public Object aroundPointCut(ProceedingJoinPoint joinPoint, Idempotent idempotent) throws Throwable {
        log.info("进入了幂等切面~");
        Long userId = getLoginUser().getUserId();
        Integer lock = map.get(getLoginUser().getUserId());
        if (lock != null) {
            log.info(idempotent.message());
            return error(idempotent.message());
        }
        map.put(userId, 1);
        getSelf().unlock(userId, idempotent.timeout());
        // 2. 执行逻辑
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    @Async("poolExecutor")
    public void unlock(Long userId, int timeout) {
        try {
            Thread.sleep(timeout * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        map.remove(userId);
        log.info("解锁成功~userId[{}]", userId);
    }

    private IdempotentAspect getSelf() {
        return SpringUtil.getBean(getClass());
    }
}
