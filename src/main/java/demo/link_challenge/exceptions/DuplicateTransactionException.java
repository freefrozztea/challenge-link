package demo.link_challenge.exceptions;

public class DuplicateTransactionException extends RuntimeException {
    private String transactionId;

    public DuplicateTransactionException(String message, String transactionId) {
        super(message);
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
