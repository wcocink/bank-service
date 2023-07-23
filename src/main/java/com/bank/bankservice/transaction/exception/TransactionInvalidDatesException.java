package com.bank.bankservice.transaction.exception;

public class TransactionInvalidDatesException extends TransactionException{

    protected TransactionInvalidDatesException(TransactionExceptionEnum transactionExceptionEnum){
        super(transactionExceptionEnum.getMessage());
    }

    public static TransactionInvalidDatesException transactionInvalidDate(){
        return new TransactionInvalidDatesException(TransactionExceptionEnum.INVALID_DATE);
    }

}
