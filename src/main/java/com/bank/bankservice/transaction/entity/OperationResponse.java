package com.bank.bankservice.transaction.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OperationResponse {

    private BigDecimal balanceAfterTransaction;

}
