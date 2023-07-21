package com.bank.bankservice.kafka.producer.config;

import com.bank.bankservice.kafka.producer.control.TransactionMessageRequestSerializer;
import com.bank.bankservice.kafka.producer.entity.TransactionMessageRequest;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class TransactionProducerFactoryConfig {

    private final KafkaProperties properties;

    @Bean
    public ProducerFactory<String, TransactionMessageRequest> producerFactory() {
        var configs = new HashMap<String, Object>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, TransactionMessageRequestSerializer.class);
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<String, TransactionMessageRequest> kafkaTemplate(ProducerFactory<String, TransactionMessageRequest> producerFactory){
        return new KafkaTemplate<>(producerFactory);
    }

}
