package mav.shan.common.custexception;

public class BusinessException extends RuntimeException {
    protected String message;

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
