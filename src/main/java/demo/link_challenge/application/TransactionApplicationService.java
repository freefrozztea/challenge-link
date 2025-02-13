package demo.link_challenge.application;

import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.event.TransactionEvent;
import demo.link_challenge.services.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TransactionApplicationService {
    private final TransactionService transactionService;
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public TransactionApplicationService(TransactionService transactionService, KafkaTemplate<String, TransactionEvent> kafkaTemplate) {
        this.transactionService = transactionService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        TransactionDTO savedTransaction = transactionService.createTransaction(transactionDTO);
        //kafkaTemplate.send("transaction-events", new TransactionEvent(savedTransaction.getId(), "CREATED"));
        return savedTransaction;
    }

    public String getTransaction(UUID id) {
        return transactionService.getTransaction(id).getStatus();
    }

    public Page<TransactionDTO> getAllTransactions(String userId, String type, String status, Pageable pageable) {
        return transactionService.getAllTransactions(userId, type, status, pageable);
    }
}

