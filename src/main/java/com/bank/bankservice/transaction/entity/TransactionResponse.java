package com.bank.bankservice.transaction.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionResponse {

    private BigDecimal currentBalance;

}
