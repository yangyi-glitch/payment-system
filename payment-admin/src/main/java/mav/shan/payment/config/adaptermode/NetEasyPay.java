package mav.shan.payment.config.adaptermode;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class NetEasyPay implements Pay {
    @Override
    public boolean support(Object o) {
        if (o instanceof NetEasyPay){
            return true;
        }
        return false;
    }

    @Override
    public void pay(Integer money) {
        System.out.println("网易支付====>" + money);
    }
}
