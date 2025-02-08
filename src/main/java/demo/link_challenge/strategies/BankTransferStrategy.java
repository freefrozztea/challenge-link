package demo.link_challenge.strategies;

import demo.link_challenge.dtos.BankTransferDTO;
import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.exceptions.InvalidTransactionException;
import demo.link_challenge.mappers.BankTransferMapper;
import demo.link_challenge.models.BankTransfer;
import demo.link_challenge.models.TransactionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BankTransferStrategy implements ITransactionStrategy {

    private final BankTransferMapper bankTransferMapper;

    @Autowired
    public BankTransferStrategy(BankTransferMapper bankTransferMapper) {
        this.bankTransferMapper = bankTransferMapper;
    }
    @Override
    public TransactionModel process(TransactionDTO transactionDTO) {
        BankTransferDTO bankTransferDTO = (BankTransferDTO) transactionDTO;

        if (bankTransferDTO.getRecipientAccount() == null || bankTransferDTO.getRecipientAccount().isEmpty()) {
            throw new InvalidTransactionException("The recipient's account cannot be empty", "INVALID_RECIPIENT_ACCOUNT");
        }

        BankTransfer bankTransfer = bankTransferMapper.toEntity(bankTransferDTO);

        return bankTransfer;
    }

    @Override
    public TransactionDTO convertToDTO(TransactionModel entity) {
        return bankTransferMapper.toDto((BankTransfer) entity);
    }
}
