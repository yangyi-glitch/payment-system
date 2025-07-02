package mav.shan.common.utils.designmode.adaptermode;

public class NetEasyPay implements Pay {
    @Override
    public boolean support(Object o) {
        if (o instanceof NetEasyPay){
            return true;
        }
        return false;
    }

    @Override
    public void pay(Integer money) {
        System.out.println("网易支付====>" + money);
    }
}
