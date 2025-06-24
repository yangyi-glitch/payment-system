package mav.shan.common.utils.designmode.observermode;

import mav.shan.common.utils.designmode.observermode.observed.WeatherStation;
import mav.shan.common.utils.designmode.observermode.observer.PhoneDisplay;
import mav.shan.common.utils.designmode.observermode.observer.WebDisplay;

/**
 * @Description:观察者模式
 */
public class Main {
    public static void main(String[] args) {
        WeatherStation station = new WeatherStation();

        // 注册观察者
        new PhoneDisplay(station);
        new WebDisplay(station);

        // 更新数据，触发通知
        station.setWeatherData("晴天 25°C", "1000");
    }
}
