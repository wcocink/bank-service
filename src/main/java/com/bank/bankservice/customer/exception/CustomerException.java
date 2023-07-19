package com.bank.bankservice.customer.exception;

public class CustomerException extends RuntimeException{

    protected CustomerException(CustomerExceptionEnum customerExceptionEnum){
        super(customerExceptionEnum.getMessage());
    }

    public static CustomerException customerNotFound(){
        return new CustomerException(CustomerExceptionEnum.INVALID_CUSTOMER);
    }

}
