package mav.shan.common.utils.dynamicagents.cglibproxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("前置增强处理");
        // 调用目标方法
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("后置增强处理");

        return result;
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        // 设置父类
        enhancer.setSuperclass(SingleServiceImpl.class);
        // 设置回调
        enhancer.setCallback(new MyMethodInterceptor());
        SingleServiceImpl singleService = (SingleServiceImpl) enhancer.create();
        singleService.start();
    }
}
