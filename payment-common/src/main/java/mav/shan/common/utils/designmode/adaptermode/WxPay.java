package mav.shan.common.utils.designmode.adaptermode;

public class WxPay implements Pay {
    @Override
    public boolean support(Object o) {
        if (o instanceof WxPay){
            return true;
        }
        return false;
    }
    @Override
    public void pay(Integer money) {
        System.out.println("微信支付====>" + money);
    }
}
