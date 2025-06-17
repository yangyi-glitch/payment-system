package mav.shan.common.utils.designmode.factorymode.abstractfactory.factory;

import mav.shan.common.utils.designmode.factorymode.abstractfactory.method.Payment;
import mav.shan.common.utils.designmode.factorymode.abstractfactory.method.Report;

public interface PaymentFactory {
    Payment createPayment();

    Report createReport();
}
