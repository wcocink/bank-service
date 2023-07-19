package com.bank.bankservice.transaction.exception;

public class TransactionException extends RuntimeException{

    protected TransactionException(TransactionExceptionEnum transactionExceptionEnum){
        super(transactionExceptionEnum.getMessage());
    }

    public static TransactionException notEnoughBalance(){
        return new TransactionException(TransactionExceptionEnum.NOT_ENOUGH_BALANCE);
    }

}
