package demo.link_challenge.mappers;

import demo.link_challenge.dtos.BankTransferDTO;
import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.models.BankTransfer;
import demo.link_challenge.models.TransactionModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TransactionMapper {

    TransactionDTO toDto(TransactionModel transaction);

    TransactionModel toEntity(TransactionDTO dto);

    @Mapping(source = "bankCode", target = "bankCode")
    @Mapping(source = "recipientAccount", target = "recipientAccount")
    BankTransferDTO toBankTransferDto(BankTransfer transaction);

    @Mapping(target = "type", constant = "BankTransfer")
    BankTransfer toBankTransfer(BankTransferDTO dto);
}
