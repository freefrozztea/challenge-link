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
public class CardPayment extends TransactionModel {
    private String cardId;
    private String merchantName;
    private String merchantId;
    private int mccCode;

    public CardPayment(Long id, UUID transactionId, Double amount, Currency currency, String status,
                       String cardId, String merchantName, String merchantId, int mccCode) {
        super(id, transactionId, amount, currency, status);
        this.cardId = cardId;
        this.merchantName = merchantName;
        this.merchantId = merchantId;
        this.mccCode = mccCode;
    }

    public String getCardId() {
        return cardId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public int getMccCode() {
        return mccCode;
    }
}
