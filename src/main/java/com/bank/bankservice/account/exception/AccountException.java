package com.bank.bankservice.account.exception;

public class AccountException extends RuntimeException{

    protected AccountException(AccountExceptionEnum accountExceptionEnum){
        super(accountExceptionEnum.getMessage());
    }

    public static AccountException accountNotFound(){
        return new AccountException(AccountExceptionEnum.INVALID_ACCOUNT);
    }

}
