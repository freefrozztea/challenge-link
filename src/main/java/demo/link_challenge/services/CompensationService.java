package demo.link_challenge.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.models.TransactionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;

@Service
public class CompensationService implements ICompensationService{

    private RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final TransactionService transactionService;

    public CompensationService(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper, TransactionService transactionService) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.transactionService = transactionService;
    }

    @Scheduled(fixedDelay = 30000) // Reintentar cada 30 segundos
    public void retryFailedTransactions() {
        Set<String> keys = redisTemplate.keys("failed:*");
        for (String key : keys) {
            try {
                String json = redisTemplate.opsForValue().get(key);
                TransactionDTO dto = deserialize(json);
                transactionService.retryTransaction(dto);
                redisTemplate.delete(key);
            } catch (Exception ex) {
                // Loggear error sin interrumpir el ciclo
            }
        }
    }

    public void handleFailure(TransactionModel transaction) {
        try {
            String json = serialize(transaction);
            redisTemplate.opsForValue().set(
                    "failed:" + transaction.getTransactionId(),
                    json,
                    Duration.ofHours(24)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing transaction", e);
        }
    }

    private String serialize(TransactionModel transaction) throws JsonProcessingException {
        return objectMapper.writeValueAsString(transaction);
    }

    private TransactionDTO deserialize(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, TransactionDTO.class);
    }
}
