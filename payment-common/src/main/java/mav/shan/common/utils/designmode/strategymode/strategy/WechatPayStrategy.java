package mav.shan.common.utils.designmode.strategymode.strategy;

import mav.shan.common.utils.designmode.strategymode.PaymentStrategy;

// 具体策略类：微信支付
public class WechatPayStrategy implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("使用微信支付: ¥" + amount);
    }
}
