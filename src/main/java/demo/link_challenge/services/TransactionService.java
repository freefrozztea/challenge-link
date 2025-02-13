package demo.link_challenge.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.link_challenge.enums.Currency;
import demo.link_challenge.enums.TransactionStatus;
import demo.link_challenge.exceptions.InvalidTransactionException;
import demo.link_challenge.exceptions.TransactionFailedException;
import demo.link_challenge.mappers.BankTransferMapper;
import demo.link_challenge.mappers.CardPaymentMapper;
import demo.link_challenge.mappers.P2PTransferMapper;
import demo.link_challenge.repository.TransactionRepository;
import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.models.TransactionModel;
import demo.link_challenge.strategies.TransactionContext;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TransactionService implements ITransactionService{

    private final TransactionRepository transactionRepository;
    private final IIdempotencyService idempotencyService;
    private final ICurrencyService currencyService;
    private final TransactionContext transactionContext;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public TransactionService(TransactionRepository transactionRepository, IIdempotencyService idempotencyService, ICurrencyService currencyService, RedisTemplate<String, String> redisTemplate, TransactionContext transactionContext, RedisTemplate<String, String> redisTemplate1, ObjectMapper objectMapper) {
        this.transactionRepository = transactionRepository;
        this.idempotencyService = idempotencyService;
        this.currencyService = currencyService;
        this.transactionContext = transactionContext;
        this.redisTemplate = redisTemplate1;
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
        transaction.setCreatedAt(LocalDateTime.now());

        try {
            TransactionModel savedTransaction = transactionRepository.save(transaction);
            return transactionContext.executeStrategyDTO(savedTransaction.getType().toLowerCase(), savedTransaction);
        } catch (Exception ex) {
            ex.printStackTrace();
            handleFailedTransaction(transactionDTO, ex);
            throw new TransactionFailedException("Error processing transaction", ex);
        }

    }

    @Override
    @Cacheable(value = "transactions", key = "#id")
    public TransactionDTO getTransaction(UUID id) {
        System.out.println("llego acá al service");
        System.out.println(id);
        try {
            TransactionModel transaction= transactionRepository.findByTransactionId(id);
            TransactionDTO transactionDTO = transactionContext.executeStrategyDTO(transaction.getType().toLowerCase(), transaction);
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(transactionDTO));
            printTransactionDetails(transactionDTO);
            return transactionDTO;
        } catch (Exception ex){
            ex.printStackTrace();
            throw new TransactionFailedException("Error find transaction", ex);
        }
    }

    @Override
    public Page<TransactionDTO> getAllTransactions(
            String userId, String type, String status, Pageable pageable) {

        Specification<TransactionModel> spec = Specification.where(null);
        if (userId != null) spec = spec.and((root, query, cb) -> cb.equal(root.get("userId"), userId));
        if (type != null) spec = spec.and((root, query, cb) -> cb.equal(root.get("type"), type));
        if (status != null) spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));

        try {
            System.out.println("Filtros aplicados - userId: " + userId + ", type: " + type + ", status: " + status);

            System.out.println("Pageable - page: " + pageable.getPageNumber() + ", size: " + pageable.getPageSize() + ", sort: " + pageable.getSort());

            Page<TransactionModel> transactionPage = transactionRepository.findAll(spec, pageable);

            Page<TransactionDTO> listTransactionDTO = transactionPage.map(transaction ->
                transactionContext.executeStrategyDTO(transaction.getType().toLowerCase(), transaction));

            return listTransactionDTO;
        } catch (Exception ex){
            ex.printStackTrace();
            throw new TransactionFailedException("Error find transaction", ex);
        }
    }

    public Page<TransactionDTO> getTransactionsByUserId(String userId, Pageable pageable) {
        Page<TransactionModel> transactionPage = transactionRepository.findByUserId(userId, pageable);

        Page<TransactionDTO> listTransactionDTO = transactionPage.map(transaction -> {
            return transactionContext.executeStrategyDTO(transaction.getType().toLowerCase(), transaction);
        });

        return listTransactionDTO;
    }

    public Page<TransactionDTO> getTransactionsByStatus(String status, Pageable pageable) {
        Page<TransactionModel> transactionPage = transactionRepository.findByStatus(status, pageable);

        Page<TransactionDTO> listTransactionDTO = transactionPage.map(transaction -> {
            return transactionContext.executeStrategyDTO(transaction.getType().toLowerCase(), transaction);
        });

        return listTransactionDTO;
    }

    public List<TransactionDTO> getTransactionsByDateRange(LocalDateTime start, LocalDateTime end) {

        List<TransactionModel> transactionPage = transactionRepository.findByCreatedAtBetween(start, end);

        List<TransactionDTO> listTransactionDTO = transactionPage.stream()
                .map(transaction -> transactionContext.executeStrategyDTO(transaction.getType().toLowerCase(), transaction))
                .toList();

        return listTransactionDTO;
    }

    public void retryTransaction(TransactionDTO dto) {
        TransactionModel transaction = transactionContext.executeStrategy(dto.getType(), dto);
        transaction.setStatus(TransactionStatus.RETRYING);
        transactionRepository.save(transaction);
        //paymentGateway.process(transaction);
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

    private void handleFailedTransaction(TransactionDTO dto, Exception ex) {
        try {
            String transactionId = dto.getTransactionId() != null ? dto.getTransactionId().toString() : "unknown";
            String jsonPayload = objectMapper.writeValueAsString(dto);
            redisTemplate.opsForValue().set("failed:" + transactionId, jsonPayload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize transaction", e);
        }
    }

    public void printTransactionDetails(TransactionDTO transactionDTO) {
        if (transactionDTO == null) {
            System.out.println("transactionDTO es nulo");
            return;
        }

        Class<?> clazz = transactionDTO.getClass();
        System.out.println("Clase: " + clazz.getSimpleName());

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(transactionDTO);
                    System.out.println(field.getName() + ": " + value);
                } catch (IllegalAccessException e) {
                    System.out.println(field.getName() + ": No se pudo acceder");
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}
