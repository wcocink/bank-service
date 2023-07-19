package com.bank.bankservice.transaction.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionExceptionEnum {

    NOT_ENOUGH_BALANCE("Your balance is not enough to complete this transaction.");

    private final String message;


}
