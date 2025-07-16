package mav.shan.payment.config.adaptermode;

public interface Pay {
    boolean support(Object o);
    void pay(Integer money);
}
