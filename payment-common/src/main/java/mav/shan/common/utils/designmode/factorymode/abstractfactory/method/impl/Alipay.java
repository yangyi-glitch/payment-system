package mav.shan.common.utils.designmode.factorymode.abstractfactory.method.impl;

import mav.shan.common.utils.designmode.factorymode.abstractfactory.method.Payment;

public class Alipay implements Payment {
    @Override
    public void pay(int amount) {
        System.out.println("支付宝支付 $" + amount);
    }
}
