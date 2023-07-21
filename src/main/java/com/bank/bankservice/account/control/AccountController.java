package com.bank.bankservice.account.control;

import com.bank.bankservice.account.entity.Account;
import com.bank.bankservice.account.entity.AccountMapper;
import com.bank.bankservice.account.entity.AccountRequest;
import com.bank.bankservice.account.entity.AccountResponse;
import com.bank.bankservice.account.exception.AccountException;
import com.bank.bankservice.customer.control.CustomerRepository;
import com.bank.bankservice.customer.entity.Customer;
import com.bank.bankservice.customer.exception.CustomerException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@Controller
@Log4j2
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountMapper accountMapper;

    public AccountResponse createAccount(String customerId, AccountRequest accountRequest){
        Optional<Customer> optionalCustomer = customerRepository.findCustomerById(Long.valueOf(customerId));
        if(optionalCustomer.isEmpty()){
            log.info("Customer not found");
            throw CustomerException.customerNotFound();
        }
        Account accountEntity = new Account();
        accountEntity.setCustomer(optionalCustomer.get());
        if(accountRequest.getInitialBalance() != null){
            log.info("Setting balance passed in request");
            accountEntity.setBalance(accountRequest.getInitialBalance());
        }else{
            log.info("Balance set to 0");
            accountEntity.setBalance(new BigDecimal(BigInteger.ZERO));
        }
        accountRepository.save(accountEntity);
        return accountMapper.accountEntityToAccountResponse(accountEntity);
    }

    public AccountResponse getAccount(String customerId){
        Optional<Account> optionalAccount = accountRepository.findAccountById(Long.valueOf(customerId));
        if(optionalAccount.isEmpty()){
            throw AccountException.accountNotFound();
        }
        return accountMapper.accountEntityToAccountResponse(optionalAccount.get());
    }
}
