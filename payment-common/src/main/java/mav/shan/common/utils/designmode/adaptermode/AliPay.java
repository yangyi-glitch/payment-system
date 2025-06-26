package mav.shan.common.utils.designmode.adaptermode;

public class AliPay implements Pay {

    @Override
    public boolean support(Object o) {
        if (o instanceof AliPay){
            return true;
        }
        return false;
    }

    @Override
    public void pay(Integer money) {
        System.out.println("支付宝支付====>" + money);
    }
}
