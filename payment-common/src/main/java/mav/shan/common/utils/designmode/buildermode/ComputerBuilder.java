package mav.shan.common.utils.designmode.buildermode;

public class ComputerBuilder {
    private Computer computer = new Computer();

    public static ComputerBuilder builder() {

        return new ComputerBuilder();
    }

    public ComputerBuilder buildCpu(String cpu) {
        if (cpu == "Intel") {
            computer.setBrand("英特尔");
        }
        if (cpu == "AMD") {
            computer.setBrand("锐龙");
        }
        computer.setCpu(cpu);
        return this;
    }

    public ComputerBuilder buildRam(String ram) {
        computer.setRam(ram);
        return this;
    }

    public ComputerBuilder buildStorage(String storage) {
        computer.setStorage(storage);
        return this;
    }

    public Computer build() {
        return computer;
    }
}
