package com.bank.bankservice.kafka.producer;

import com.bank.bankservice.account.entity.Account;
import com.bank.bankservice.kafka.producer.control.TransactionProducerController;
import com.bank.bankservice.kafka.producer.entity.TransactionMessageRequest;
import com.bank.bankservice.utils.AbstractIntegrationTests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TransactionProducerControllerTest extends AbstractIntegrationTests {

    @MockBean
    TransactionProducerController transactionProducerController;

    @MockBean
    KafkaTemplate kafkaTemplate;

    @MockBean
    KafkaAdmin kafkaAdmin;

    @Test
    public void givenOperation_WhenDoingAnOperation_ThenSendMessage(){
        TransactionMessageRequest transactionMessageRequest = new TransactionMessageRequest();
        transactionMessageRequest.setTransactionType("DEPOSIT");
        transactionMessageRequest.setAccount(new Account());
        transactionMessageRequest.setValue(new BigDecimal("1"));
        transactionProducerController.sendMessage(transactionMessageRequest);
    }

}
