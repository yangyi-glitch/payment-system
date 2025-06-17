package mav.shan.common.utils.designmode.singletonmode.hungry;

public class Singleton_1 {
    private String name;
    private static Singleton_1 instance = new Singleton_1("饿汉");

    private Singleton_1(String name) {
        this.name = name;
    }

    public static Singleton_1 getInstance() {
        return instance;
    }

    public String getName() {
        return this.name;
    }
}
