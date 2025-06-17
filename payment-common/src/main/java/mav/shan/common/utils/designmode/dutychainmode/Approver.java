package mav.shan.common.utils.designmode.dutychainmode;

/**
 * @Description: 审批者抽象类
 */
abstract class Approver {
    protected Approver nextApprover;

    public void setNextApprover(Approver nextApprover) {
        this.nextApprover = nextApprover;
    }

    public abstract void processRequest(ExpenseRequest request);
}
