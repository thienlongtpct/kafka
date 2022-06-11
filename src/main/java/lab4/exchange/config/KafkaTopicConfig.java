package lab4.exchange.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {
    @Value("${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configs.put(AdminClientConfig.CLIENT_ID_CONFIG, "local-test");
        configs.put(AdminClientConfig.RETRIES_CONFIG, "3");
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicExchange() {
        return new NewTopic("exchange-all", 10, (short) 1);
    }

    @Bean
    public NewTopic topicExchangeFromRub() {
        return new NewTopic("exchange-rub", 2, (short) 1);
    }

    @Bean
    public NewTopic topicExchangeFromEur() {
        return new NewTopic("exchange-eur", 2, (short) 1);
    }

    @Bean
    public NewTopic topicExchangeFromUSD() {
        return new NewTopic("exchange-usd", 2, (short) 1);
    }
}
