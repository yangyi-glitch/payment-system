package mav.shan.payment.service.payment;

import mav.shan.common.entity.UserDTO;
import mav.shan.payment.service.payment.impl.AlipayImpl;
import mav.shan.payment.service.user.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class PaymentSerivceImpl {
    private PaymentStrategy strategy;

    @Resource
    private UserService userService;

    @PostConstruct
    public void init() {
        // 初始化逻辑
        List<UserDTO> list = userService.list();
        System.out.println("PaymentSerivceImpl=============================>init");
        System.out.println(list);
        strategy = new AlipayImpl();
    }

    public void executePayment(int amount) {
        strategy.pay(amount);
    }
}
