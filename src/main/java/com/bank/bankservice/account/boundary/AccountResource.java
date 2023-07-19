package com.bank.bankservice.account.boundary;

import com.bank.bankservice.account.control.AccountController;
import com.bank.bankservice.account.entity.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AccountResponse> createAccount(@PathVariable String customerId){
        return new ResponseEntity<>(accountController.createAccount(customerId), HttpStatus.CREATED);
    }

}
