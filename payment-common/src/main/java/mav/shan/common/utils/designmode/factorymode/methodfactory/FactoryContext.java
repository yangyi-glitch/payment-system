package mav.shan.common.utils.designmode.factorymode.methodfactory;

import mav.shan.common.utils.designmode.factorymode.methodfactory.factory.PaymentFactory;
import mav.shan.common.utils.designmode.factorymode.methodfactory.factory.impl.AlipayFactory;

import java.util.HashMap;
import java.util.Map;

public class FactoryContext {
    private static Map<Class, PaymentFactory> factory = new HashMap<>();

    public FactoryContext() {
        AlipayFactory alipayFactory = new AlipayFactory();
        Class<?>[] interfaces = AlipayFactory.class.getInterfaces();
        for (Class<?> e : interfaces) {
            if (e.getName().equals(PaymentFactory.class.getName())) {
                factory.put(AlipayFactory.class, alipayFactory);
            }
        }
    }

    public void getFactory(Class<?> clazz, int amount) {
        PaymentFactory paymentFactory = factory.get(clazz);
        if (paymentFactory == null) {
            System.out.println("No factory found for " + clazz.getName());
        } else {
            paymentFactory.createPayment().pay(amount);
        }

    }
}
