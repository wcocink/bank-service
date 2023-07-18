package com.bank.bankservice.control;

import com.bank.bankservice.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@Controller
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    public void createAccount(String id){
        Optional<Customer> optionalCustomer = Optional.of(customerRepository.getReferenceById(Long.valueOf(id)));
        if(optionalCustomer.isEmpty()){
            //todo
            System.out.println("jogar erro aqui");
        }
        Account accountEntity = new Account();
        accountEntity.setCustomer(optionalCustomer.get());
        accountEntity.setAmount(new BigDecimal(BigInteger.ZERO));
        accountRepository.save(accountEntity);
    }
}
