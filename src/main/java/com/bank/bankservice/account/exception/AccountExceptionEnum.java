package com.bank.bankservice.account.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountExceptionEnum {

    INVALID_ACCOUNT("Account not found.");

    private final String message;

}
