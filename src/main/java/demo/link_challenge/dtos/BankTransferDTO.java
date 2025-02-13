package demo.link_challenge.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

public class BankTransferDTO extends TransactionDTO implements Serializable {
    @NotNull(message = "Bank code cannot be null")
    private String bankCode;

    @NotNull(message = "The recipient's account cannot be null")
    private String recipientAccount;

    public @NotNull(message = "Bank code cannot be null") String getBankCode() {
        return bankCode;
    }

    public void setBankCode(@NotNull(message = "Bank code cannot be null") String bankCode) {
        this.bankCode = bankCode;
    }

    public @NotNull(message = "The recipient's account cannot be null") String getRecipientAccount() {
        return recipientAccount;
    }

    public void setRecipientAccount(@NotNull(message = "The recipient's account cannot be null") String recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    @Override
    public String getType() {
        return "bank";
    }

    @JsonProperty("transaction_id")
    public UUID getTransactionId() {
        return super.getTransactionId();
    }

    public void setTransactionId(UUID transactionId) {
        super.setTransactionId(transactionId);
    }

    @NotNull(message = "User's id cannot be null")
    @JsonProperty("user_id")
    public String userId(){
        return super.getUserId();
    }

    public void setUserId(String userId) {
        super.setUserId(userId);
    }

    public BankTransferDTO() {}
}
