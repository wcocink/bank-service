package com.bank.bankservice.transaction.control;

import com.bank.bankservice.account.control.AccountRepository;
import com.bank.bankservice.account.entity.Account;
import com.bank.bankservice.customer.entity.CustomerResponse;
import com.bank.bankservice.transaction.entity.Transaction;
import com.bank.bankservice.transaction.entity.TransactionEnum;
import com.bank.bankservice.transaction.entity.TransactionMapper;
import com.bank.bankservice.transaction.entity.TransactionRequest;
import com.bank.bankservice.transaction.entity.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
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

        optionalAccount.get().setBalance(
                optionalAccount.get().getBalance().add(transactionRequest.getAmount()));

        this.updateAccount(optionalAccount.get());

        //TODO - send this to kafka to get consumed later
        Transaction transactionEntity = new Transaction();
        transactionEntity.setTransactionType(TransactionEnum.DEPOSIT.getTransactionType());
        transactionEntity.setDate(LocalDateTime.now());
        transactionEntity.setAccount(optionalAccount.get());
        transactionEntity.setValue(transactionRequest.getAmount());

        transactionRepository.save(transactionEntity);
    }

    public List<TransactionResponse> getTransactions(String accountId){
        Optional<Account> optionalAccount = Optional.of(accountRepository.getReferenceById(Long.valueOf(accountId)));

        return transactionMapper.transactionEntityListToTransactionResponseList(transactionRepository.findByAccountId(optionalAccount.get().getId()));
    }


    public void withdraw(String accountId, TransactionRequest transactionRequest){
        Optional<Account> optionalAccount = Optional.of(accountRepository.getReferenceById(Long.valueOf(accountId)));
        if(optionalAccount.isEmpty()){
            //todo
            System.out.println("Throw error invalid account");
        }
        if(!hasEnoughBalance(optionalAccount.get().getBalance(), transactionRequest.getAmount())){
            //todo
            System.out.println("Throw error not enough balance");
        }

        optionalAccount.get().setBalance(
                optionalAccount.get().getBalance().subtract(transactionRequest.getAmount()));

        this.updateAccount(optionalAccount.get());

        //TODO - send this to kafka to get consumed later
        Transaction transactionEntity = new Transaction();
        transactionEntity.setTransactionType(TransactionEnum.WITHDRAW.getTransactionType());
        transactionEntity.setDate(LocalDateTime.now());
        transactionEntity.setAccount(optionalAccount.get());
        transactionEntity.setValue(transactionRequest.getAmount());

        transactionRepository.save(transactionEntity);
    }

    private boolean hasEnoughBalance(BigDecimal accountBalance, BigDecimal withdrawAmount){
        return accountBalance.subtract(withdrawAmount).compareTo(BigDecimal.ZERO) >= 0;
    }

    private void updateAccount(Account account){
        accountRepository.save(account);
    }


}
