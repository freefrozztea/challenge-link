package demo.link_challenge.exceptions;

public class InvalidTransactionException extends RuntimeException {
    private String errorCode;

    public InvalidTransactionException(String message) {
        super(message);
    }

    public InvalidTransactionException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
