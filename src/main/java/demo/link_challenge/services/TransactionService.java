package demo.link_challenge.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.link_challenge.dtos.BankTransferDTO;
import demo.link_challenge.dtos.CardPaymentDTO;
import demo.link_challenge.dtos.P2PTransferDTO;
import demo.link_challenge.enums.Currency;
import demo.link_challenge.enums.TransactionStatus;
import demo.link_challenge.exceptions.DuplicateTransactionException;
import demo.link_challenge.exceptions.InvalidTransactionException;
import demo.link_challenge.exceptions.TransactionFailedException;
import demo.link_challenge.exceptions.TransactionNotFoundException;
import demo.link_challenge.mappers.BankTransferMapper;
import demo.link_challenge.mappers.CardPaymentMapper;
import demo.link_challenge.mappers.P2PTransferMapper;
import demo.link_challenge.mappers.TransactionMapper;
import demo.link_challenge.models.BankTransfer;
import demo.link_challenge.models.CardPayment;
import demo.link_challenge.models.P2PTransfer;
import demo.link_challenge.repository.TransactionRepository;
import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.models.TransactionModel;
import demo.link_challenge.strategies.TransactionContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class TransactionService implements ITransactionService{

    private final TransactionRepository transactionRepository;
    private final IIdempotencyService idempotencyService;
    private final ICurrencyService currencyService;
    private final TransactionContext transactionContext;
    private final RedisTemplate<String, String> redisTemplate;
    private final BankTransferMapper bankTransferMapper;
    private final CardPaymentMapper cardPaymentMapper;
    private final P2PTransferMapper p2PTransferMapper;
    private final ObjectMapper objectMapper;

    public TransactionService(TransactionRepository transactionRepository, IIdempotencyService idempotencyService, ICurrencyService currencyService, RedisTemplate<String, String> redisTemplate, TransactionContext transactionContext, RedisTemplate<String, String> redisTemplate1, BankTransferMapper bankTransferMapper, CardPaymentMapper cardPaymentMapper, P2PTransferMapper p2PTransferMapper, ObjectMapper objectMapper) {
        this.transactionRepository = transactionRepository;
        this.idempotencyService = idempotencyService;
        this.currencyService = currencyService;
        this.transactionContext = transactionContext;
        this.redisTemplate = redisTemplate1;
        this.bankTransferMapper = bankTransferMapper;
        this.cardPaymentMapper = cardPaymentMapper;
        this.p2PTransferMapper = p2PTransferMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {

        if (transactionDTO.getTransactionId() == null) {
            transactionDTO.setTransactionId(UUID.randomUUID());
        }

        validateTransaction(transactionDTO);
        TransactionModel transaction = transactionContext.executeStrategy(transactionDTO.getType().toLowerCase(), transactionDTO);
        transaction.setStatus(TransactionStatus.PENDING);

        try {
            TransactionModel savedTransaction = transactionRepository.save(transaction);
            return convertToDTO(savedTransaction);
        } catch (Exception ex) {
            handleFailedTransaction(transactionDTO, ex);
            throw new TransactionFailedException("Error processing transaction", ex);
        }

    }

    @Override
    public TransactionDTO getTransaction(Long id) {
        return transactionRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
    }

    @Override
    public Page<TransactionDTO> getAllTransactions(
            String userId, String type, String status, Pageable pageable) {

        Specification<TransactionModel> spec = Specification.where(null);
        if (userId != null) spec = spec.and((root, query, cb) -> cb.equal(root.get("userId"), userId));
        if (type != null) spec = spec.and((root, query, cb) -> cb.equal(root.get("type"), type));
        if (status != null) spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));

        return transactionRepository.findAll(spec, pageable).map(this::convertToDTO);
    }

    public void retryTransaction(TransactionDTO dto) {
        TransactionModel entity = transactionContext.executeStrategy(dto.getType(), dto);
        entity.setStatus(TransactionStatus.RETRYING);
        transactionRepository.save(entity);
        //paymentGateway.process(entity);
    }

    private void validateTransaction(TransactionDTO transactionDTO) {
        validateIdempotency(transactionDTO.getTransactionId());
        validateAmount(transactionDTO.getAmount());
        validateCurrency(transactionDTO.getCurrency().toString());
    }

    private void validateIdempotency(UUID transactionId) {
        idempotencyService.checkIdempotency(transactionId);
    }

    private void validateAmount(Double amount) {
        if (amount == null || amount <= 0) {
            throw new InvalidTransactionException("Amount must be positive", "INVALID_AMOUNT");
        }
    }

    private void validateCurrency(String currencyCode) {
        try {
            Currency currency = Currency.valueOf(currencyCode);
            if (!currencyService.isSupportedCurrency(currency)) {
                throw new InvalidTransactionException("Unsupported currency", "UNSUPPORTED_CURRENCY");
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidTransactionException("Invalid currency code", "INVALID_CURRENCY_CODE");
        }
    }

    private TransactionDTO convertToDTO(TransactionModel entity) {
        if (entity instanceof CardPayment) {
            return cardPaymentMapper.toDto((CardPayment) entity);
        } else if (entity instanceof BankTransfer) {
            return bankTransferMapper.toDto((BankTransfer) entity);
        } else if (entity instanceof P2PTransfer) {
            return p2PTransferMapper.toDto((P2PTransfer) entity);
        }
        throw new IllegalArgumentException("Unsupported entity type");
    }

    private void handleFailedTransaction(TransactionDTO dto, Exception ex) {
        try {
            String transactionId = dto.getTransactionId() != null ? dto.getTransactionId().toString() : "unknown";
            String jsonPayload = objectMapper.writeValueAsString(dto);
            redisTemplate.opsForValue().set("failed:" + transactionId, jsonPayload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize transaction", e);
        }
    }
}
