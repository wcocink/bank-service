package com.bank.bankservice.kafka.producer;

import com.bank.bankservice.account.entity.Account;
import com.bank.bankservice.kafka.producer.control.TransactionProducerController;
import com.bank.bankservice.kafka.producer.entity.TransactionMessageRequest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

@SpringBootTest
public class TransactionProducerControllerTest {

    @MockBean
    TransactionProducerController transactionProducerController;

    @Test
    public void givenOperation_WhenDoingAnOperation_ThenSendMessage(){
        TransactionMessageRequest transactionMessageRequest = new TransactionMessageRequest();
        transactionMessageRequest.setTransactionType("DEPOSIT");
        transactionMessageRequest.setAccount(new Account());
        transactionMessageRequest.setValue(new BigDecimal("1"));
        transactionProducerController.sendMessage(transactionMessageRequest);
    }

}
