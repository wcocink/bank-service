package com.bank.bankservice.bank.boundary;

import com.bank.bankservice.account.control.AccountController;
import com.bank.bankservice.customer.control.CustomerController;
import com.bank.bankservice.customer.entity.CustomerRequest;
import com.bank.bankservice.customer.entity.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class BankResource {

    @Autowired
    CustomerController customerController;

    @Autowired
    AccountController accountController;

    @GetMapping(
        value="customers",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CustomerResponse listAllCustomers() {
        return customerController.getCustomers();
    }

    @PostMapping(path = "customers",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void createCustomer(@RequestBody CustomerRequest customerRequest) {
        customerController.createCustomer(customerRequest);
    }

    @PostMapping(path = "accounts/{customerId}")
    public void createAccount(@PathVariable String customerId){
        accountController.createAccount(customerId);
    }

}