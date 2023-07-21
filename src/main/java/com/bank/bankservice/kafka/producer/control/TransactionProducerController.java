package com.bank.bankservice.kafka.producer.control;

import com.bank.bankservice.kafka.producer.entity.TransactionMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionProducerController {

    private final KafkaTemplate<String, TransactionMessageRequest> kafkaTemplate;

    public void sendMessage(TransactionMessageRequest transactionMessageRequest) {
        kafkaTemplate.send("transaction-topic", transactionMessageRequest);
    }
}
