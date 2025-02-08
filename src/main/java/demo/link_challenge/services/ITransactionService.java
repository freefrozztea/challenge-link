package demo.link_challenge.services;

import demo.link_challenge.dtos.TransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITransactionService {
    TransactionDTO createTransaction(TransactionDTO transactionDTO);
    TransactionDTO getTransaction(Long id);
    Page<TransactionDTO> getAllTransactions(String userId, String type, String status, Pageable pageable);
}
