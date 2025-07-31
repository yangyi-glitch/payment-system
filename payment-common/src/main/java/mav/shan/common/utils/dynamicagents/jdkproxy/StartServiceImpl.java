package mav.shan.common.utils.dynamicagents.jdkproxy;

import java.lang.reflect.Proxy;

public class StartServiceImpl implements StartService {

    @Override
    public void start() {
        System.out.println("启动成功");
    }

    @Override
    public void stop() {
        System.out.println("停止成功");
    }
}
