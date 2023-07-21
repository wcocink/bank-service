package com.bank.bankservice.kafka.consumer.control;

import com.bank.bankservice.kafka.consumer.boundary.TransactionConsumerListener;
import com.bank.bankservice.kafka.producer.entity.TransactionMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class TransactionConsumerController {

    @TransactionConsumerListener(groupId = "group-1")
    @SneakyThrows
    public void create(TransactionMessageRequest message){
        System.out.println("received message: " + message);
        throw new IllegalArgumentException("Exception");
    }
}
