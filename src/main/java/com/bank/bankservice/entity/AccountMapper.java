package com.bank.bankservice.entity;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper MAPPER = Mappers.getMapper(AccountMapper.class);

    CustomerResponse customerEntityToCustomerResponse(Customer entity);

    Customer customerRequestToCustomerEntity(CustomerRequest customerRequest);

}
