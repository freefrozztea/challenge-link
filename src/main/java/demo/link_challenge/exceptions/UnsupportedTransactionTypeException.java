package demo.link_challenge.exceptions;

public class UnsupportedTransactionTypeException extends RuntimeException {
    public UnsupportedTransactionTypeException(String type) {
        super("Transaction type not supported: " + type);
    }
}
