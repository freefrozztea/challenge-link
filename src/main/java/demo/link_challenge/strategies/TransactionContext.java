package demo.link_challenge.strategies;

import demo.link_challenge.annotations.TransactionTypeQualifier;
import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.exceptions.UnsupportedTransactionTypeException;
import demo.link_challenge.models.TransactionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TransactionContext {
    private final Map<String, ITransactionStrategy> strategies = new HashMap<>();

    @Autowired
    public TransactionContext(List<ITransactionStrategy> strategyList) {
        Map<String, ITransactionStrategy> tempMap = strategyList.stream()
                .collect(Collectors.toMap(
                        s -> s.getClass().getAnnotation(TransactionTypeQualifier.class).value(),
                        Function.identity()
                ));

        this.strategies.putAll(tempMap);
    }

    public TransactionModel executeStrategy(String type, TransactionDTO dto) {
        ITransactionStrategy strategy = strategies.get(type);
        if (strategy == null) throw new UnsupportedTransactionTypeException(type);
        return strategy.process(dto);
    }
}
