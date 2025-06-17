package mav.shan.common.utils.designmode.factorymode.abstractfactory.method.impl;

import mav.shan.common.utils.designmode.factorymode.abstractfactory.method.Report;

public class AliReport implements Report {
    @Override
    public void report() {
        System.out.println("支付宝报表");
    }
}
