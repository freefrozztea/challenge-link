package demo.link_challenge.strategies;

import demo.link_challenge.dtos.BankTransferDTO;
import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.models.BankTransfer;
import demo.link_challenge.models.TransactionModel;
import org.springframework.stereotype.Component;

@Component
public class BankTransferStrategy implements ITransactionStrategy {
    @Override
    public TransactionModel process(TransactionDTO transactionDTO) {
        BankTransferDTO bankTransferDTO = (BankTransferDTO) transactionDTO;

        BankTransfer bankTransfer = new BankTransfer();
        bankTransfer.setAmount(bankTransferDTO.getAmount());
        bankTransfer.setCurrency(bankTransferDTO.getCurrency());
        bankTransfer.setBankCode(bankTransferDTO.getBankCode());
        bankTransfer.setRecipientAccount(bankTransferDTO.getRecipientAccount());
        return bankTransfer;
    }
}
