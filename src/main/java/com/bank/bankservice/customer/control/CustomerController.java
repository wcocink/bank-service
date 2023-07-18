package com.bank.bankservice.customer.control;

import com.bank.bankservice.customer.entity.Customer;
import com.bank.bankservice.customer.entity.CustomerMapper;
import com.bank.bankservice.customer.entity.CustomerRequest;
import com.bank.bankservice.customer.entity.CustomerResponse;
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
