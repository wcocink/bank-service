package com.bank.bankservice.kafka.consumer;

import com.bank.bankservice.account.entity.Account;
import com.bank.bankservice.kafka.consumer.control.TransactionConsumerController;
import com.bank.bankservice.kafka.producer.entity.TransactionMessageRequest;
import com.bank.bankservice.transaction.control.TransactionController;
import com.bank.bankservice.transaction.control.TransactionRepository;
import com.bank.bankservice.transaction.entity.Transaction;
import com.bank.bankservice.transaction.entity.TransactionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TransactionConsumerControllerTest {

    @Autowired
    TransactionConsumerController transactionConsumerController;

    @MockBean
    TransactionMapper transactionMapper;

    @MockBean
    TransactionRepository transactionRepository;

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
