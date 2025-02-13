package demo.link_challenge.controllers;

import demo.link_challenge.application.TransactionApplicationService;
import demo.link_challenge.dtos.TransactionDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionApplicationService transactionApplicationServiceService;

    public TransactionController(TransactionApplicationService transactionApplicationServiceService) {
        this.transactionApplicationServiceService = transactionApplicationServiceService;
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction( @Valid @RequestBody TransactionDTO transactionDTO ) {
        return ResponseEntity.ok(transactionApplicationServiceService.createTransaction(transactionDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getTransaction(@PathVariable String id) {
        return ResponseEntity.ok(transactionApplicationServiceService.getTransaction(UUID.fromString(id)));
    }

    @GetMapping
    public ResponseEntity<Page<TransactionDTO>> getAllTransactions(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort) {

        String[] sortParams = sort.split(",");
        Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);
        Sort.Order order = new Sort.Order(direction, sortParams[0]);

        Pageable pageable = PageRequest.of(page, size, Sort.by(order));
        return ResponseEntity.ok(transactionApplicationServiceService.getAllTransactions(userId, type, status, pageable));
    }

    private void validateTransactionType(TransactionDTO transactionDTO) {
        if (!Set.of("CardPayment", "BankTransfer", "P2PTransfer").contains(transactionDTO.getType())) {
            throw new IllegalArgumentException("Unsupported transaction type: " + transactionDTO.getType());
        }
    }
}
