package com.bank.bankservice.account.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(source = "id", target = "accountId")
    @Mapping(source = "customer", target = "customer")
    AccountResponse accountEntityToAccountResponse(Account entity);


}
