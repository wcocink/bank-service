package com.bank.bankservice.transaction.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    List<TransactionResponse> transactionEntityListToTransactionResponseList(List<Transaction> transactionList);

    @Mapping(source = "account.balance", target = "currentBalance")
    TransactionResponse transactionEntityToTransactionResponse(Transaction transaction);

}
