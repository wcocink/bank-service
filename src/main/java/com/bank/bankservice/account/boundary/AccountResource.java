package com.bank.bankservice.account.boundary;

import com.bank.bankservice.account.control.AccountController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank")
public class AccountResource {

    @Autowired
    AccountController accountController;

    @PostMapping(path = "accounts/{customerId}")
    public void createAccount(@PathVariable String customerId){
        accountController.createAccount(customerId);
    }

}
