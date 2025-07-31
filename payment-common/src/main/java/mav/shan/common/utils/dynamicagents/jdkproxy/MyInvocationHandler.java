package mav.shan.common.utils.dynamicagents.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyInvocationHandler implements InvocationHandler {
    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 前置处理
        System.out.println("方法调用前: " + method.getName());

        // 调用目标对象的方法
        Object result = method.invoke(target, args);

        // 后置处理
        System.out.println("方法调用后: " + method.getName());
        return result;
    }

    public static void main(String[] args) {
        StartService startService = new StartServiceImpl();
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(startService);
        StartService startService1 = (StartService) Proxy.newProxyInstance(
                StartService.class.getClassLoader(),
                new Class[]{StartService.class},
                myInvocationHandler);
        startService1.start();
    }
}
