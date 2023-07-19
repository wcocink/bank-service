package com.bank.bankservice.transaction.boundary;

import com.bank.bankservice.customer.entity.CustomerResponse;
import com.bank.bankservice.transaction.control.TransactionController;
import com.bank.bankservice.transaction.entity.TransactionRequest;
import com.bank.bankservice.transaction.entity.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class TransactionResource {

    @Autowired
    TransactionController transactionController;

    @PatchMapping(path = "transactions/{accountId}/deposit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void deposit(@PathVariable String accountId, @RequestBody TransactionRequest transactionRequest) {
        transactionController.deposit(accountId, transactionRequest);
    }

    @PatchMapping(path = "transactions/{accountId}/withdraw",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void withdraw(@PathVariable String accountId, @RequestBody TransactionRequest transactionRequest) {
        transactionController.withdraw(accountId, transactionRequest);
    }

    @GetMapping(
            value="transactions/{accountId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<TransactionResponse> getAllTransactionsFromAccount(@PathVariable String accountId) {
        return transactionController.getTransactions(accountId);
    }

}
