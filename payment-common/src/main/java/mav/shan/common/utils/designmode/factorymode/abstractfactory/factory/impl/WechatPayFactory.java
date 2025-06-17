package mav.shan.common.utils.designmode.factorymode.abstractfactory.factory.impl;

import mav.shan.common.utils.designmode.factorymode.abstractfactory.factory.PaymentFactory;
import mav.shan.common.utils.designmode.factorymode.abstractfactory.method.Payment;
import mav.shan.common.utils.designmode.factorymode.abstractfactory.method.Report;
import mav.shan.common.utils.designmode.factorymode.abstractfactory.method.impl.WechatPay;
import mav.shan.common.utils.designmode.factorymode.abstractfactory.method.impl.WechatReport;

public class WechatPayFactory implements PaymentFactory {
    @Override
    public Payment createPayment() {
        return new WechatPay();
    }

    @Override
    public Report createReport() {
        return new WechatReport();
    }
}
