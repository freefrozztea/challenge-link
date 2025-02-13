package demo.link_challenge.mappers;

import demo.link_challenge.dtos.CardPaymentDTO;
import demo.link_challenge.dtos.MerchantDTO;
import demo.link_challenge.models.CardPayment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", uses = TransactionMapper.class)
public interface CardPaymentMapper {
    @Mapping(target = "merchant", source = ".")
    @Mapping(target = "paymentId", source = "transactionId")
    @Mapping(target = "createdAt", expression = "java(formatDate(cardPayment.getCreatedAt()))")
    CardPaymentDTO toDto(CardPayment cardPayment);

    @Mapping(target = "merchantName", source = "merchant.name")
    @Mapping(target = "merchantId", source = "merchant.merchantId")
    @Mapping(target = "createdAt", expression = "java(parseDate(cardPaymentDTO.getCreatedAt()))")
    CardPayment toEntity(CardPaymentDTO cardPaymentDTO);

    default MerchantDTO mapMerchant(CardPayment cardPayment) {
        if (cardPayment == null) {
            return null;
        }
        return new MerchantDTO(cardPayment.getMerchantName(), cardPayment.getMerchantId());
    }

    default String formatDate(LocalDateTime date) {
        return date != null ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }

    default LocalDateTime parseDate(String date) {
        return date != null ? LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }
}
