package mav.shan.common.utils.designmode.singletonmode;


import mav.shan.common.utils.designmode.singletonmode.hungry.Singleton_1;
import mav.shan.common.utils.designmode.singletonmode.lazy.Singleton_2;

/**
 * 单例模式还可以通过spring注解@Scope("singleton")
 */
public class Main {
    public static void main(String[] args) {
        Singleton_1 instance_1 = Singleton_1.getInstance();
        Singleton_2 instance_2 = Singleton_2.getInstanceV2();
        System.out.println(instance_1.getName());
        System.out.println(instance_2.getName());
    }
}
