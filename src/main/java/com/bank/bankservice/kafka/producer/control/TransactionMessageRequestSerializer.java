package com.bank.bankservice.kafka.producer.control;

import com.bank.bankservice.kafka.producer.entity.TransactionMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@Log4j2
public class TransactionMessageRequestSerializer implements Serializer<TransactionMessageRequest> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, TransactionMessageRequest transactionMessageRequest) {
        try {
            if (transactionMessageRequest == null){
                return null;
            }
            return objectMapper.writeValueAsBytes(transactionMessageRequest);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing TransactionMessageRequest to byte[]");
        }
    }

    @Override
    public void close() {
    }
}
