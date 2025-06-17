package mav.shan.common.utils.designmode.strategymode.strategy;

import mav.shan.common.utils.designmode.strategymode.PaymentStrategy;

// 具体策略类：支付宝支付
public class AlipayStrategy implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("使用支付宝支付: ¥" + amount);
    }
}
