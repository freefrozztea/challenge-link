package demo.link_challenge.controllers;

import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        if (transactionDTO.getTransactionId() == null) {
            transactionDTO.setTransactionId(UUID.randomUUID());
        }
        validateTransactionType(transactionDTO);
        return ResponseEntity.ok(transactionService.createTransaction(transactionDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransaction(id));
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    private void validateTransactionType(TransactionDTO transactionDTO) {
        if (!Set.of("CardPayment", "BankTransfer", "P2PTransfer").contains(transactionDTO.getType())) {
            throw new IllegalArgumentException("Unsupported transaction type: " + transactionDTO.getType());
        }
    }
}
