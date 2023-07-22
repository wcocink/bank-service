package com.bank.bankservice.transaction.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {

    private String id;

    private String transactionType;

    private LocalDateTime date;

    private BigDecimal value;

}
