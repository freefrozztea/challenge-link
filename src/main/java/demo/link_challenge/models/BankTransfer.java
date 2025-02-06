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
public class BankTransfer extends TransactionModel{
    private String bankCode;
    private String recipientAccount;

    public BankTransfer(Long id, UUID transactionId, Double amount, Currency currency, String status,
                        String bankCode, String recipientAccount) {
        super(id, transactionId, amount, currency, status);
        this.bankCode = bankCode;
        this.recipientAccount = recipientAccount;
    }

    public String getBankCode() {
        return bankCode;
    }

    public String getRecipientAccount() {
        return recipientAccount;
    }
}
