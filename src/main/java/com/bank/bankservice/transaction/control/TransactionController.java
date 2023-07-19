package com.bank.bankservice.transaction.control;

import com.bank.bankservice.account.control.AccountRepository;
import com.bank.bankservice.account.entity.Account;
import com.bank.bankservice.customer.entity.CustomerResponse;
import com.bank.bankservice.transaction.entity.Transaction;
import com.bank.bankservice.transaction.entity.TransactionMapper;
import com.bank.bankservice.transaction.entity.TransactionRequest;
import com.bank.bankservice.transaction.entity.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionMapper transactionMapper;

    public void deposit(String accountId, TransactionRequest transactionRequest){
        Optional<Account> optionalAccount = Optional.of(accountRepository.getReferenceById(Long.valueOf(accountId)));

        optionalAccount.get().setAmount(optionalAccount.get().getAmount().add(transactionRequest.getAmount()));
        accountRepository.save(optionalAccount.get());

        //TODO - send this to kafka to get consumed later
        Transaction transactionEntity = new Transaction();
        transactionEntity.setTransactionType("DEPOSIT"); //TODO change to enum
        transactionEntity.setDate(LocalDateTime.now());
        transactionEntity.setAccount(optionalAccount.get());
        transactionEntity.setValue(transactionRequest.getAmount());

        transactionRepository.save(transactionEntity);
    }

    public List<TransactionResponse> getTransactions(String accountId){
        Optional<Account> optionalAccount = Optional.of(accountRepository.getReferenceById(Long.valueOf(accountId)));

        return transactionMapper.transactionEntityListToTransactionResponseList(transactionRepository.findByAccountId(optionalAccount.get().getId()));
    }


//    public void withdraw(String accountId, TransactionRequest transactionRequest){
//        Optional<Customer> optionalCustomer = Optional.of(customerRepository.getReferenceById(Long.valueOf(id)));
//        if(optionalCustomer.isEmpty()){
//            //todo
//            System.out.println("jogar erro aqui");
//        }
//        Account accountEntity = new Account();
//        accountEntity.setCustomer(optionalCustomer.get());
//        accountEntity.setAmount(new BigDecimal(BigInteger.ZERO));
//        accountRepository.save(accountEntity);
//    }


}
