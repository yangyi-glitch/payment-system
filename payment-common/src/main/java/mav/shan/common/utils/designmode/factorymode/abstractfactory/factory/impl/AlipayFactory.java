package mav.shan.common.utils.designmode.factorymode.abstractfactory.factory.impl;

import mav.shan.common.utils.designmode.factorymode.abstractfactory.factory.PaymentFactory;
import mav.shan.common.utils.designmode.factorymode.abstractfactory.method.Payment;
import mav.shan.common.utils.designmode.factorymode.abstractfactory.method.Report;
import mav.shan.common.utils.designmode.factorymode.abstractfactory.method.impl.AliReport;
import mav.shan.common.utils.designmode.factorymode.abstractfactory.method.impl.Alipay;

public class AlipayFactory implements PaymentFactory {
    @Override
    public Payment createPayment() {
        return new Alipay();
    }

    @Override
    public Report createReport() {
        return new AliReport();
    }
}
