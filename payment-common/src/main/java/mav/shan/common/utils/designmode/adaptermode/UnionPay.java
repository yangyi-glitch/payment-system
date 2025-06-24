package mav.shan.common.utils.designmode.adaptermode;

public class UnionPay implements Pay {
    @Override
    public boolean support(Object o) {
        if (o instanceof UnionPay){
            return true;
        }
        return false;
    }
    @Override
    public void pay(Integer money) {
        System.out.println("银联支付====>" + money);
    }
}
