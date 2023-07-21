package com.bank.bankservice.account.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(source = "id", target = "accountId")
    @Mapping(source = "customer.id", target = "customerId")
    AccountResponse accountEntityToAccountResponse(Account entity);


}
