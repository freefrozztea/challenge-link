package demo.link_challenge.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Long id;
    private Double amount;
    private String currency;
    private String status;
    private String type;
    private String cardId;
    private String merchantName;
    private String merchantId;
    private int mccCode;
    private String bankCode;
    private String recipientAccount;
    private String senderId;
    private String recipientId;
    private String note;
}
