package com.bank.bankservice.customer.control;

import com.bank.bankservice.customer.entity.Customer;
import com.bank.bankservice.customer.entity.CustomerMapper;
import com.bank.bankservice.customer.entity.CustomerRequest;
import com.bank.bankservice.customer.entity.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    public CustomerResponse createCustomer(CustomerRequest customerRequest){
        Customer customer = customerMapper.customerRequestToCustomerEntity(customerRequest);
        customerRepository.save(customer);
        return customerMapper.customerEntityToCustomerResponse(customer);
    }

    public List<CustomerResponse> getCustomers(){
        return customerMapper.customerEntityListToCustomerResponseList(customerRepository.findAll());
    }

}
