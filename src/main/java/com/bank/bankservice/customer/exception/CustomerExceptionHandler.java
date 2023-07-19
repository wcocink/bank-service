package com.bank.bankservice.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomerException.class)
    public Map<String, String> handleException(CustomerException ex){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", ex.getMessage());
        return errorMap;
    }

}
