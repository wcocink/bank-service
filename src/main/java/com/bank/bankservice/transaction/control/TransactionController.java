package com.bank.bankservice.transaction.control;

import com.bank.bankservice.account.control.AccountRepository;
import com.bank.bankservice.account.entity.Account;
import com.bank.bankservice.account.exception.AccountException;
import com.bank.bankservice.kafka.producer.control.TransactionProducerController;
import com.bank.bankservice.kafka.producer.entity.TransactionMessageRequest;
import com.bank.bankservice.transaction.entity.*;
import com.bank.bankservice.transaction.exception.TransactionException;
import com.bank.bankservice.transaction.exception.TransactionInvalidDatesException;
import com.bank.bankservice.transaction.exception.TransactionNotEnoughBalanceException;
import com.bank.bankservice.transaction.exception.TransactionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
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

        Transaction transaction = createTransactionObject(optionalAccount.get(),
                TransactionEnum.DEPOSIT.getTransactionType(),
                transactionRequest.getAmount());


        this.sendMessageToQueue(transactionMapper.transactionEntityToTransactionMessageRequest(transaction));

        optionalAccount.get().setBalance(
                optionalAccount.get().getBalance().add(transactionRequest.getAmount()));

        this.updateAccount(optionalAccount.get());

        return transactionMapper.transactionEntityToOperationResponse(transaction);
    }

    public List<TransactionResponse> getAccountTransactions(String accountId,
                                                            Optional<String> transactionType,
                                                            Optional<BigDecimal> value,
                                                            Optional<LocalDate> startDate,
                                                            Optional<LocalDate> endDate){

        this.validateDates(startDate, endDate);

        Optional<Account> optionalAccount = accountRepository.findAccountById(Long.valueOf(accountId));
        if(optionalAccount.isEmpty()){
            throw AccountException.accountNotFound();
        }

        return transactionMapper.transactionEntityListToTransactionResponseList(
                transactionRepository.findByAccountIdAndOptionals(optionalAccount.get(), transactionType, value,
                        convertToLocalDateTime(startDate),
                        convertToLocalDateTime(endDate)));
    }

    private LocalDateTime convertToLocalDateTime(Optional<LocalDate> date) {
        return date.map(LocalDate::atStartOfDay).orElse(null);
    }

    private void validateDates(Optional<LocalDate> startDate, Optional<LocalDate> endDate) {
        if (endDate.isPresent() && startDate.isPresent()){
            if (endDate.get().isBefore(startDate.get())){
                throw TransactionInvalidDatesException.transactionInvalidDate();
            }
        }
    }

    public TransactionResponse getTransaction(String transactionId){
        Optional<Transaction> optionalTransaction = transactionRepository.findTransactionById(transactionId);
        if(optionalTransaction.isEmpty()){
            throw TransactionNotFoundException.transactionNotFound();
        }
        return transactionMapper.transactionEntityToTransactionResponse(optionalTransaction.get());
    }

    public OperationResponse withdraw(String accountId, TransactionRequest transactionRequest){
        Optional<Account> optionalAccount = accountRepository.findAccountById(Long.valueOf(accountId));
        if(optionalAccount.isEmpty()){
            throw AccountException.accountNotFound();
        }
        if(!hasEnoughBalance(optionalAccount.get().getBalance(), transactionRequest.getAmount())){
            throw TransactionNotEnoughBalanceException.notEnoughBalance();
        }

        Transaction transaction = createTransactionObject(optionalAccount.get(),
                TransactionEnum.WITHDRAW.getTransactionType(),
                transactionRequest.getAmount());

        this.sendMessageToQueue(transactionMapper.transactionEntityToTransactionMessageRequest(transaction));

        optionalAccount.get().setBalance(optionalAccount.get().getBalance().subtract(transactionRequest.getAmount()));

        this.updateAccount(optionalAccount.get());

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
