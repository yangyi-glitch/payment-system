package mav.shan.common.utils.designmode.adaptermode;


public enum PayEnum {
    ALI_PAY(1, new AliPay()),
    WX_PAY(2, new WxPay()),
    UNION_PAY(3, new UnionPay()),
    NET_EASY_PAY(4, new NetEasyPay());

    PayEnum(int code, Pay pay) {
        this.code = code;
        this.pay = pay;
    }

    private int code;
    private Pay pay;

    public int getCode() {
        return code;
    }

    public Pay getPay() {
        return pay;
    }

    public static Pay getPayByCode(int code) {
        for (PayEnum payEnum : PayEnum.values()) {
            if (payEnum.getCode() == code) {
                return payEnum.getPay();
            }
        }
        return null;
    }

}
