package demo.link_challenge.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class P2PTransfer extends TransactionModel {
    private String senderId;
    private String recipientId;
    private String note;

    public P2PTransfer(Long id, Double amount, String currency, String status,
                       String senderId, String recipientId, String note) {
        super(id, amount, currency, status);
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.note = note;
    }
}
