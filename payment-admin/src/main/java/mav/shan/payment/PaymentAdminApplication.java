package mav.shan.payment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("mav.shan.payment.mapper")
public class PaymentAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentAdminApplication.class, args);
    }

}
