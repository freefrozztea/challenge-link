package demo.link_challenge.controllers;

import demo.link_challenge.dtos.BankTransferDTO;
import demo.link_challenge.dtos.CardPaymentDTO;
import demo.link_challenge.dtos.P2PTransferDTO;
import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<TransactionDTO> createTransaction( @Valid @RequestBody TransactionDTO transactionDTO ) {
        return ResponseEntity.ok(transactionService.createTransaction(transactionDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransaction(id));
    }

    @GetMapping
    public ResponseEntity<Page<TransactionDTO>> getAllTransactions(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(transactionService.getAllTransactions(pageable));
    }

    private void validateTransactionType(TransactionDTO transactionDTO) {
        if (!Set.of("CardPayment", "BankTransfer", "P2PTransfer").contains(transactionDTO.getType())) {
            throw new IllegalArgumentException("Unsupported transaction type: " + transactionDTO.getType());
        }
    }
}
