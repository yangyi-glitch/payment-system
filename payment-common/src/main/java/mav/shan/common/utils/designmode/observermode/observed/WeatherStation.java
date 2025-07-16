package mav.shan.common.utils.designmode.observermode.observed;

import mav.shan.common.utils.designmode.observermode.Observer_1;
import mav.shan.common.utils.designmode.observermode.Subject_1;

import java.util.ArrayList;
import java.util.List;

public class WeatherStation implements Subject_1 {

    private List<Observer_1> observers = new ArrayList<>();
    private String weatherData;
    String total;

    public void setWeatherData(String data, String total) {
        this.weatherData = data;
        this.total = total;
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer_1 observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer_1 observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer_1 observer : observers) {
            observer.update(this.total);
        }
    }

    public String getWeatherData() {
        return weatherData;
    }
}
