package demo.link_challenge.mappers;

import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.models.BankTransfer;
import demo.link_challenge.models.CardPayment;
import demo.link_challenge.models.P2PTransfer;
import demo.link_challenge.models.TransactionModel;

public class TransactionMapper {
    public static TransactionDTO toDto(TransactionModel transaction) {
        TransactionDTO dto = new TransactionDTO();
        //dto.setTransactionId(transaction.getTransactionId());
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setCurrency(transaction.getCurrency());
        dto.setStatus(transaction.getStatus());
        dto.setType(transaction.getClass().getSimpleName());

        if (transaction instanceof CardPayment cardPayment) {
            dto.setCardId(cardPayment.getCardId());
            dto.setMerchantName(cardPayment.getMerchantName());
            dto.setMerchantId(cardPayment.getMerchantId());
            dto.setMccCode(cardPayment.getMccCode());
        } else if (transaction instanceof BankTransfer bankTransfer) {
            dto.setBankCode(bankTransfer.getBankCode());
            dto.setRecipientAccount(bankTransfer.getRecipientAccount());
        } else if (transaction instanceof P2PTransfer p2pTransfer) {
            dto.setSenderId(p2pTransfer.getSenderId());
            dto.setRecipientId(p2pTransfer.getRecipientId());
            dto.setNote(p2pTransfer.getNote());
        }
        return dto;
    }

    public static TransactionModel toEntity(TransactionDTO dto) {
        switch (dto.getType()) {
            case "CardPayment":
                return new CardPayment(dto.getId(), dto.getAmount(), dto.getCurrency(), dto.getStatus(),
                        dto.getCardId(), dto.getMerchantName(), dto.getMerchantId(), dto.getMccCode());
            case "BankTransfer":
                return new BankTransfer(dto.getId(), dto.getAmount(), dto.getCurrency(), dto.getStatus(),
                        dto.getBankCode(), dto.getRecipientAccount());
            case "P2PTransfer":
                return new P2PTransfer(dto.getId(), dto.getAmount(), dto.getCurrency(), dto.getStatus(),
                        dto.getSenderId(), dto.getRecipientId(), dto.getNote());
            default:
                throw new IllegalArgumentException("Unknown transaction type: " + dto.getType());
        }
    }
}
