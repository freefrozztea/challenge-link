package demo.link_challenge.strategies;

import demo.link_challenge.dtos.P2PTransferDTO;
import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.models.P2PTransfer;
import demo.link_challenge.models.TransactionModel;
import org.springframework.stereotype.Component;

@Component
public class P2PTransferStrategy implements ITransactionStrategy {
    @Override
    public TransactionModel process(TransactionDTO transactionDTO) {
        P2PTransferDTO p2pTransferDTO = (P2PTransferDTO) transactionDTO;
        // Lógica específica para transferencias P2P
        P2PTransfer p2pTransfer = new P2PTransfer();
        p2pTransfer.setAmount(p2pTransferDTO.getAmount());
        p2pTransfer.setCurrency(p2pTransferDTO.getCurrency());
        p2pTransfer.setSenderId(p2pTransferDTO.getSenderId());
        p2pTransfer.setRecipientId(p2pTransferDTO.getRecipientId());
        p2pTransfer.setNote(p2pTransferDTO.getNote());
        return p2pTransfer;
    }
}
