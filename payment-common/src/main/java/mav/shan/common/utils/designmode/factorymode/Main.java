package mav.shan.common.utils.designmode.factorymode;

import mav.shan.common.utils.designmode.factorymode.methodfactory.factory.PaymentFactory;
import mav.shan.common.utils.designmode.factorymode.methodfactory.factory.impl.AlipayFactory;
import mav.shan.common.utils.designmode.factorymode.methodfactory.factory.impl.WechatPayFactory;
import mav.shan.common.utils.designmode.factorymode.methodfactory.method.Payment;

/**
 * @Description:工厂模式
 */
public class Main {
    public static void main(String[] args) {
        PaymentFactory factory = new AlipayFactory();
        Payment payment = factory.createPayment();
        payment.pay(100);

        WechatPayFactory wechatPayFactory = new WechatPayFactory();
        Payment wechatPay = wechatPayFactory.createPayment();
        wechatPay.pay(100);

        FactoryContext factoryContext = new FactoryContext();
        factoryContext.getFactory(AlipayFactory.class,100);
    }
}
