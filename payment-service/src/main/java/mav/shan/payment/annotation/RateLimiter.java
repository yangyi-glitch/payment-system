package mav.shan.payment.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 限流注解
 *
 * @author 芋道源码
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimiter {

    /**
     * 限流的时间，默认为 1 秒
     */
    long time() default 1L;

    /**
     * 限流的时间，默认为 1 秒
     */
    int count() default 10;

    /**
     * 时间单位，默认为 SECONDS 秒
     */
    TimeUnit timeUnit() default TimeUnit.MINUTES;

    /**
     * 提示信息，请求过快的提示
     */
    String message() default "请求太多了，滚蛋~";

}
