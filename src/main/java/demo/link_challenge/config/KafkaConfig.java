package demo.link_challenge.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    // @Bean
    // public NewTopic transactionTopic() {
    //     return new NewTopic("transaction-events", 1, (short) 1);
    // }

    // @Bean
    // public KafkaTemplate<String, String> kafkaTemplate() {
    //     return new KafkaTemplate<>(producerFactory());
    // }

    // @Bean
    // public ConsumerFactory<String, String> consumerFactory() {
    //     return new DefaultKafkaConsumerFactory<>(consumerConfig());
    // }

    // @Bean
    // public ConcurrentMessageListenerContainer<String, String> messageListenerContainer() {
    //     return new ConcurrentMessageListenerContainer<>(consumerFactory());
    // }
}
