package mav.shan.payment.config.adaptermode;

import org.springframework.stereotype.Component;

@Component
public class UnionPay implements Pay {
    @Override
    public boolean support(Object o) {
        if (o instanceof UnionPay){
            return true;
        }
        return false;
    }
    @Override
    public void pay(Integer money) {
        System.out.println("银联支付====>" + money);
    }
}
