package demo.link_challenge.services;

import demo.link_challenge.dtos.TransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ITransactionService {
    TransactionDTO createTransaction(TransactionDTO transactionDTO);
    TransactionDTO getTransaction(UUID id);
    Page<TransactionDTO> getAllTransactions(String userId, String type, String status, Pageable pageable);
}
