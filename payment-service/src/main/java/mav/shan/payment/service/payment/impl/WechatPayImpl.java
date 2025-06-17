package mav.shan.payment.service.payment.impl;

import mav.shan.payment.service.payment.PaymentStrategy;

public class WechatPayImpl implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("使用微信支付: ¥" + amount);
    }
}
