package demo.link_challenge.services;

import demo.link_challenge.enums.Currency;
import demo.link_challenge.exceptions.DuplicateTransactionException;
import demo.link_challenge.exceptions.InvalidTransactionException;
import demo.link_challenge.mappers.TransactionMapper;
import demo.link_challenge.repository.TransactionRepository;
import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.models.TransactionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransactionService implements ITransactionService{

    private final TransactionRepository transactionRepository;
    private final IIdempotencyService idempotencyService;
    private final ICurrencyService currencyService;

    public TransactionService(TransactionRepository transactionRepository, IIdempotencyService idempotencyService, ICurrencyService currencyService) {
        this.transactionRepository = transactionRepository;
        this.idempotencyService = idempotencyService;
        this.currencyService = currencyService;
    }

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        validateTransaction(transactionDTO);
        TransactionModel transaction = TransactionMapper.toEntity(transactionDTO);
        transaction = transactionRepository.save(transaction);
        return TransactionMapper.toDto(transaction);
    }

    @Override
    public TransactionDTO getTransaction(Long id) {
        TransactionModel transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return TransactionMapper.toDto(transaction);
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        List<TransactionModel> transactions = transactionRepository.findAll();
        return transactions.stream().map(TransactionMapper::toDto).toList();
    }

    private void validateTransaction(TransactionDTO transactionDTO) {
        if (!idempotencyService.checkAndStore(transactionDTO.getTransactionId().toString())) {
            throw new DuplicateTransactionException("Duplicate transaction detected", transactionDTO.getTransactionId().toString());
        }

        if (transactionDTO.getAmount() <= 0) {
            throw new InvalidTransactionException("The transaction amount must be greater than zero", "INVALID_AMOUNT");
        }

        if (!currencyService.isSupportedCurrency(Currency.valueOf(transactionDTO.getCurrency()))) {
            throw new InvalidTransactionException("Unsupported currency: " + transactionDTO.getCurrency(), "UNSUPPORTED_CURRENCY");
        }
    }
}
