package demo.link_challenge.factory;

import demo.link_challenge.dtos.BankTransferDTO;
import demo.link_challenge.dtos.CardPaymentDTO;
import demo.link_challenge.dtos.P2PTransferDTO;
import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.models.BankTransfer;
import demo.link_challenge.models.CardPayment;
import demo.link_challenge.models.P2PTransfer;
import demo.link_challenge.models.TransactionModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class TransactionFactory {
    private final Map<Class<? extends TransactionDTO>, Supplier<TransactionModel>> transactionMap = new HashMap<>();
    private final Map<Class<? extends TransactionModel>, Supplier<TransactionDTO>> dtoMap = new HashMap<>();

    public TransactionFactory() {
        transactionMap.put(BankTransferDTO.class, BankTransfer::new);
        transactionMap.put(CardPaymentDTO.class, CardPayment::new);
        transactionMap.put(P2PTransferDTO.class, P2PTransfer::new);

        dtoMap.put(BankTransfer.class, BankTransferDTO::new);
        dtoMap.put(CardPayment.class, CardPaymentDTO::new);
        dtoMap.put(P2PTransfer.class, P2PTransferDTO::new);
    }

    public TransactionModel createTransaction(TransactionDTO dto) {
        Supplier<TransactionModel> supplier = transactionMap.get(dto.getClass());
        if (supplier != null) {
            return supplier.get();
        }
        throw new IllegalArgumentException("Unknown transaction type: " + dto.getClass());
    }

    public TransactionDTO createTransactionDTO(TransactionModel model) {
        Supplier<TransactionDTO> supplier = dtoMap.get(model.getClass());
        if (supplier != null) {
            return supplier.get();
        }
        throw new IllegalArgumentException("Unknown transaction type: " + model.getClass());
    }
}

