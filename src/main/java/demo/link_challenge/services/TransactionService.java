package demo.link_challenge.services;

import demo.link_challenge.repository.TransactionRepository;
import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.models.TransactionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService{

    private final TransactionRepository transactionRepository;

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        TransactionModel transaction = new TransactionModel();
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setCurrency(transactionDTO.getCurrency());
        transaction.setStatus("PENDING");
        transaction = transactionRepository.save(transaction);
        return new TransactionDTO(transaction.getId(), transaction.getAmount(), transaction.getCurrency(), transaction.getStatus());
    }

    @Override
    public TransactionDTO getTransaction(Long id) {
        TransactionModel transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return new TransactionDTO(transaction.getId(), transaction.getAmount(), transaction.getCurrency(), transaction.getStatus());
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        List<TransactionModel> transactions = transactionRepository.findAll();
        List<TransactionDTO> transactionDTOs = new ArrayList<>();
        for (TransactionModel transaction : transactions) {
            transactionDTOs.add(new TransactionDTO(transaction.getId(), transaction.getAmount(), transaction.getCurrency(), transaction.getStatus()));
        }
        return transactionDTOs;
    }
}
