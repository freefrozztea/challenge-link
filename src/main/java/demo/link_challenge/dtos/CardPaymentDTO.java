package demo.link_challenge.dtos;

import jakarta.validation.constraints.NotNull;

public class CardPaymentDTO extends TransactionDTO {
    @NotNull(message = "Card ID cannot be null")
    private String cardId;

    @NotNull(message = "The name of the merchant cannot be null")
    private String merchantName;

    @NotNull(message = "The merchant ID cannot be null")
    private String merchantId;

    @NotNull(message = "MCC code cannot be null")
    private int mccCode;

    public @NotNull(message = "Card ID cannot be null") String getCardId() {
        return cardId;
    }

    public void setCardId(@NotNull(message = "Card ID cannot be null") String cardId) {
        this.cardId = cardId;
    }

    public @NotNull(message = "The name of the merchant cannot be null") String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(@NotNull(message = "The name of the merchant cannot be null") String merchantName) {
        this.merchantName = merchantName;
    }

    public @NotNull(message = "The merchant ID cannot be null") String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(@NotNull(message = "The merchant ID cannot be null") String merchantId) {
        this.merchantId = merchantId;
    }

    @NotNull(message = "MCC code cannot be null")
    public int getMccCode() {
        return mccCode;
    }

    public void setMccCode(@NotNull(message = "MCC code cannot be null") int mccCode) {
        this.mccCode = mccCode;
    }

    @Override
    public String getType() {
        return "card";
    }
}
