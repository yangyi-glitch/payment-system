package mav.shan.payment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("mav.shan.payment.mapper")
@ComponentScan(basePackages = {"mav.shan.payment", "mav.shan.common.aop"})
public class PaymentAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentAdminApplication.class, args);
    }

}
