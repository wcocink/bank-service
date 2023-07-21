package com.bank.bankservice.transaction.exception;

public class TransactionException extends RuntimeException{

    protected TransactionException(String message){
        super(message);
    }

}
