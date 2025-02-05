package demo.link_challenge.services;

import demo.link_challenge.dtos.TransactionDTO;

import java.util.List;

public interface ITransactionService {
    TransactionDTO createTransaction(TransactionDTO transactionDTO);
    TransactionDTO getTransaction(Long id);
    List<TransactionDTO> getAllTransactions();
}
