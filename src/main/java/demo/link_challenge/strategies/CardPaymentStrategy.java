package demo.link_challenge.strategies;

import demo.link_challenge.dtos.CardPaymentDTO;
import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.exceptions.InvalidTransactionException;
import demo.link_challenge.mappers.CardPaymentMapper;
import demo.link_challenge.models.CardPayment;
import demo.link_challenge.models.TransactionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardPaymentStrategy implements ITransactionStrategy {

    private final CardPaymentMapper cardPaymentMapper;

    @Autowired
    public CardPaymentStrategy(CardPaymentMapper cardPaymentMapper) {
        this.cardPaymentMapper = cardPaymentMapper;
    }

    @Override
    public TransactionModel process(TransactionDTO transactionDTO) {
        CardPaymentDTO cardPaymentDTO = (CardPaymentDTO) transactionDTO;

        if (cardPaymentDTO.getMccCode() <= 0) {
            throw new InvalidTransactionException("Invalid MCC Code", "INVALID_MCC");
        }

        CardPayment cardPayment = cardPaymentMapper.toEntity(cardPaymentDTO);

        return cardPayment;
    }

    @Override
    public TransactionDTO convertToDTO(TransactionModel entity) {
        return cardPaymentMapper.toDto((CardPayment) entity);
    }
}
