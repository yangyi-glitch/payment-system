package mav.shan.payment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("mav.shan.payment.mapper")
@EnableAsync
public class PaymentAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentAdminApplication.class, args);
    }

}
