package com.bank.bankservice.transaction.exception;

public class TransactionNotEnoughBalanceException extends TransactionException{

    protected TransactionNotEnoughBalanceException(TransactionExceptionEnum transactionExceptionEnum){
        super(transactionExceptionEnum.getMessage());
    }

    public static TransactionNotEnoughBalanceException notEnoughBalance(){
        return new TransactionNotEnoughBalanceException(TransactionExceptionEnum.NOT_ENOUGH_BALANCE);
    }

}
