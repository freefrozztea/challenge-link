package demo.link_challenge.dtos;

import demo.link_challenge.enums.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TransactionDTO {
    @NotNull(message = "Transaction ID cannot be null")
    private UUID transactionId;

    @NotNull(message = "The amount cannot be zero")
    @Positive(message = "The amount must be greater than zero")
    private Double amount;

    @NotNull(message = "The currency cannot be null")
    private Currency currency;

    public @NotNull(message = "Transaction ID cannot be null") UUID getTransactionId() {
        return transactionId;
    }

    public abstract String getType();

    public void setTransactionId(@NotNull(message = "Transaction ID cannot be null") UUID transactionId) {
        this.transactionId = transactionId;
    }

    public @NotNull(message = "The amount cannot be zero") @Positive(message = "The amount must be greater than zero") Double getAmount() {
        return amount;
    }

    public void setAmount(@NotNull(message = "The amount cannot be zero") @Positive(message = "The amount must be greater than zero") Double amount) {
        this.amount = amount;
    }

    public @NotNull(message = "The currency cannot be null") Currency getCurrency() {
        return currency;
    }

    public void setCurrency(@NotNull(message = "The currency cannot be null") Currency currency) {
        this.currency = currency;
    }

}
