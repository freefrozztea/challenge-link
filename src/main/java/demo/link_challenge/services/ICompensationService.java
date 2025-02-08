package demo.link_challenge.services;

import demo.link_challenge.models.TransactionModel;

public interface ICompensationService {
    void handleFailure(TransactionModel transaction);
    void retryFailedTransactions();
}
