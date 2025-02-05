package demo.link_challenge.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardPayment extends TransactionModel {
    private String cardId;
    private String merchantName;
    private String merchantId;
    private int mccCode;

    public CardPayment(Long id, Double amount, String currency, String status,
                       String cardId, String merchantName, String merchantId, int mccCode) {
        super(id, amount, currency, status);
        this.cardId = cardId;
        this.merchantName = merchantName;
        this.merchantId = merchantId;
        this.mccCode = mccCode;
    }
}
