package demo.link_challenge.services;

public interface IIdempotencyService {
    boolean checkAndStore(String transactionId);
}
