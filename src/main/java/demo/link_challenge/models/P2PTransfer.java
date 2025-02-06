package demo.link_challenge.models;

import demo.link_challenge.enums.Currency;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class P2PTransfer extends TransactionModel {
    private String senderId;
    private String recipientId;
    private String note;

    public P2PTransfer(Long id, UUID transactionId, Double amount, Currency currency, String status,
                       String senderId, String recipientId, String note) {
        super(id, transactionId, amount, currency, status);
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.note = note;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public String getNote() {
        return note;
    }
}
