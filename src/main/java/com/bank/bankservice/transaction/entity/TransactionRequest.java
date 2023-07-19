package com.bank.bankservice.transaction.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {

    private BigDecimal amount;

}
