package demo.link_challenge.services;

import demo.link_challenge.exceptions.DuplicateTransactionException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class IdempotencyService implements IIdempotencyService{
    private final StringRedisTemplate redisTemplate;
    private static final String KEY_PREFIX = "idempotency:";
    private static final Duration EXPIRATION = Duration.ofMinutes(30);

    public IdempotencyService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void checkIdempotency(UUID transactionId) {
        String key = KEY_PREFIX + transactionId;
        Boolean isNew = redisTemplate.opsForValue().setIfAbsent(key, "processed", EXPIRATION);

        if (isNew == null || !isNew) {
            throw new DuplicateTransactionException("Transacción duplicada: ", transactionId.toString());
        }
    }
}
