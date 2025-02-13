package demo.link_challenge.mappers;

import demo.link_challenge.dtos.BankTransferDTO;
import demo.link_challenge.models.BankTransfer;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = TransactionMapper.class)
public interface BankTransferMapper {

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "id", ignore = true)
    BankTransfer toEntity(BankTransferDTO dto);

    @InheritInverseConfiguration
    BankTransferDTO toDto(BankTransfer entity);

}