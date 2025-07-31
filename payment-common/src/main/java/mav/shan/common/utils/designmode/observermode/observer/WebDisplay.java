package mav.shan.common.utils.designmode.observermode.observer;

import mav.shan.common.utils.designmode.observermode.Observer_1;
import mav.shan.common.utils.designmode.observermode.observed.WeatherStation;

public class WebDisplay implements Observer_1 {
    private WeatherStation station;

    public WebDisplay() {

    }

    public WebDisplay(WeatherStation station) {
        this.station = station;
        station.registerObserver(this);
    }

    @Override
    public void update(String weatherData) {
//        System.out.println("Web Display: 当前天气数据是 -> " + station.getWeatherData() + "--->" + weatherData);
        System.out.println("Web Display: 当前天气数据是 -> " + weatherData);
    }
}
