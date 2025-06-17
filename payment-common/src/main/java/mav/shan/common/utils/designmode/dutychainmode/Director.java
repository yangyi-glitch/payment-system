package mav.shan.common.utils.designmode.dutychainmode;

/*
 * 总监审批
 */
public class Director extends Approver {
    @Override
    public void processRequest(ExpenseRequest request) {
        if (request.getAmount() > 5000) {
            System.out.println("Director approved request of $" + request.getAmount());
        } else if (nextApprover != null) {
            nextApprover.processRequest(request);
        }
    }
}
