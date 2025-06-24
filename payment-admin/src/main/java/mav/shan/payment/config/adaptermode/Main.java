package mav.shan.payment.config.adaptermode;

import static mav.shan.common.utils.designmode.adaptermode.PayEnum.getPayByCode;

/**
 * @Description: 适配器模式
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext_Shan applicationContext_shan = new ApplicationContext_Shan();
        applicationContext_shan.pay(getPayByCode(4));
    }
}
