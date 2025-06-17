package mav.shan.common.utils.designmode.factorymode.abstractfactory.method.impl;

import mav.shan.common.utils.designmode.factorymode.abstractfactory.method.Report;

public class WechatReport implements Report {
    @Override
    public void report() {
        System.out.println("微信报表");
    }
}
