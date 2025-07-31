package mav.shan.common.utils.designmode.observermode.observer;

import mav.shan.common.utils.designmode.observermode.Observer_1;
import mav.shan.common.utils.designmode.observermode.observed.WeatherStation;

public class PhoneDisplay implements Observer_1 {
    private WeatherStation station;

    public PhoneDisplay() {

    }

    public PhoneDisplay(WeatherStation station) {
        this.station = station;
        station.registerObserver(this);
    }

    @Override
    public void update(String weatherData) {
//        System.out.println("Phone Display: 天气数据已更新为 -> " + station.getWeatherData() + "--->" + weatherData);
        System.out.println("Phone Display: 天气数据已更新为 -> " + weatherData);
    }
}
