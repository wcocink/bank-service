package com.bank.bankservice.customer.entity;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper MAPPER = Mappers.getMapper(CustomerMapper.class);

    CustomerResponse customerEntityToCustomerResponse(Customer entity);

    Customer customerRequestToCustomerEntity(CustomerRequest customerRequest);

}
