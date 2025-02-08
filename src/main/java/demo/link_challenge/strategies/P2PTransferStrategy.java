package demo.link_challenge.strategies;

import demo.link_challenge.dtos.P2PTransferDTO;
import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.exceptions.InvalidTransactionException;
import demo.link_challenge.mappers.P2PTransferMapper;
import demo.link_challenge.models.P2PTransfer;
import demo.link_challenge.models.TransactionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class P2PTransferStrategy implements ITransactionStrategy {
    private final P2PTransferMapper p2pTransferMapper;

    @Autowired
    public P2PTransferStrategy(P2PTransferMapper p2pTransferMapper) {
        this.p2pTransferMapper = p2pTransferMapper;
    }

    @Override
    public TransactionModel process(TransactionDTO transactionDTO) {
        P2PTransferDTO p2pTransferDTO = (P2PTransferDTO) transactionDTO;

        if (p2pTransferDTO.getSenderId() == null || p2pTransferDTO.getRecipientId() == null) {
            throw new InvalidTransactionException("Sender and recipient cannot be empty", "INVALID_P2P_TRANSFER");
        }

        P2PTransfer p2pTransfer = p2pTransferMapper.toEntity(p2pTransferDTO);

        return p2pTransfer;
    }

    @Override
    public TransactionDTO convertToDTO(TransactionModel entity) {
        return p2pTransferMapper.toDto((P2PTransfer) entity);
    }
}
