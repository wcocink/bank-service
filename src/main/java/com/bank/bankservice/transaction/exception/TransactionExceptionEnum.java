package com.bank.bankservice.transaction.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionExceptionEnum {

    NOT_ENOUGH_BALANCE("Your balance is not enough to complete this transaction."),
    NOT_FOUND("The transaction was not found."),
    INVALID_DATE("The end date must be after start date.");

    private final String message;

}
