package mav.shan.common.utils.designmode.singletonmode;


import mav.shan.common.utils.designmode.singletonmode.hungry.Singleton_1;
import mav.shan.common.utils.designmode.singletonmode.lazy.Singleton_2;

/**
 * @Description:单例模式
 * 通过spring注解@Scope("singleton") 也可以实现单例模式
 */
public class Main {
    public static void main(String[] args) {
        Singleton_1 instance_1 = Singleton_1.getInstance();
        Singleton_2 instance_2 = Singleton_2.getInstanceV2();
        System.out.println(instance_1.getName());
        System.out.println(instance_2.getName());
    }
}
