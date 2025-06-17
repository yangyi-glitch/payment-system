package mav.shan.common.utils.designmode.singletonmode.lazy;

public class Singleton_2 {
    private static Singleton_2 instance;

    private String name;

    private Singleton_2(String name) {
        this.name = name;
    }

    public static Singleton_2 getInstance() {
        if (instance == null) {
            synchronized (Singleton_2.class) {
                if (instance == null) {
                    instance = new Singleton_2("懒汉V1");
                }
            }
        }
        return instance;
    }

    private static class SingletonHolder {
        private static Singleton_2 instance = new Singleton_2("懒汉V2");
    }

    //静态内部类
    public static Singleton_2 getInstanceV2() {
        return SingletonHolder.instance;
    }

    public String getName() {
        return name;
    }
}
