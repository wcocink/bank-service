package com.bank.bankservice.transaction.entity;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    List<TransactionResponse> transactionEntityListToTransactionResponseList(List<Transaction> transactionList);

}
