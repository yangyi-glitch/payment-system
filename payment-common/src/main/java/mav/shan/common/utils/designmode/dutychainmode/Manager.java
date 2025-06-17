package mav.shan.common.utils.designmode.dutychainmode;

/**
 * 经理审批
 */
public class Manager extends Approver{
    @Override
    public void processRequest(ExpenseRequest request) {
        if (request.getAmount() > 1000 && request.getAmount() <= 5000) {
            System.out.println("Manager approved request of $" + request.getAmount());
        } else if (nextApprover != null) {
            nextApprover.processRequest(request);
        }
    }
}
