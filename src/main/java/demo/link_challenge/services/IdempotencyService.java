package demo.link_challenge.services;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class IdempotencyService implements IIdempotencyService{
    private final StringRedisTemplate redisTemplate;

    public IdempotencyService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean checkAndStore(String transactionId) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        Boolean isNew = ops.setIfAbsent(transactionId, "processed", 10, TimeUnit.MINUTES);
        return Boolean.TRUE.equals(isNew);
    }
}
