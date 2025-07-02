package mav.shan.common.utils.designmode.adaptermode;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApplicationContext_Shan {
    List<Pay> payList = new ArrayList<>();

    public ApplicationContext_Shan() {
        AliPay aliPay = new AliPay();
        WxPay wxPay = new WxPay();
        UnionPay unionPay = new UnionPay();
        NetEasyPay netEasyPay = new NetEasyPay();
        payList.add(aliPay);
        payList.add(wxPay);
        payList.add(unionPay);
        payList.add(netEasyPay);
    }

    public void pay(Object o) {
        for (Pay pay : payList) {
            if (pay.support(o)){
                pay.pay(100);
            }
        }
    }

}
