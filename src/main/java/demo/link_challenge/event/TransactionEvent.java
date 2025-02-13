package demo.link_challenge.event;

public class TransactionEvent {
    private Long transactionId;
    private String status;

    public TransactionEvent(Long transactionId, String status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public String getStatus() {
        return status;
    }
}
