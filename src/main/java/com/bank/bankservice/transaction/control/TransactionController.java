package com.bank.bankservice.transaction.control;

import com.bank.bankservice.account.control.AccountRepository;
import com.bank.bankservice.account.entity.Account;
import com.bank.bankservice.account.exception.AccountException;
import com.bank.bankservice.kafka.producer.control.TransactionProducerController;
import com.bank.bankservice.kafka.producer.entity.TransactionMessageRequest;
import com.bank.bankservice.transaction.entity.*;
import com.bank.bankservice.transaction.exception.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    @Autowired
    TransactionProducerController transactionProducerController;

    public OperationResponse deposit(String accountId, TransactionRequest transactionRequest){
        Optional<Account> optionalAccount = accountRepository.findAccountById(Long.valueOf(accountId));

        if(optionalAccount.isEmpty()){
            throw AccountException.accountNotFound();
        }

        optionalAccount.get().setBalance(
                optionalAccount.get().getBalance().add(transactionRequest.getAmount()));

        this.updateAccount(optionalAccount.get());

        Transaction transaction = createTransactionObject(optionalAccount.get(),
                TransactionEnum.DEPOSIT.getTransactionType(),
                transactionRequest.getAmount());


        this.sendMessageToQueue(transactionMapper.transactionEntityToTransactionMessageRequest(transaction));

        return transactionMapper.transactionEntityToOperationResponse(transaction);
    }

    public List<TransactionResponse> getTransactions(String accountId){
        Optional<Account> optionalAccount = accountRepository.findAccountById(Long.valueOf(accountId));
        if(optionalAccount.isEmpty()){
            throw AccountException.accountNotFound();
        }

        return transactionMapper.transactionEntityListToTransactionResponseList(
                transactionRepository.findTransactionsByAccountId(optionalAccount.get().getId())
        );
    }

    public OperationResponse withdraw(String accountId, TransactionRequest transactionRequest){
        Optional<Account> optionalAccount = accountRepository.findAccountById(Long.valueOf(accountId));
        if(optionalAccount.isEmpty()){
            throw AccountException.accountNotFound();
        }
        if(!hasEnoughBalance(optionalAccount.get().getBalance(), transactionRequest.getAmount())){
            throw TransactionException.notEnoughBalance();
        }

        optionalAccount.get().setBalance(optionalAccount.get().getBalance().subtract(transactionRequest.getAmount()));

        this.updateAccount(optionalAccount.get());

        Transaction transaction = createTransactionObject(optionalAccount.get(),
                TransactionEnum.WITHDRAW.getTransactionType(),
                transactionRequest.getAmount());

        this.sendMessageToQueue(transactionMapper.transactionEntityToTransactionMessageRequest(transaction));

        return transactionMapper.transactionEntityToOperationResponse(transaction);
    }

    public void saveTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }

    private boolean hasEnoughBalance(BigDecimal accountBalance, BigDecimal withdrawAmount){
        return accountBalance.subtract(withdrawAmount).compareTo(BigDecimal.ZERO) >= 0;
    }

    private void updateAccount(Account account){
        accountRepository.save(account);
    }

    private void sendMessageToQueue(TransactionMessageRequest transactionMessageRequest){
        this.transactionProducerController.sendMessage(transactionMessageRequest);
    }

    private Transaction createTransactionObject(Account account, String transactionType, BigDecimal amount){
        Transaction transactionEntity = new Transaction();
        transactionEntity.setTransactionType(transactionType);
        transactionEntity.setDate(LocalDateTime.now());
        transactionEntity.setAccount(account);
        transactionEntity.setValue(amount);
        return transactionEntity;
    }

}
