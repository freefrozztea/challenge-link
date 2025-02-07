package demo.link_challenge.strategies;

import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.models.TransactionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TransactionContext {
    private final Map<String, ITransactionStrategy> strategies = new HashMap<>();

    @Autowired
    public TransactionContext(CardPaymentStrategy cardPaymentStrategy,
                              BankTransferStrategy bankTransferStrategy,
                              P2PTransferStrategy p2pTransferStrategy) {
        strategies.put("card", cardPaymentStrategy);
        strategies.put("bank", bankTransferStrategy);
        strategies.put("p2p", p2pTransferStrategy);
    }

    public TransactionModel executeStrategy(String type, TransactionDTO transactionDTO) {
        ITransactionStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("Transaction type not supported: " + type);
        }
        return strategy.process(transactionDTO);
    }
}
