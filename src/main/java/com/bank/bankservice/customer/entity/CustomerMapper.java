package com.bank.bankservice.customer.entity;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponse customerEntityToCustomerResponse(Customer entity);

    Customer customerRequestToCustomerEntity(CustomerRequest customerRequest);

    List<CustomerResponse> customerEntityListToCustomerResponseList(List<Customer> customerList);


}
