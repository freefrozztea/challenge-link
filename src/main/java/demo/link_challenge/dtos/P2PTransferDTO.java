package demo.link_challenge.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

public class P2PTransferDTO extends TransactionDTO implements Serializable {

    @NotNull(message = "Recipient ID cannot be null")
    private String recipientId;

    private String note;

    public @NotNull(message = "Recipient ID cannot be null") String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(@NotNull(message = "Recipient ID cannot be null") String recipientId) {
        this.recipientId = recipientId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String getType() {
        return "p2p";
    }

    @JsonProperty("transfer_id")
    public UUID getTransferId() {
        return super.getTransactionId();
    }

    public void setTransferId(UUID transferId) {
        super.setTransactionId(transferId);
    }

    @NotNull(message = "User's id cannot be null")
    @JsonProperty("sender_id")
    public String getSenderId(){
        return super.getUserId();
    }

    public void setSenderId(String senderId) {
        super.setUserId(senderId);
    }

}
