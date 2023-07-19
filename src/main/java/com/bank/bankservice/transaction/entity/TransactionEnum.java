package com.bank.bankservice.transaction.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionEnum {

    DEPOSIT("DEPOSIT"),
    WITHDRAW("WITHDRAW");

    private final String transactionType;

}
