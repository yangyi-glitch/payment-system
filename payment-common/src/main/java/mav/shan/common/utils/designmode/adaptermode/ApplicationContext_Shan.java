package mav.shan.common.utils.designmode.adaptermode;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
@Component
public class ApplicationContext_Shan {

    @Autowired
    private ApplicationContext applicationContext;
    Collection<Pay> values = new ArrayList<>();

    public ApplicationContext_Shan() {
        Map<String, Pay> beansOfType = applicationContext.getBeansOfType(Pay.class);
        values = beansOfType.values();
    }

    public void pay(Object o) {
        for (Pay pay : values) {
            if (pay.support(o)){
                pay.pay(100);
            }
        }
    }

}
