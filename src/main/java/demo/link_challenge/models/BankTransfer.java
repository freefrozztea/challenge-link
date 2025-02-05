package demo.link_challenge.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankTransfer extends TransactionModel{
    private String bankCode;
    private String recipientAccount;

    public BankTransfer(Long id, Double amount, String currency, String status,
                        String bankCode, String recipientAccount) {
        super(id, amount, currency, status);
        this.bankCode = bankCode;
        this.recipientAccount = recipientAccount;
    }
}
