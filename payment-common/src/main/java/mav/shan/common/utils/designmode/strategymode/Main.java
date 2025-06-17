package mav.shan.common.utils.designmode.strategymode;

import mav.shan.common.utils.designmode.strategymode.strategy.AlipayStrategy;

public class Main {
    public static void main(String[] args) {
        PaymentContext context = new PaymentContext();
        context.setPaymentStrategy(new AlipayStrategy());
        context.executePayment(100);
    }
}
