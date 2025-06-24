package mav.shan.payment.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

@Component
public class DemoJobHandler {

    @XxlJob("demoJobHandler")
    public void demoJobHandler() throws Exception {
        System.out.println("XXL-JOB, Hello World.");
    }
}
