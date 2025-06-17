package mav.shan.common.utils.designmode.factorymode.methodfactory.method.impl;

import mav.shan.common.utils.designmode.factorymode.methodfactory.method.Payment;

public class Alipay implements Payment {
    @Override
    public void pay(int amount) {
        System.out.println("支付宝支付 $" + amount);
    }
}
