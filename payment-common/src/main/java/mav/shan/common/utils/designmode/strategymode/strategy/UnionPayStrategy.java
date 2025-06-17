package mav.shan.common.utils.designmode.strategymode.strategy;

import mav.shan.common.utils.designmode.strategymode.PaymentStrategy;

// 具体策略类：银联支付
public class UnionPayStrategy implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("使用银联支付: ¥" + amount);
    }
}
