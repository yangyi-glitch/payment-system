package mav.shan.payment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("mav.shan.payment.mapper")
@EnableAsync
@EnableScheduling
public class PaymentAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentAdminApplication.class, args);
    }

}
