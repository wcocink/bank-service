package com.bank.bankservice.transaction.control;

import com.bank.bankservice.account.control.AccountRepository;
import com.bank.bankservice.account.entity.Account;
import com.bank.bankservice.account.exception.AccountException;
import com.bank.bankservice.transaction.entity.*;
import com.bank.bankservice.transaction.exception.TransactionException;
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

    public TransactionResponse deposit(String accountId, TransactionRequest transactionRequest){
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
        return transactionMapper.transactionEntityToTransactionResponse(transactionEntity);
    }

    public List<TransactionResponse> getTransactions(String accountId){
        Optional<Account> optionalAccount = accountRepository.findAccountById(Long.valueOf(accountId));
        return transactionMapper.transactionEntityListToTransactionResponseList(transactionRepository.findByAccountId(optionalAccount.get().getId()));
    }

    public TransactionResponse withdraw(String accountId, TransactionRequest transactionRequest){
        Optional<Account> account = accountRepository.findAccountById(Long.valueOf(accountId));
        if(account.isEmpty()){
            throw AccountException.accountNotFound();
        }
        if(!hasEnoughBalance(account.get().getBalance(), transactionRequest.getAmount())){
            throw TransactionException.notEnoughBalance();

        }
        account.get().setBalance(account.get().getBalance().subtract(transactionRequest.getAmount()));

        this.updateAccount(account.get());

        //TODO - send this to kafka to get consumed later
        Transaction transactionEntity = new Transaction();
        transactionEntity.setTransactionType(TransactionEnum.WITHDRAW.getTransactionType());
        transactionEntity.setDate(LocalDateTime.now());
        transactionEntity.setAccount(account.get());
        transactionEntity.setValue(transactionRequest.getAmount());

        transactionRepository.save(transactionEntity);
        return transactionMapper.transactionEntityToTransactionResponse(transactionEntity);
    }

    private boolean hasEnoughBalance(BigDecimal accountBalance, BigDecimal withdrawAmount){
        return accountBalance.subtract(withdrawAmount).compareTo(BigDecimal.ZERO) >= 0;
    }

    private void updateAccount(Account account){
        accountRepository.save(account);
    }


}
