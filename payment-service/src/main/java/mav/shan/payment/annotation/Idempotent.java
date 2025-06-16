package mav.shan.payment.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

    /**
     * 幂等的超时时间，默认为 10 秒
     */
    int timeout() default 10;

    /**
     * 提示信息
     */
    String message() default "重复请求，请稍后重试";
}
