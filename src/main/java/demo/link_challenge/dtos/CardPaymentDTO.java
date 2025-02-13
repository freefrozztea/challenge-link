package demo.link_challenge.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import demo.link_challenge.enums.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.util.UUID;

public class CardPaymentDTO extends TransactionDTO implements Serializable {

    @NotNull(message = "Card ID cannot be null")
    @JsonProperty("card_id")
    private String cardId;

    @NotNull(message = "The merchant cannot be null")
    @JsonProperty("merchant")
    private MerchantDTO merchant;

    @NotNull(message = "MCC code cannot be null")
    @JsonProperty("mcc_code")
    private int mccCode;

    @JsonProperty("created_at")
    private String createdAt;



    public CardPaymentDTO() {}

    public CardPaymentDTO(Long id, UUID transactionId, @NotNull(message = "The amount cannot be zero") @Positive(message = "The amount must be greater than zero") Double amount, @NotNull(message = "The currency cannot be null") Currency currency, @NotNull(message = "The user ID cannot be null") String userId, String status, String cardId, MerchantDTO merchant, int mccCode, String createdAt) {
        super(id, transactionId, amount, currency, userId, status);
        this.cardId = cardId;
        this.merchant = merchant;
        this.mccCode = mccCode;
        this.createdAt = createdAt;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public MerchantDTO getMerchant() {
        return merchant;
    }

    public void setMerchant(MerchantDTO merchant) {
        this.merchant = merchant;
    }

    public int getMccCode() {
        return mccCode;
    }

    public void setMccCode(int mccCode) {
        this.mccCode = mccCode;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    @JsonProperty("type")
    public String getType() {
        return "card";
    }

    @JsonProperty("payment_id")
    public UUID getPaymentId() {
        return super.getTransactionId();
    }

    public void setPaymentId(UUID paymentId) {
        super.setTransactionId(paymentId);
    }

    @NotNull(message = "User's id cannot be null")
    @JsonProperty("user_id")
    public String userId(){
        return super.getUserId();
    }

    public void setUserId(String userId) {
        super.setUserId(userId);
    }
}
