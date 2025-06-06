package mav.shan.payment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@MapperScan("mav.shan.payment.mapper")
public class PaymentFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentFrontApplication.class, args);
    }

}
