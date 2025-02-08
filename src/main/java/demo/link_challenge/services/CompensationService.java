package demo.link_challenge.services;

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

    public CompensationService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Scheduled(fixedDelay = 30000) // Reintentar cada 30 segundos
    public void retryFailedTransactions() {
        Set<String> keys = redisTemplate.keys("failed:*");
        for (String key : keys) {
            String json = redisTemplate.opsForValue().get(key);
            TransactionDTO dto = deserialize(json);
            transactionService.retryTransaction(dto);
            redisTemplate.delete(key);
        }
    }

    public void handleFailure(TransactionModel transaction) {
        redisTemplate.opsForValue().set(
                "failed:" + transaction.getTransactionId(),
                serialize(transaction),
                Duration.ofHours(24)
        );
    }
}
