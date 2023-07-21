package com.bank.bankservice.kafka.consumer.entity;

import com.bank.bankservice.kafka.producer.entity.TransactionMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class TransactionMessageRequestDeserializer implements Deserializer<TransactionMessageRequest> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public TransactionMessageRequest deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), TransactionMessageRequest.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
    }

    @Override
    public void close() {
    }
}
