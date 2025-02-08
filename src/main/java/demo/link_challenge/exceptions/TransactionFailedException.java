package demo.link_challenge.exceptions;

public class TransactionFailedException extends RuntimeException {
    public TransactionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
