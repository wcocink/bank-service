package com.bank.bankservice.kafka.consumer.control;

import com.bank.bankservice.kafka.consumer.boundary.TransactionConsumerListener;
import com.bank.bankservice.kafka.producer.entity.TransactionMessageRequest;
import com.bank.bankservice.transaction.control.TransactionController;
import com.bank.bankservice.transaction.entity.Transaction;
import com.bank.bankservice.transaction.entity.TransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class TransactionConsumerController {

    @Autowired
    TransactionMapper transactionMapper;

    @Autowired
    TransactionController transactionController;

    @TransactionConsumerListener
    @SneakyThrows
    public void create(TransactionMessageRequest message){
        Transaction transaction = transactionMapper.transactionMessageRequestToTransactionEntity(message);
        transactionController.saveTransaction(transaction);
    }
}
