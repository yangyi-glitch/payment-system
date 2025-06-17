package mav.shan.common.utils.designmode.factorymode.methodfactory.method.impl;

import mav.shan.common.utils.designmode.factorymode.methodfactory.method.Payment;

public class WechatPay implements Payment {
    @Override
    public void pay(int amount) {
        System.out.println("微信支付 $" + amount);
    }
}
