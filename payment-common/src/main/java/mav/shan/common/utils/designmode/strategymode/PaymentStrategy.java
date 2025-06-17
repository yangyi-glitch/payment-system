package mav.shan.common.utils.designmode.strategymode;

public interface PaymentStrategy {
    void pay(int amount);

    // 上下文类
    class PaymentContext {
        private PaymentStrategy strategy;

        public void setPaymentStrategy(PaymentStrategy strategy) {
            this.strategy = strategy;
        }

        public void executePayment(int amount) {
            strategy.pay(amount);
        }
    }
}
