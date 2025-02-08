package demo.link_challenge.strategies;

import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.models.TransactionModel;

public interface ITransactionStrategy {
    TransactionModel process(TransactionDTO transactionDTO);
    TransactionDTO convertToDTO(TransactionModel entity);
}
