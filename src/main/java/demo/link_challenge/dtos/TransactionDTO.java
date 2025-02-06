package demo.link_challenge.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Long id;
    private UUID transactionId;
    private Double amount;
    private String currency;
    private String status;
    private String type;
    private String cardId;
    private String merchantName;
    private String merchantId;
    private int mccCode;
    private String bankCode;
    private String recipientAccount;
    private String senderId;
    private String recipientId;
    private String note;

    public Long getId() {
        return this.id;
    }

    public UUID getTransactionId() {
        return this.transactionId;
    }

    public Double getAmount() {
        return this.amount;
    }

    public String getCurrency() {
        return this.currency;
    }

    public String getStatus() {
        return this.status;
    }

    public String getType() {
        return this.type;
    }

    public String getCardId() {
        return this.cardId;
    }

    public String getMerchantName() {
        return this.merchantName;
    }

    public String getMerchantId() {
        return this.merchantId;
    }

    public int getMccCode() {
        return this.mccCode;
    }

    public String getBankCode() {
        return this.bankCode;
    }

    public String getRecipientAccount() {
        return this.recipientAccount;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public String getRecipientId() {
        return this.recipientId;
    }

    public String getNote() {
        return this.note;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setMccCode(int mccCode) {
        this.mccCode = mccCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public void setRecipientAccount(String recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
