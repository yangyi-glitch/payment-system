package mav.shan.common.utils.designmode.buildermode;

public class Main {
    public static void main(String[] args) {
        Computer intel = ComputerBuilder.builder().buildCpu("Intel").buildRam("16G").buildStorage("1T").build();
        Computer amd = ComputerBuilder.builder().buildCpu("AMD").buildRam("16G").buildStorage("1T").build();
        Computer amd1 = ComputerBuilder.builder().buildCpu("AMD").buildRam("16G").buildStorage("1T").build();
        System.out.println(intel);
        System.out.println(amd);
    }
}
