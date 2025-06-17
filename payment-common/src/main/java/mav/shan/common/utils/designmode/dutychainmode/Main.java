package mav.shan.common.utils.designmode.dutychainmode;

/**
 * @Description:责任链模式
 */
public class Main {
    public static void main(String[] args) {
        // 构建审批链
        Approver teamLeader = new TeamLeader();
        //经理
        Approver manager = new Manager();
        //总监
        Approver director = new Director();

        teamLeader.setNextApprover(manager);
        manager.setNextApprover(director);

        // 发起审批请求
        ExpenseRequest r1 = new ExpenseRequest(800);
        ExpenseRequest r2 = new ExpenseRequest(3000);
        ExpenseRequest r3 = new ExpenseRequest(6000);

        teamLeader.processRequest(r1); // 组长处理
        teamLeader.processRequest(r2); // 经理处理
        teamLeader.processRequest(r3); // 总监处理
    }
}
