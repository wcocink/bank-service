package com.bank.bankservice.customer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomerExceptionEnum {

    INVALID_CUSTOMER("Customer not found.");

    private final String message;

}
