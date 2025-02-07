package demo.link_challenge.mappers;

import demo.link_challenge.dtos.CardPaymentDTO;
import demo.link_challenge.models.CardPayment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TransactionMapper.class)
public interface CardPaymentMapper {
    CardPaymentDTO toDto(CardPayment cardPayment);

    @Mapping(target = "status", ignore = true)
    CardPayment toEntity(CardPaymentDTO cardPaymentDTO);
}
