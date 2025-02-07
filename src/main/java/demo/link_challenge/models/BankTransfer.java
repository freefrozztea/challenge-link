package demo.link_challenge.models;

import demo.link_challenge.enums.Currency;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
public class BankTransfer extends TransactionModel{
    private String bankCode;
    private String recipientAccount;

    public BankTransfer(Long id, UUID transactionId, Double amount, String currency, String status, String bankCode, String recipientAccount) {
        super(id, transactionId, amount, currency, status);
        this.bankCode = bankCode;
        this.recipientAccount = recipientAccount;
    }

    public BankTransfer(String bankCode, String recipientAccount) {
        this.bankCode = bankCode;
        this.recipientAccount = recipientAccount;
    }

    public BankTransfer() {

    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getRecipientAccount() {
        return recipientAccount;
    }

    public void setRecipientAccount(String recipientAccount) {
        this.recipientAccount = recipientAccount;
    }
}
