package com.bank.bankservice.control;

import com.bank.bankservice.entity.Customer;
import com.bank.bankservice.entity.CustomerMapper;
import com.bank.bankservice.entity.CustomerRequest;
import com.bank.bankservice.entity.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    public void createCustomer(CustomerRequest customerRequest){
        Customer customer = customerMapper.MAPPER.customerRequestToCustomerEntity(customerRequest);
        customerRepository.save(customer);
    }

    public CustomerResponse getCustomers(){
        return customerMapper.MAPPER.customerEntityToCustomerResponse(customerRepository.getReferenceById(1L));
    }

}
