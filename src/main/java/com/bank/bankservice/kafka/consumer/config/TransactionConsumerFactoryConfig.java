package com.bank.bankservice.kafka.consumer.config;

import com.bank.bankservice.kafka.consumer.entity.TransactionMessageRequestDeserializer;
import com.bank.bankservice.kafka.producer.control.TransactionMessageRequestSerializer;
import com.bank.bankservice.kafka.producer.entity.TransactionMessageRequest;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
@EnableKafka
public class TransactionConsumerFactoryConfig {

    private final KafkaProperties properties;

    @Bean
    public ConsumerFactory<String, TransactionMessageRequest> consumerFactory() {
        var configs = new HashMap<String, Object>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, TransactionMessageRequestDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configs);
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TransactionMessageRequest> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TransactionMessageRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
