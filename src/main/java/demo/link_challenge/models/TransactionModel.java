package demo.link_challenge.models;

import demo.link_challenge.enums.Currency;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Entity
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
public class TransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private UUID transactionId;
    @NotNull
    private Double amount;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private String status;

    public Long getId() {
        return id;
    }

    public @NotNull UUID getTransactionId() {
        return transactionId;
    }

    public @NotNull Double getAmount() {
        return amount;
    }

    public @NotNull Currency getCurrency() {
        return currency;
    }

    public String getStatus() {
        return status;
    }

    public TransactionModel(Long id, UUID transactionId, Double amount, Currency currency, String status) {
        this.id = id;
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
    }
}
