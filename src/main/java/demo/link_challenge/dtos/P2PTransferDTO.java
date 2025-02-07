package demo.link_challenge.dtos;

import jakarta.validation.constraints.NotNull;

public class P2PTransferDTO extends TransactionDTO{
    @NotNull(message = "Sender ID cannot be null")
    private String senderId;

    @NotNull(message = "Recipient ID cannot be null")
    private String recipientId;

    private String note;

    public @NotNull(message = "Sender ID cannot be null") String getSenderId() {
        return senderId;
    }

    public void setSenderId(@NotNull(message = "Sender ID cannot be null") String senderId) {
        this.senderId = senderId;
    }

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
}
