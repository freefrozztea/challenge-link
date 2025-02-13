package demo.link_challenge.models;

import demo.link_challenge.enums.Currency;
import demo.link_challenge.enums.TransactionStatus;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
public class P2PTransfer extends TransactionModel {
    private String senderId;
    private String recipientId;
    private String note;

    public P2PTransfer(Long id, UUID transactionId, String userId, Double amount, Currency currency, TransactionStatus status, String senderId, String recipientId, String note) {
        super(id, transactionId, userId, amount, currency, status);
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.note = note;
    }

    public P2PTransfer(String senderId, String recipientId, String note) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.note = note;
    }

    public P2PTransfer() {

    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
