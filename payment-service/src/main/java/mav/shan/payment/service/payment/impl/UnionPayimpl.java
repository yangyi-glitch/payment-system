package mav.shan.payment.service.payment.impl;

import mav.shan.payment.service.payment.PaymentStrategy;

public class UnionPayimpl implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("使用银联支付: ¥" + amount);
    }
}
