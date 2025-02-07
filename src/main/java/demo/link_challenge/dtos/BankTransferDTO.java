package demo.link_challenge.dtos;

import jakarta.validation.constraints.NotNull;

public class BankTransferDTO extends TransactionDTO{
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
}
