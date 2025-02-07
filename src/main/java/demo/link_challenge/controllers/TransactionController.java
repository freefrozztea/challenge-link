package demo.link_challenge.controllers;

import demo.link_challenge.dtos.BankTransferDTO;
import demo.link_challenge.dtos.CardPaymentDTO;
import demo.link_challenge.dtos.P2PTransferDTO;
import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.services.TransactionService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> createTransaction(            @RequestParam String type,
                                                           @Valid @RequestBody TransactionDTO transactionDTO) {
        if (transactionDTO.getTransactionId() == null) {
            transactionDTO.setTransactionId(UUID.randomUUID());
        }
        switch (type.toLowerCase()) {
            case "card":
                if (!(transactionDTO instanceof CardPaymentDTO)) {
                    throw new IllegalArgumentException("Tipo de transacción no coincide con el DTO proporcionado");
                }
                return ResponseEntity.ok(transactionService.createCardPayment((CardPaymentDTO) transactionDTO));

            case "bank":
                if (!(transactionDTO instanceof BankTransferDTO)) {
                    throw new IllegalArgumentException("Tipo de transacción no coincide con el DTO proporcionado");
                }
                return ResponseEntity.ok(transactionService.createBankTransfer((BankTransferDTO) transactionDTO));

            case "p2p":
                if (!(transactionDTO instanceof P2PTransferDTO)) {
                    throw new IllegalArgumentException("Tipo de transacción no coincide con el DTO proporcionado");
                }
                return ResponseEntity.ok(transactionService.createP2PTransfer((P2PTransferDTO) transactionDTO));

            default:
                throw new IllegalArgumentException("Unsupported transaction type: " + type);
        }
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
