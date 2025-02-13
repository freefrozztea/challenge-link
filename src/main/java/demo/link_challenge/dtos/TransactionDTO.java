package demo.link_challenge.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import demo.link_challenge.enums.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;



@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type" // <- Campo que define el tipo concreto
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BankTransferDTO.class, name = "bank"),
        @JsonSubTypes.Type(value = CardPaymentDTO.class, name = "card"),
        @JsonSubTypes.Type(value = P2PTransferDTO.class, name = "p2p")
})
public abstract class TransactionDTO implements Serializable {
    private Long id;

    @JsonIgnore
    private UUID transactionId;

    @NotNull(message = "The amount cannot be zero")
    @Positive(message = "The amount must be greater than zero")
    private Double amount;

    @NotNull(message = "The currency cannot be null")
    private Currency currency;

    @JsonIgnore
    private String userId;

    private String status;

    public UUID getTransactionId() {
        return transactionId;
    }

    public abstract String getType();

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
