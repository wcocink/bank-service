package com.bank.bankservice.transaction.exception;

public class TransactionNotFoundException extends TransactionException{

    protected TransactionNotFoundException(TransactionExceptionEnum transactionExceptionEnum){
        super(transactionExceptionEnum.getMessage());
    }

    public static TransactionNotFoundException transactionNotFound(){
        return new TransactionNotFoundException(TransactionExceptionEnum.NOT_FOUND);
    }

}
