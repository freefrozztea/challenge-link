package demo.link_challenge.models;

import demo.link_challenge.enums.Currency;
import demo.link_challenge.enums.TransactionStatus;
import jakarta.persistence.Entity;

import java.util.UUID;

@Entity
public class CardPayment extends TransactionModel {
    private String cardId;
    private String merchantName;
    private String merchantId;
    private int mccCode;

    public CardPayment(Long id, UUID transactionId, String userId, Double amount, Currency currency, TransactionStatus status, String cardId, String merchantName, String merchantId, int mccCode) {
        super(id, transactionId, userId, amount, currency, status);
        this.cardId = cardId;
        this.merchantName = merchantName;
        this.merchantId = merchantId;
        this.mccCode = mccCode;
    }

    public CardPayment(String cardId, String merchantName, String merchantId, int mccCode) {
        this.cardId = cardId;
        this.merchantName = merchantName;
        this.merchantId = merchantId;
        this.mccCode = mccCode;
    }

    public CardPayment() {

    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public int getMccCode() {
        return mccCode;
    }

    public void setMccCode(int mccCode) {
        this.mccCode = mccCode;
    }
}
