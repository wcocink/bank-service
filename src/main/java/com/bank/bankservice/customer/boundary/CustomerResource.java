package com.bank.bankservice.customer.boundary;

import com.bank.bankservice.customer.control.CustomerController;
import com.bank.bankservice.customer.entity.CustomerRequest;
import com.bank.bankservice.customer.entity.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/bank")
public class CustomerResource {

    @Autowired
    CustomerController customerController;

    @GetMapping(
            value="customers",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<CustomerResponse> listAllCustomers() {
        return customerController.getCustomers();
    }

    @PostMapping(path = "customers",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void createCustomer(@RequestBody CustomerRequest customerRequest) {
        customerController.createCustomer(customerRequest);
    }
}
