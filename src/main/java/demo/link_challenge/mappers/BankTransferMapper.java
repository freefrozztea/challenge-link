package demo.link_challenge.mappers;

import demo.link_challenge.dtos.BankTransferDTO;
import demo.link_challenge.models.BankTransfer;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TransactionMapper.class)
public interface BankTransferMapper {

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "id", ignore = true)
    BankTransfer toEntity(BankTransferDTO dto);

    @InheritInverseConfiguration
    BankTransferDTO toDto(BankTransfer entity);

    default BankTransfer map(BankTransferDTO dto) {
        BankTransfer entity = new BankTransfer();
        TransactionMapper.super.mapCommonFields(dto, entity);

        entity.setBankCode(dto.getBankCode());
        entity.setRecipientAccount(dto.getRecipientAccount());
        return entity;
    }
}