package com.bank.bankservice.account.boundary;

import com.bank.bankservice.account.control.AccountController;
import com.bank.bankservice.account.entity.AccountRequest;
import com.bank.bankservice.account.entity.AccountResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class AccountResource {

    @Autowired
    AccountController accountController;

    @PostMapping(path = "accounts/{customerId}")
    public ResponseEntity<AccountResponse> createAccount(@PathVariable String customerId,
                                                         @RequestBody @Valid AccountRequest accountRequest){
        return new ResponseEntity<>(accountController.createAccount(customerId, accountRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "accounts/{accountId}")
    public AccountResponse getAccount(@PathVariable String accountId){
        return accountController.getAccount(accountId);
    }
}
