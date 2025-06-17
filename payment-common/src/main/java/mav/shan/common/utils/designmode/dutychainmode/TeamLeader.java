package mav.shan.common.utils.designmode.dutychainmode;

/**
 * 组长审批
 */
public class TeamLeader extends Approver{
    @Override
    public void processRequest(ExpenseRequest request) {
        if (request.getAmount() <= 1000) {
            System.out.println("TeamLeader approved request of $" + request.getAmount());
        } else if (nextApprover != null) {
            nextApprover.processRequest(request);
        }
    }
}
