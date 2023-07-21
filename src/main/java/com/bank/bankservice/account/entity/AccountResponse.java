package com.bank.bankservice.account.entity;

import com.bank.bankservice.customer.entity.CustomerResponse;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponse {

    private Long accountId;

    private BigDecimal balance;

    private Long customerId;

    private CustomerResponse customer;

}
