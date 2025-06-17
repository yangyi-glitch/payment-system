package mav.shan.common.utils.designmode.factorymode.methodfactory.factory.impl;

import mav.shan.common.utils.designmode.factorymode.methodfactory.factory.PaymentFactory;
import mav.shan.common.utils.designmode.factorymode.methodfactory.method.Payment;
import mav.shan.common.utils.designmode.factorymode.methodfactory.method.impl.WechatPay;

public class WechatPayFactory implements PaymentFactory {
    @Override
    public Payment createPayment() {
        return new WechatPay();
    }
}
