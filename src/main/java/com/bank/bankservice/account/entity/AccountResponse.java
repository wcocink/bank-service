package com.bank.bankservice.account.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponse {

    private Long id;

    private BigDecimal balance;

    private Long customerId;

}