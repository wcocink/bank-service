package com.bank.bankservice.kafka.consumer;

import com.bank.bankservice.account.entity.Account;
import com.bank.bankservice.kafka.consumer.control.TransactionConsumerController;
import com.bank.bankservice.kafka.producer.entity.TransactionMessageRequest;
import com.bank.bankservice.transaction.control.TransactionRepository;
import com.bank.bankservice.transaction.entity.Transaction;
import com.bank.bankservice.transaction.entity.TransactionMapper;
import com.bank.bankservice.utils.AbstractIntegrationTests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TransactionConsumerControllerTest extends AbstractIntegrationTests {

    @Autowired
    TransactionConsumerController transactionConsumerController;

    @MockBean
    TransactionMapper transactionMapper;

    @MockBean
    TransactionRepository transactionRepository;

    @MockBean
    KafkaTemplate kafkaTemplate;

    @MockBean
    KafkaAdmin kafkaAdmin;

    @Test
    public void givenMessage_WhenReceivingMessage_ThenConsumeMessage(){
        TransactionMessageRequest transactionMessageRequest = new TransactionMessageRequest();
        transactionMessageRequest.setTransactionType("DEPOSIT");
        transactionMessageRequest.setAccount(new Account());
        transactionMessageRequest.setValue(new BigDecimal("1"));

        when(transactionMapper.transactionMessageRequestToTransactionEntity(any())).thenReturn(new Transaction());
        when(transactionRepository.save(any())).thenReturn(new Transaction());

        transactionConsumerController.getMessage(transactionMessageRequest);

    }
}
