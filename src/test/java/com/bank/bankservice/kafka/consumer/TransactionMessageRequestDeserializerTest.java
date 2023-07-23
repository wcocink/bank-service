package com.bank.bankservice.kafka.consumer;

import com.bank.bankservice.kafka.consumer.entity.TransactionMessageRequestDeserializer;
import com.bank.bankservice.kafka.producer.entity.TransactionMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.errors.SerializationException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class TransactionMessageRequestDeserializerTest {

    @Test
    public void givenValidRequestMessage_ThenDeserialize() throws IOException {
        TransactionMessageRequest transactionMessageRequest = new TransactionMessageRequest();
        transactionMessageRequest.setTransactionType("DEPOSIT");
        transactionMessageRequest.setValue(new BigDecimal("1"));

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] data = objectMapper.writeValueAsBytes(transactionMessageRequest);

        TransactionMessageRequestDeserializer tmrd = new TransactionMessageRequestDeserializer();
        TransactionMessageRequest transactionMessage = tmrd.deserialize("test", data);

        Assert.assertEquals("DEPOSIT", transactionMessage.getTransactionType());
        Assert.assertEquals(new BigDecimal("1"), transactionMessage.getValue());

    }

    @Test
    public void givenNoDataRequestMessage_ThenReturnNull() throws IOException {
        TransactionMessageRequestDeserializer tmrd = new TransactionMessageRequestDeserializer();
        var transactionMessageRequest = tmrd.deserialize("test", null);
        Assert.assertEquals(transactionMessageRequest, null);
    }

    @Test
    public void givenInvalidRequestMessage_ThenThrowError() throws IOException {
        TransactionMessageRequest transactionMessageRequest = new TransactionMessageRequest();

        byte[] data = SerializationUtils.serialize(transactionMessageRequest);

        TransactionMessageRequestDeserializer tmrd = new TransactionMessageRequestDeserializer();

        Assert.assertThrows(SerializationException.class, () -> {
            tmrd.deserialize("test", data);
        });

    }

}
