package com.bank.bankservice.account.entity;

import com.bank.bankservice.customer.entity.Customer;
import com.bank.bankservice.customer.entity.CustomerRequest;
import com.bank.bankservice.customer.entity.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper MAPPER = Mappers.getMapper(AccountMapper.class);

    CustomerResponse customerEntityToCustomerResponse(Customer entity);

    Customer customerRequestToCustomerEntity(CustomerRequest customerRequest);

}
