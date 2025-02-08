package demo.link_challenge.mappers;

import demo.link_challenge.dtos.BankTransferDTO;
import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.models.BankTransfer;
import demo.link_challenge.models.TransactionModel;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @InheritInverseConfiguration
    TransactionDTO toDto(TransactionModel transaction);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "id", ignore = true)
    TransactionModel toEntity(TransactionDTO dto);

    default void mapCommonFields(TransactionDTO dto, @MappingTarget TransactionModel entity) {
        entity.setTransactionId(dto.getTransactionId());
        entity.setAmount(dto.getAmount().doubleValue());
        entity.setCurrency(dto.getCurrency());
    }
}
