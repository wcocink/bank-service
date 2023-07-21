package com.bank.bankservice.transaction.boundary;

import com.bank.bankservice.transaction.control.TransactionController;
import com.bank.bankservice.transaction.entity.TransactionRequest;
import com.bank.bankservice.transaction.entity.TransactionResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<TransactionResponse> deposit(@PathVariable String accountId, @RequestBody @Valid TransactionRequest transactionRequest) {
        return new ResponseEntity<>(transactionController.deposit(accountId, transactionRequest), HttpStatus.OK);
    }

    @PatchMapping(path = "transactions/{accountId}/withdraw",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> withdraw(@PathVariable String accountId, @RequestBody @Valid TransactionRequest transactionRequest) {
        return new ResponseEntity<>(transactionController.withdraw(accountId, transactionRequest), HttpStatus.OK);
    }

    @GetMapping(
            value="transactions/{accountId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<TransactionResponse> getAllTransactionsFromAccount(@PathVariable String accountId) {
        return transactionController.getTransactions(accountId);
    }

}
