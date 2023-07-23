package com.bank.bankservice.kafka.producer;

import com.bank.bankservice.kafka.producer.control.TransactionMessageRequestSerializer;
import com.bank.bankservice.kafka.producer.entity.TransactionMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TransactionMessageRequestSerializerTest {

    @Test
    public void givenValidRequestMessage_ThenSerialize() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        TransactionMessageRequest transactionMessageRequest = new TransactionMessageRequest();
        transactionMessageRequest.setTransactionType("DEPOSIT");
        transactionMessageRequest.setValue(new BigDecimal("1"));

        TransactionMessageRequestSerializer tmrs = new TransactionMessageRequestSerializer();
        byte[] byteArray = tmrs.serialize("test", transactionMessageRequest);

        TransactionMessageRequest messageRequest = objectMapper.readValue(new String(byteArray, "UTF-8"), TransactionMessageRequest.class);

        Assert.assertEquals("DEPOSIT", messageRequest.getTransactionType());
        Assert.assertEquals(new BigDecimal("1"), messageRequest.getValue());

    }

    @Test
    public void givenNoDataRequestMessage_ThenReturnNull() {
        TransactionMessageRequestSerializer tmrs = new TransactionMessageRequestSerializer();
        byte[] byteArray = tmrs.serialize("test", null);
        Assert.assertEquals(byteArray, null);
    }

}
