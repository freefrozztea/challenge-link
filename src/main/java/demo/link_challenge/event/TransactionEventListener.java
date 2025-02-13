package demo.link_challenge.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionEventListener {
    //@KafkaListener(topics = "transaction-events", groupId = "transaction-group")
    //public void handleTransactionEvent(TransactionEvent event) {
        //System.out.println("Received transaction event: " + event.getTransactionId() + " with status: " + event.getStatus());
    //}
}
