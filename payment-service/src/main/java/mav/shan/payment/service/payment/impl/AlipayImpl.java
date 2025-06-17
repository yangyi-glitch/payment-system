package mav.shan.payment.service.payment.impl;

import mav.shan.payment.service.payment.PaymentStrategy;

public class AlipayImpl implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("使用支付宝支付: ¥" + amount);
    }
}
