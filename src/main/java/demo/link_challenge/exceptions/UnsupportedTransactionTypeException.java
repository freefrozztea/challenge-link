package demo.link_challenge.exceptions;

public class UnsupportedTransactionTypeException extends RuntimeException {


    private String errorCode;

    public UnsupportedTransactionTypeException(String message) {
        super(message);
    }

    public UnsupportedTransactionTypeException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
