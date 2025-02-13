package demo.link_challenge.mappers;

import demo.link_challenge.dtos.TransactionDTO;
import demo.link_challenge.enums.TransactionStatus;
import demo.link_challenge.factory.TransactionFactory;
import demo.link_challenge.models.TransactionModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @InheritInverseConfiguration
    default TransactionDTO toDto(TransactionModel transaction, @Context TransactionFactory factory) {
        TransactionDTO dto = factory.createTransactionDTO(transaction);
        mapCommonFieldsToDto(transaction, dto);
        return dto;
    }

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "id", ignore = true)
    default TransactionModel toEntity(TransactionDTO dto, @Context TransactionFactory factory) {
        TransactionModel entity = factory.createTransaction(dto);
        mapCommonFields(dto, entity);
        return entity;
    }

    @AfterMapping
    default void mapCommonFields(TransactionDTO dto, @MappingTarget TransactionModel entity) {
        entity.setTransactionId(dto.getTransactionId());
        entity.setAmount(dto.getAmount());
        entity.setCurrency(dto.getCurrency());
        entity.setStatus(mapStatusToEnum(dto.getStatus()));
    }

    @AfterMapping
    default void mapCommonFieldsToDto(TransactionModel entity, @MappingTarget TransactionDTO dto) {
        dto.setAmount(entity.getAmount());
        dto.setCurrency(entity.getCurrency());
        dto.setStatus(mapStatusToString(entity.getStatus()));
    }

    default String mapStatusToString(TransactionStatus status) {
        return status != null ? status.name() : null;
    }

    default TransactionStatus mapStatusToEnum(String status) {
        return status != null ? TransactionStatus.valueOf(status) : null;
    }
}
