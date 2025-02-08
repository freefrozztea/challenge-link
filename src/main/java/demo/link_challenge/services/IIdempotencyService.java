package demo.link_challenge.services;

import java.util.UUID;

public interface IIdempotencyService {
    void checkIdempotency(UUID transactionId);
}
