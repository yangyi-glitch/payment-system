package mav.shan.common.utils.designmode.observermode;

public interface Subject_1 {
    void registerObserver(Observer_1 observer);
    void removeObserver(Observer_1 observer);
    void notifyObservers();
}
