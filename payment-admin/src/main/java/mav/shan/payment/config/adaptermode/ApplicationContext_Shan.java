package mav.shan.payment.config.adaptermode;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Data
@Component
public class ApplicationContext_Shan implements InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;
    Collection<Pay> values;


    public void pay(Object o) {
        Map<String, Pay> beansOfType = applicationContext.getBeansOfType(Pay.class);
        for (Pay pay : beansOfType.values()) {
            if (pay.support(o)) {
                pay.pay(100);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, Pay> beansOfType = applicationContext.getBeansOfType(Pay.class);
        values = beansOfType.values();
    }
}
